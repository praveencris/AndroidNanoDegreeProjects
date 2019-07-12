package com.sabkayar.praveen.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class TrailerDetails implements Parcelable {
    private String id;
    private String key;
    private String name;
    private String site;
    private String resolution;
    private String videoType;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static Creator<TrailerDetails> getCREATOR() {
        return CREATOR;
    }


    protected TrailerDetails(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        resolution = in.readString();
        videoType = in.readString();
    }

    public TrailerDetails() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
        dest.writeString(resolution);
        dest.writeString(videoType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrailerDetails> CREATOR = new Creator<TrailerDetails>() {
        @Override
        public TrailerDetails createFromParcel(Parcel in) {
            return new TrailerDetails(in);
        }

        @Override
        public TrailerDetails[] newArray(int size) {
            return new TrailerDetails[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

}
