package com.emranul.movieinfo.api;

import com.emranul.movieinfo.model.CategoriesGenres;
import com.emranul.movieinfo.model.CategoriesPlaying;
import com.emranul.movieinfo.model.CategoriesPopular;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("genre/movie/list")
    Call<CategoriesGenres> getGenres(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<CategoriesPopular> getPopular(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<CategoriesPlaying> getPlaying(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<CategoriesPopular> getTopRated(@Query("api_key") String apiKey);

    @GET("trending/movie/week")
    Call<CategoriesPopular> getTrending(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<CategoriesPopular> getUpComing(@Query("api_key") String apiKey);


}
