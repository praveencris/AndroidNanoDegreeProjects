package com.sabkayar.praveen.popularmovies;

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
                moviesDetails.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesDetails;
    }


}
