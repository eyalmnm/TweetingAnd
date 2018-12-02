package com.em_projects.tweetings.model;

import com.google.gson.annotations.SerializedName;

public class RegionModel {

    @SerializedName("id")
    private int id;
    @SerializedName("itemTitle")
    private String title;

    public RegionModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
