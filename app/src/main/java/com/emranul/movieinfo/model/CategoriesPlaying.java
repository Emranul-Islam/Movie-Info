package com.emranul.movieinfo.model;

import java.util.List;

public class CategoriesPlaying {
    private List<Results> results;

    public CategoriesPlaying() {
    }

    public CategoriesPlaying(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
