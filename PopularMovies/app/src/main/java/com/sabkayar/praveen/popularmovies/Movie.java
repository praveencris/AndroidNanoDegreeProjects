package com.sabkayar.praveen.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String mTitle;
    private String mReleaseDate;
    private String mMoviePosterRelativePath;
    private String mAverageVoting;
    private String mMovieOverView;

    public Movie(){

    }
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getMoviePosterRelativePath() {
        return mMoviePosterRelativePath;
    }

    public void setMoviePosterRelativePath(String moviePosterRelativePath) {
        mMoviePosterRelativePath = moviePosterRelativePath;
    }

    public String getAverageVoting() {
        return mAverageVoting;
    }

    public void setAverageVoting(String averageVoting) {
        mAverageVoting = averageVoting;
    }

    public String getMovieOverView() {
        return mMovieOverView;
    }

    public void setMovieOverView(String movieOverView) {
        mMovieOverView = movieOverView;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mMoviePosterRelativePath);
        dest.writeString(mAverageVoting);
        dest.writeString(mMovieOverView);
    }


    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        mTitle = in.readString();
        mReleaseDate=in.readString();
        mMoviePosterRelativePath=in.readString();
        mAverageVoting=in.readString();
        mMovieOverView=in.readString();
    }
}
