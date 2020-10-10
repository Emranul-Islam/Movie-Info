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
import com.emranul.movieinfo.adapter.AdapterCategories;
import com.emranul.movieinfo.adapter.AdapterMain;
import com.emranul.movieinfo.api.ApiClint;
import com.emranul.movieinfo.api.ApiServices;
import com.emranul.movieinfo.model.CategoriesGenres;
import com.emranul.movieinfo.model.CategoriesPlaying;
import com.emranul.movieinfo.model.CategoriesPopular;
import com.emranul.movieinfo.model.Genres;
import com.emranul.movieinfo.model.MainModel;
import com.emranul.movieinfo.model.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.emranul.movieinfo.Constant.API_KEY;
import static com.emranul.movieinfo.Constant.POPULAR_TYPE;
import static com.emranul.movieinfo.Constant.SLIDER_TYPE;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView, mainRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ApiServices apiServices = ApiClint.getRetrofit().create(ApiServices.class);

        recyclerView = view.findViewById(R.id.rv_categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        linearLayoutManager.getReverseLayout();
        recyclerView.setLayoutManager(linearLayoutManager);


        mainRecycler = view.findViewById(R.id.main_recycler);
        mainRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        List<MainModel> mainList = new ArrayList<>();
        AdapterMain adapterMain = new AdapterMain(getContext(), mainList);

        mainRecycler.setAdapter(adapterMain);


        List<Results> list = new ArrayList<>();
        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));
        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));
        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));
        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));
        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));


        //categories api called:----------------------->
        Call<CategoriesGenres> genresCall = apiServices.getGenres(API_KEY);
        genresCall.enqueue(new Callback<CategoriesGenres>() {
            @Override
            public void onResponse(Call<CategoriesGenres> call, Response<CategoriesGenres> response) {
                if (response.isSuccessful()) {
                    List<Genres> genres = response.body().getGenres();
                    genres.add(new Genres("Recommends", -1));
                    AdapterCategories adapter = new AdapterCategories(genres);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onResponse: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CategoriesGenres> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Problem", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

        //Slider call part start now:------------------->

        Call<CategoriesPlaying> playingCall = apiServices.getPlaying(API_KEY);
        playingCall.enqueue(new Callback<CategoriesPlaying>() {
            @Override
            public void onResponse(Call<CategoriesPlaying> call, Response<CategoriesPlaying> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Loading playing", Toast.LENGTH_SHORT).show();
                    mainList.add(new MainModel(response.body().getResults(), SLIDER_TYPE));
                    adapterMain.notifyDataSetChanged();

                } else {
                    Toast.makeText(getContext(), "Fiald playing", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onResponse: " + response.message());

                }
            }

            @Override
            public void onFailure(Call<CategoriesPlaying> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Problem playing", Toast.LENGTH_SHORT).show();
            }
        });


        //Popular call part start now:------------------------------>

        Call<CategoriesPopular> popularCall = apiServices.getPopular(API_KEY);
        popularCall.enqueue(new Callback<CategoriesPopular>() {
            @Override
            public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    mainList.add(new MainModel(response.body().getResults(), "Popular", POPULAR_TYPE));
                    mainList.add(new MainModel(response.body().getResults(), "Top Rated", POPULAR_TYPE));

                    adapterMain.notifyDataSetChanged();


                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                Toast.makeText(getContext(), "Faild.." + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });







        return view;
    }



}