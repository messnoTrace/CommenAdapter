package com.sample.model;

/**
 * Created by notrace on 2016/3/1.
 */
public class Place {
    private String imageUrl;
    private String name;

    public Place(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
