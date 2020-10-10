package com.emranul.movieinfo.model;

import java.util.List;

public class MainModel {

    //Slider recycler model
    private List<Results> slider;
    private int type;

    public MainModel(List<Results> slider, int type) {
        this.slider = slider;
        this.type = type;
    }

    public List<Results> getSlider() {
        return slider;
    }

    public void setSlider(List<Results> slider) {
        this.slider = slider;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    //Popular recycler model:
    private List<Results> popular;
    private String popularTitle;


    public MainModel(List<Results> popular, String popularTitle, int type) {
        this.type = type;
        this.popular = popular;
        this.popularTitle = popularTitle;
    }

    public String getPopularTitle() {
        return popularTitle;
    }

    public void setPopularTitle(String popularTitle) {
        this.popularTitle = popularTitle;
    }

    public List<Results> getPopular() {
        return popular;
    }

    public void setPopular(List<Results> popular) {
        this.popular = popular;
    }
}
