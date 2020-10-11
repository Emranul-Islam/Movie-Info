package com.emranul.movieinfo.model;

import static com.emranul.movieinfo.Constant.POSTER_BASE_URL;

public class Images {
    private int width;
    private int height;
    private String file_path;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFile_path() {
        return POSTER_BASE_URL + file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
