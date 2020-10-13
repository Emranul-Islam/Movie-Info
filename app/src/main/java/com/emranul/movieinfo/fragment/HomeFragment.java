package com.emranul.movieinfo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.emranul.movieinfo.Constant.API_KEY;
import static com.emranul.movieinfo.Constant.POPULAR_TYPE;
import static com.emranul.movieinfo.Constant.SLIDER_TYPE;
import static com.emranul.movieinfo.Constant.TYPE_POPULAR;
import static com.emranul.movieinfo.Constant.TYPE_TOP_RATED;
import static com.emranul.movieinfo.Constant.TYPE_TRENDING;
import static com.emranul.movieinfo.Constant.TYPE_UP_COMING;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView, mainRecycler;
    private final String TAG = "CALL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ApiServices apiServices = ApiClint.getRetrofit().create(ApiServices.class);

        Toolbar toolbar = view.findViewById(R.id.toolbar_home);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

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


//        List<Results> list = new ArrayList<>(5);
//        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));
//        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));
//        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));
//        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));
//        list.add(new Results(1, "TItle", "Original", 1, "wef", "sdf", "", ""));


        //categories api called:----------------------->
        Call<CategoriesGenres> genresCall = apiServices.getGenres(API_KEY);
        genresCall.enqueue(new Callback<CategoriesGenres>() {
            @Override
            public void onResponse(Call<CategoriesGenres> call, Response<CategoriesGenres> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Categories: " + response.isSuccessful());
                    List<Genres> genres = response.body().getGenres();
                    genres.add(new Genres("Recommends", -1));
                    AdapterCategories adapter = new AdapterCategories(genres);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {

                    Log.d(TAG, "Categories onResponse: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CategoriesGenres> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Categories onFailure: " + t.getMessage());
            }
        });

        //Slider: Playing call part start now:------------------->

        Call<CategoriesPlaying> playingCall = apiServices.getPlaying(API_KEY);
        playingCall.enqueue(new Callback<CategoriesPlaying>() {
            @Override
            public void onResponse(Call<CategoriesPlaying> call, Response<CategoriesPlaying> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Playing: " + response.isSuccessful());
                    mainList.add(0, new MainModel(response.body().getResults(), SLIDER_TYPE));
                    adapterMain.notifyDataSetChanged();

                } else {
                    Log.d(TAG, "Playing onResponse: " + response.message());


                }
            }

            @Override
            public void onFailure(Call<CategoriesPlaying> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Playing onFailure: " + t.getMessage());
            }
        });


        //Trending call part start now:------------------------------>

        Call<CategoriesPopular> trendingCall = apiServices.getTrending(API_KEY);
        trendingCall.enqueue(new Callback<CategoriesPopular>() {
            @Override
            public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Trending: " + response.isSuccessful());
                    mainList.add(new MainModel(response.body().getResults(), "Trending Movie", POPULAR_TYPE, TYPE_TRENDING));


                    adapterMain.notifyDataSetChanged();


                } else {
                    Log.d(TAG, "onResponse: Trending: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Trending: ", t);
            }
        });


        //Popular call part start now:------------------------------>

        Call<CategoriesPopular> popularCall = apiServices.getPopular(API_KEY);
        popularCall.enqueue(new Callback<CategoriesPopular>() {
            @Override
            public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Popular onResponse: " + response.isSuccessful());

                    mainList.add(new MainModel(response.body().getResults(), "Popular Movie", POPULAR_TYPE, TYPE_POPULAR));
                    adapterMain.notifyDataSetChanged();


                } else {
                    Log.d(TAG, "Popular onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Popular: ", t);
            }
        });


        //Top rated call part start now:------------------------------>

        Call<CategoriesPopular> topRatedCall = apiServices.getTopRated(API_KEY);
        topRatedCall.enqueue(new Callback<CategoriesPopular>() {
            @Override
            public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Top Rated onResponse: " + response.isSuccessful());

                    mainList.add(new MainModel(response.body().getResults(), "Top Rated Movie", POPULAR_TYPE, TYPE_TOP_RATED));
                    adapterMain.notifyDataSetChanged();


                } else {
                    Log.d(TAG, "Top Rated onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Top Rated: ", t);
            }
        });


        //Up Coming call part start now:------------------------------>

        Call<CategoriesPopular> upComingCall = apiServices.getUpComing(API_KEY);
        upComingCall.enqueue(new Callback<CategoriesPopular>() {
            @Override
            public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Up Coming onResponse: " + response.isSuccessful());

                    mainList.add(new MainModel(response.body().getResults(), "Up Coming Movie", POPULAR_TYPE, TYPE_UP_COMING));
                    adapterMain.notifyDataSetChanged();


                } else {
                    Log.d(TAG, "Up Coming onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Error", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Up Coming : ", t);
            }
        });


        return view;
    }



}