package com.emranul.movieinfo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.emranul.movieinfo.Constant.COVER_BASE_URL;
import static com.emranul.movieinfo.Constant.POSTER_BASE_URL;

public class GetDetails {
    @SerializedName("backdrop_path")
    private String backdropPath;
    private Integer budget;
    private List<Genres> genres;
    private Integer id;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    private String title;
    private String overview;
    private Double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    private String release_date;
    private Integer revenue;
    private Integer runtime;
    private Double vote_average;

    public GetDetails() {
    }

    public GetDetails(String backdropPath, Integer budget, List<Genres> genres, Integer id, String originalLanguage, String originalTitle, String title, String overview, Double popularity, String posterPath, String release_date, Integer revenue, Integer runtime, Double vote_average) {
        this.backdropPath = backdropPath;
        this.budget = budget;
        this.genres = genres;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return COVER_BASE_URL + backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return POSTER_BASE_URL + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }
}
