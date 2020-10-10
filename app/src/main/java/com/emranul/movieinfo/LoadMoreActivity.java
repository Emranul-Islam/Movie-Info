package com.emranul.movieinfo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emranul.movieinfo.adapter.AdapterRank;
import com.emranul.movieinfo.api.ApiClint;
import com.emranul.movieinfo.api.ApiServices;
import com.emranul.movieinfo.model.CategoriesPopular;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.emranul.movieinfo.Constant.API_KEY;
import static com.emranul.movieinfo.Constant.TYPE_POPULAR;
import static com.emranul.movieinfo.Constant.TYPE_TOP_RATED;
import static com.emranul.movieinfo.Constant.TYPE_TRENDING;
import static com.emranul.movieinfo.Constant.TYPE_UP_COMING;

public class LoadMoreActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private final String TAG = "LoadMore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);

        ApiServices apiServices = ApiClint.getRetrofit().create(ApiServices.class);

        toolbar = findViewById(R.id.load_more_toolbar);
        recyclerView = findViewById(R.id.load_more_rv);

        String title = getIntent().getStringExtra("title");
        int type = getIntent().getIntExtra("type", -1);
        toolbar.setTitle(title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switch (type) {
            case TYPE_POPULAR:
                Call<CategoriesPopular> popularCall = apiServices.getPopular(API_KEY);
                popularCall.enqueue(new Callback<CategoriesPopular>() {
                    @Override
                    public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Popular onResponse: " + response.isSuccessful());

                            AdapterRank adapterRank = new AdapterRank(response.body().getResults());
                            recyclerView.setAdapter(adapterRank);

                        } else {
                            Log.d(TAG, "Popular onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                        Toast.makeText(LoadMoreActivity.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: Popular: ", t);
                    }
                });

                break;
            case TYPE_TOP_RATED:
                Call<CategoriesPopular> topRatedCall = apiServices.getTopRated(API_KEY);
                topRatedCall.enqueue(new Callback<CategoriesPopular>() {
                    @Override
                    public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Top Rated onResponse: " + response.isSuccessful());

                            AdapterRank adapterRank = new AdapterRank(response.body().getResults());
                            recyclerView.setAdapter(adapterRank);
                        } else {
                            Log.d(TAG, "Top Rated onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                        Toast.makeText(LoadMoreActivity.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: Top Rated: ", t);
                    }
                });

                break;
            case TYPE_TRENDING:
                Call<CategoriesPopular> trendingCall = apiServices.getTrending(API_KEY);
                trendingCall.enqueue(new Callback<CategoriesPopular>() {
                    @Override
                    public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: Trending: " + response.isSuccessful());

                            AdapterRank adapterRank = new AdapterRank(response.body().getResults());
                            recyclerView.setAdapter(adapterRank);
                        } else {
                            Log.d(TAG, "onResponse: Trending: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                        Toast.makeText(LoadMoreActivity.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: Trending: ", t);
                    }
                });


                break;
            case TYPE_UP_COMING:
                Call<CategoriesPopular> upComingCall = apiServices.getUpComing(API_KEY);
                upComingCall.enqueue(new Callback<CategoriesPopular>() {
                    @Override
                    public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Up Coming onResponse: " + response.isSuccessful());

                            AdapterRank adapterRank = new AdapterRank(response.body().getResults());
                            recyclerView.setAdapter(adapterRank);

                        } else {
                            Log.d(TAG, "Up Coming onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                        Toast.makeText(LoadMoreActivity.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: Up Coming : ", t);
                    }
                });

                break;
            default:
                return;
        }
    }
}