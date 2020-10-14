package com.emranul.movieinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.emranul.movieinfo.adapter.AdapterCredits;
import com.emranul.movieinfo.adapter.AdapterPopular;
import com.emranul.movieinfo.adapter.AdapterViewpager2Details;
import com.emranul.movieinfo.api.ApiClint;
import com.emranul.movieinfo.api.ApiServices;
import com.emranul.movieinfo.model.CategoriesPopular;
import com.emranul.movieinfo.model.Crew;
import com.emranul.movieinfo.model.Genres;
import com.emranul.movieinfo.model.GetCredits;
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
    private ImageButton backBtn;
    private TextView title, vote, overview, director, language, budget, revenue, release, categories, similarTv;
    private RatingBar ratingBar;
    private RecyclerView similarRecycler, castRecycler;
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
        similarTv = findViewById(R.id.details_similar_tv);
        similarRecycler = findViewById(R.id.details_similar_rv);
        castRecycler = findViewById(R.id.details_cast_rv);
        viewPager2SS = findViewById(R.id.details_viewpager_ss);
        indicator = findViewById(R.id.details_indicator);
        backBtn = findViewById(R.id.details_back_btn);

        similarRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        castRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        //details api call :------------->
        Call<GetDetails> getDetailsCall = apiServices.getDetails(id, API_KEY);

        //details api er sob info set kora hoiche:
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
                    if (response.body().getTotal_results() > 0) {
                        similarTv.setVisibility(View.VISIBLE);
                    }

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


        Call<GetCredits> getCreditsCall = apiServices.getCredits(id, API_KEY);
        getCreditsCall.enqueue(new Callback<GetCredits>() {
            @Override
            public void onResponse(Call<GetCredits> call, Response<GetCredits> response) {
                if (response.isSuccessful()) {
                    AdapterCredits adapterCredits = new AdapterCredits(response.body().getCast());
                    castRecycler.setAdapter(adapterCredits);
                    adapterCredits.notifyDataSetChanged();
                    StringBuilder stringBuilder = new StringBuilder();
                    List<Crew> crewList = response.body().getCrew();
                    for (int i = 0; i < crewList.size(); i++) {
                        if (crewList.get(i).getJob().equals("Director")) {
                            stringBuilder.append(crewList.get(i).getName());
                            stringBuilder.append(". ");
                        }
                    }
                    director.setText("Director: " + stringBuilder.toString());

                } else {
                    Toast.makeText(DetailsActivity.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: Credits: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GetCredits> call, Throwable t) {
                Log.e(TAG, "onFailure: Credits", t);
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}