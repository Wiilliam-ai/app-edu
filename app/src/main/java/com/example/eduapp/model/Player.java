package com.example.eduapp.model;

public class Player {
    private String url_img;
    private String fullName;
    private String points;

    public Player() {
    }

    public Player(String url_img, String fullName, String points) {
        this.url_img = url_img;
        this.fullName = fullName;
        this.points = points;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
