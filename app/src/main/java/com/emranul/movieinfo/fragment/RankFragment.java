package com.emranul.movieinfo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class RankFragment extends Fragment {


    private RecyclerView recyclerView;
    private final String TAG = "rank";

    public RankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank, container, false);

        recyclerView = view.findViewById(R.id.rank_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiServices apiServices = ApiClint.getRetrofit().create(ApiServices.class);

        Call<CategoriesPopular> rankCall = apiServices.getTopRated(API_KEY);
        rankCall.enqueue(new Callback<CategoriesPopular>() {
            @Override
            public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Rank: " + response.isSuccessful());
                    AdapterRank adapterRank = new AdapterRank(response.body().getResults());
                    recyclerView.setAdapter(adapterRank);
                } else {
                    Log.d(TAG, "onResponse: Rank: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Rank: ", t);
            }
        });


        return view;
    }
}