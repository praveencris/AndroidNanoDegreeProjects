package com.sabkayar.praveen.popularmovies.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "movie_details")
public class Movie implements Parcelable {
    @ColumnInfo(name = "movie_id")
    @PrimaryKey
    @NonNull
    private String mId;
    @ColumnInfo(name = "original_title")
    private String mTitle;
    @ColumnInfo(name = "release_date")
    private String mReleaseDate;
    @ColumnInfo(name = "poster_path")
    private String mMoviePosterRelativePath;
    @ColumnInfo(name = "vote_average")
    private String mAverageVoting;
    @ColumnInfo(name = "overview")
    private String mMovieOverView;

    @Ignore
    public Movie() {
    }

   public Movie(@NonNull String id, String title, String releaseDate, String moviePosterRelativePath, String averageVoting, String movieOverView) {
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mMoviePosterRelativePath = moviePosterRelativePath;
        mAverageVoting = averageVoting;
        mMovieOverView = movieOverView;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        mId = id;
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
        dest.writeString(mId);
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
        mId = Objects.requireNonNull(in.readString());
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mMoviePosterRelativePath = in.readString();
        mAverageVoting = in.readString();
        mMovieOverView = in.readString();
    }


    @NonNull
    @Override
    public String toString() {
        return getId() + ":" + getTitle() + ":" + getReleaseDate() + ":" + getMoviePosterRelativePath() + ":" + getMovieOverView() + "\n";
    }
}
