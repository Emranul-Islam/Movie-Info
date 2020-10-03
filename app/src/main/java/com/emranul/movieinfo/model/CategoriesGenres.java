package com.emranul.movieinfo.model;

import java.util.List;

public  class CategoriesGenres {

    private List<Genres> genres;

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public CategoriesGenres() {
    }

    public CategoriesGenres(List<Genres> genres) {
        this.genres = genres;
    }
}
