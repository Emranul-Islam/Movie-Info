package com.emranul.movieinfo.model;

public class Genres {
    private String name;
    private int id;

    public Genres() {
    }

    public Genres(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Genres{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
