package com.emranul.movieinfo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.emranul.movieinfo.adapter.AdapterPopular;
import com.emranul.movieinfo.adapter.AdapterViewpager2Details;
import com.emranul.movieinfo.api.ApiClint;
import com.emranul.movieinfo.api.ApiServices;
import com.emranul.movieinfo.model.CategoriesPopular;
import com.emranul.movieinfo.model.Genres;
import com.emranul.movieinfo.model.GetDetails;
import com.emranul.movieinfo.model.GetImages;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.emranul.movieinfo.Constant.API_KEY;

public class DetailsActivity extends AppCompatActivity {

    private ImageView cover, image;
    private TextView title, vote, overview, director, language, budget, revenue, release, categories;
    private RatingBar ratingBar;
    private RecyclerView similarRecycler;
    private ViewPager2 viewPager2SS;
    private SpringDotsIndicator indicator;
    private static final String TAG = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ApiServices apiServices = ApiClint.getRetrofit().create(ApiServices.class);

        int id = getIntent().getIntExtra("id", 1);

        cover = findViewById(R.id.details_cover);
        image = findViewById(R.id.details_image);
        title = findViewById(R.id.details_title);
        vote = findViewById(R.id.details_vote);
        overview = findViewById(R.id.details_overview);
        director = findViewById(R.id.details_director);
        language = findViewById(R.id.details_language);
        budget = findViewById(R.id.details_budget);
        revenue = findViewById(R.id.details_revenue);
        release = findViewById(R.id.details_release);
        ratingBar = findViewById(R.id.details_rating);
        categories = findViewById(R.id.details_categories);
        similarRecycler = findViewById(R.id.details_similar_rv);
        viewPager2SS = findViewById(R.id.details_viewpager_ss);
        indicator = findViewById(R.id.details_indicator);

        similarRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        Call<GetDetails> getDetailsCall = apiServices.getDetails(id, API_KEY);

        getDetailsCall.enqueue(new Callback<GetDetails>() {
            @Override
            public void onResponse(Call<GetDetails> call, Response<GetDetails> response) {
                if (response.isSuccessful()) {
                    GetDetails getDetails = response.body();
                    title.setText(getDetails.getTitle());
                    vote.setText(getDetails.getVote_average() + "");
                    overview.setText(getDetails.getOverview());
                    language.setText("Language: " + getDetails.getOriginalLanguage());
                    budget.setText("Budget: " + getDetails.getBudget());
                    revenue.setText("Revenue: " + getDetails.getRevenue());
                    release.setText(getDetails.getRelease_date() + "      " + getDetails.getRuntime() + " min");
                    //vote onek somoy null value dey eta null hole app crash korbe tai check kora hoiche:
                    if (getDetails.getVote_average() != null) {
                        float x = (float) (getDetails.getVote_average() / 2);
                        ratingBar.setRating(x);
                    } else {
                        ratingBar.setRating(0);
                    }

                    Glide.with(DetailsActivity.this)
                            .load(getDetails.getBackdropPath())
                            .into(cover);

                    Glide.with(DetailsActivity.this)
                            .load(getDetails.getPosterPath())
                            .into(image);

                    //categories list lop calaiya ekta string e rekhe set kora hoiche
                    List<Genres> genres = getDetails.getGenres();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Genres genres1 : genres) {
                        stringBuilder.append(genres1.getName());
                        stringBuilder.append(", ");
                    }
                    categories.setText(stringBuilder.toString());


                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }

            }

            @Override
            public void onFailure(Call<GetDetails> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });


        //similar api call :------------->

        Call<CategoriesPopular> getSimilar = apiServices.getSimilar(id, API_KEY);
        getSimilar.enqueue(new Callback<CategoriesPopular>() {
            @Override
            public void onResponse(Call<CategoriesPopular> call, Response<CategoriesPopular> response) {
                if (response.isSuccessful()) {

                    AdapterPopular adapterSimilar = new AdapterPopular(response.body().getResults());
                    similarRecycler.setAdapter(adapterSimilar);

                } else {
                    Log.d(TAG, "onResponse Similar: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoriesPopular> call, Throwable t) {
                Log.e(TAG, "onFailure: Similar", t);
            }
        });


        //Images api call-------------->
        Call<GetImages> getImagesCall = apiServices.getImages(id, API_KEY);
        getImagesCall.enqueue(new Callback<GetImages>() {
            @Override
            public void onResponse(Call<GetImages> call, Response<GetImages> response) {
                if (response.isSuccessful()) {
                    AdapterViewpager2Details adapterSS = new AdapterViewpager2Details(response.body().getBackdrops());
                    viewPager2SS.setAdapter(adapterSS);
                    indicator.setViewPager2(viewPager2SS);
                } else {
                    Log.d(TAG, "onResponse: Images: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GetImages> call, Throwable t) {
                Log.e(TAG, "onFailure: Images", t);
            }
        });


    }
}