package com.emranul.movieinfo.model;

import static com.emranul.movieinfo.Constant.PROFILE_BASE_URL;

public class Cast {
    private String profile_path;
    private String name;
    private String character;

    public Cast() {
    }

    public String getProfile_path() {
        return PROFILE_BASE_URL + profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
