package com.emranul.movieinfo.model;

import java.util.List;

public class GetImages {

    private List<Images> backdrops;
    private int id;

    public List<Images> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Images> backdrops) {
        this.backdrops = backdrops;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
