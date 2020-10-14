package com.emranul.movieinfo.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emranul.movieinfo.R;
import com.emranul.movieinfo.adapter.AdapterRank;
import com.emranul.movieinfo.api.ApiClint;
import com.emranul.movieinfo.api.ApiServices;
import com.emranul.movieinfo.model.CategoriesPopular;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.emranul.movieinfo.Constant.API_KEY;


public class FindFragment extends Fragment {

    private EditText searchEt;
    private Button searchBtn;
    private static final String TAG = "FindFragment";
    private RecyclerView recyclerViewSearch;
    private TextView errorText;

    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);

        searchEt = view.findViewById(R.id.search_edit);
        searchBtn = view.findViewById(R.id.search_btn);
        recyclerViewSearch = view.findViewById(R.id.search_rv);
        errorText = view.findViewById(R.id.search_error_tv);

        ApiServices apiServices = ApiClint.getRetrofit().create(ApiServices.class);

        searchEt.setFocusableInTouchMode(true);
        searchEt.setFocusable(true);
        searchEt.requestFocus();


        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));


        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 4) {

                    callSearchApi(apiServices, s.toString());

                } else {
                    errorText.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchValue = searchEt.getText().toString();
                if (!searchValue.isEmpty()) {

                    callSearchApi(apiServices, searchValue);


                } else {
                    searchEt.setError("Please write first");
                }
            }
        });


        return view;
    }

    private void callSearchApi(ApiServices apiServices, String query) {
        Call<CategoriesPopular> searchCall = apiServices.getSearch(API_KEY, query);
        searchCall.enqueue(new Callback<CategoriesPopular>() {
            @Override
            public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().getTotal_results());
                    AdapterRank adapterSearch = new AdapterRank(response.body().getResults());
                    recyclerViewSearch.setAdapter(adapterSearch);
                    adapterSearch.notifyDataSetChanged();
                    errorText.setVisibility(View.GONE);
                    if (response.body().getTotal_results() < 1) {
                        errorText.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d(TAG, "onResponse: Something wrong" + response.message());
                    errorText.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(getContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}