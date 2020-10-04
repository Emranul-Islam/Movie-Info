package com.emranul.movieinfo.model;

import java.util.List;

import static com.emranul.movieinfo.Constant.POSTER_BASE_URL;

public class Results {

    private double vote_average;
    private String title;
    private List<Integer> genre_ids;
    private String original_title;
    private int id;
    private String poster_path;
    private String release_date;
    private String overview;
    private String backdrop_path;

    public Results() {
    }

    public Results(double vote_average, String title, String original_title, int id, String poster_path, String release_date,String overview, String backdrop_path) {
        this.vote_average = vote_average;
        this.title = title;
        this.original_title = original_title;
        this.id = id;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop_path() {
        return POSTER_BASE_URL+backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        //poster path er sathe poster er base url adjust kora hoiche
        return POSTER_BASE_URL + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
