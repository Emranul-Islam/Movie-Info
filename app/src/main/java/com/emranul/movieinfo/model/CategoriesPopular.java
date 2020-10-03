package com.emranul.movieinfo.model;

import java.util.List;

public  class CategoriesPopular {

    private List<Results> results;
    private int total_pages;
    private int total_results;
    private int page;

    public CategoriesPopular() {
    }

    public CategoriesPopular(List<Results> results, int total_pages, int total_results, int page) {
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.page = page;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
