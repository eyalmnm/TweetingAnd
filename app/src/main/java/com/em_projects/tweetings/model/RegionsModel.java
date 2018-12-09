package com.em_projects.tweetings.model;

import com.google.gson.annotations.SerializedName;

public class RegionsModel {

    @SerializedName("regions")
    private RegionModel[] regions;

    public RegionsModel(RegionModel[] regions) {
        this.regions = regions;
    }

    public RegionModel[] getRegions() {
        return regions;
    }

    public void setRegions(RegionModel[] regions) {
        this.regions = regions;
    }
}
