package com.sabkayar.praveen.popularmovies.ui.detail;

import android.os.Parcel;
import android.os.Parcelable;

public class TrailerDetail implements Parcelable {
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

    public static Creator<TrailerDetail> getCREATOR() {
        return CREATOR;
    }


    protected TrailerDetail(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        resolution = in.readString();
        videoType = in.readString();
    }

    public TrailerDetail() {

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

    public static final Creator<TrailerDetail> CREATOR = new Creator<TrailerDetail>() {
        @Override
        public TrailerDetail createFromParcel(Parcel in) {
            return new TrailerDetail(in);
        }

        @Override
        public TrailerDetail[] newArray(int size) {
            return new TrailerDetail[size];
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
