package com.em_projects.tweetings.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RegionModel implements Parcelable {

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


    public static final Creator<RegionModel> CREATOR = new Creator<RegionModel>() {
        @Override
        public RegionModel createFromParcel(Parcel source) {
            return new RegionModel(source);
        }

        @Override
        public RegionModel[] newArray(int size) {
            return new RegionModel[size];
        }
    };

    protected RegionModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }
}
