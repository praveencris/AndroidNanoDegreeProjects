package com.sabkayar.praveen.popularmovies.utils;

import com.sabkayar.praveen.popularmovies.database.Movie;
import com.sabkayar.praveen.popularmovies.ui.detail.trailer.TrailerDetail;
import com.sabkayar.praveen.popularmovies.ui.reviews.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

final class JsonUtils {
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String RELEASE_DATE = "release_date";
    private static final String POSTER_PATH = "poster_path";
    private static final String AVERAGE_VOTE = "vote_average";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_ID = "id";

    public static List<Movie> getParsedListFromJson(String jsonString) {
        List<Movie> moviesDetails = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.optJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                Movie movie = new Movie();
                jsonObject = jsonArray.optJSONObject(i);
                movie.setTitle(jsonObject.optString(ORIGINAL_TITLE));
                movie.setReleaseDate(jsonObject.optString(RELEASE_DATE));
                movie.setMoviePosterRelativePath(jsonObject.optString(POSTER_PATH));
                movie.setAverageVoting(jsonObject.optString(AVERAGE_VOTE));
                movie.setMovieOverView(jsonObject.optString(MOVIE_OVERVIEW));
                movie.setId(jsonObject.optString(MOVIE_ID));
                moviesDetails.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesDetails;
    }


    private static final String VIDEO_ID = "id";
    private static final String VIDEO_KEY = "key";
    private static final String VIDEO_NAME = "name";
    private static final String VIDEO_SITE = "site";
    private static final String VIDEO_RESOLUTION = "size";
    private static final String VIDEO_TYPE = "type";

    public static List<TrailerDetail> getParsedListFromJsonForTrailers(String jsonString) {
        List<TrailerDetail> trailerDetails = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.optJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                TrailerDetail trailerDetail = new TrailerDetail();
                jsonObject = jsonArray.optJSONObject(i);
                trailerDetail.setId(jsonObject.optString(VIDEO_ID));
                trailerDetail.setKey(jsonObject.optString(VIDEO_KEY));
                trailerDetail.setName(jsonObject.optString(VIDEO_NAME));
                trailerDetail.setSite(jsonObject.optString(VIDEO_SITE));
                trailerDetail.setResolution(jsonObject.optString(VIDEO_RESOLUTION));
                trailerDetail.setVideoType(jsonObject.optString(VIDEO_TYPE));
                trailerDetails.add(trailerDetail);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailerDetails;
    }


    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";
    private static final String REVIEW_URL = "url";

    public static List<Review> getParsedListFromJsonForReviews(String jsonString) {
        List<Review> reviewList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.optJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                Review review = new Review();
                jsonObject = jsonArray.optJSONObject(i);
                review.setAuthor(jsonObject.optString(REVIEW_AUTHOR));
                review.setContent(jsonObject.optString(REVIEW_CONTENT));
                review.setUrl(jsonObject.optString(REVIEW_URL));
                reviewList.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewList;
    }
}
