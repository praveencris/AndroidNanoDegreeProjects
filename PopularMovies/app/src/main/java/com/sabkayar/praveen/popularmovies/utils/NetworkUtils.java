package com.sabkayar.praveen.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.sabkayar.praveen.popularmovies.BuildConfig;
import com.sabkayar.praveen.popularmovies.database.Movie;
import com.sabkayar.praveen.popularmovies.ui.detail.TrailerDetail;
import com.sabkayar.praveen.popularmovies.ui.reviews.Review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public final class NetworkUtils {
    private static final String BASE_URL_MOVIE_API = "http://api.themoviedb.org/3/movie/";
    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY_VALUE = BuildConfig.API_KEY_VALUE;
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";
    private static final String LANGUAGE_PARAM = "language";
    private static final String LANGUAGE_VALUE = "en-US";

    public static List<Movie> getMovieDetails(URL url) {
        HttpURLConnection urlConnection;
        List<Movie> moviesDetails = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String jsonString = getResponseString(urlConnection.getInputStream());
                moviesDetails = JsonUtils.getParsedListFromJson(jsonString);
            } else {
                Log.e(LOG_TAG, "Error occurred with response code " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesDetails;
    }

    public static List<TrailerDetail> getMovieTrailers(URL url) {
        HttpURLConnection urlConnection;
        List<TrailerDetail> trailerDetails = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String jsonString = getResponseString(urlConnection.getInputStream());
                trailerDetails = JsonUtils.getParsedListFromJsonForTrailers(jsonString);
            } else {
                Log.e(LOG_TAG, "Error occurred with response code " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trailerDetails;
    }

    public static List<Review> getMovieReviews(URL url) {
        HttpURLConnection urlConnection;
        List<Review> reviewList = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String jsonString = getResponseString(urlConnection.getInputStream());
                reviewList = JsonUtils.getParsedListFromJsonForReviews(jsonString);
            } else {
                Log.e(LOG_TAG, "Error occurred with response code " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    private static String getResponseString(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }
        return stringBuilder.toString();
    }

    public static URL createUrl(String sortBy) {
        if (sortBy.isEmpty()) {
            return null;
        }
        Uri uri = Uri.parse(BASE_URL_MOVIE_API + sortBy).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error occurred in building url");
        }
        return url;
    }

    public static URL createUrlForVideos(String movieId) {
        if (movieId == null) {
            return null;
        }
        Uri uri = Uri.parse(BASE_URL_MOVIE_API + movieId + "/videos").buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error occurred in building url");
        }
        return url;
    }
    public static URL createUrlForReviews(String movieId) {
        if (movieId == null) {
            return null;
        }
        Uri uri = Uri.parse(BASE_URL_MOVIE_API + movieId + "/reviews").buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error occurred in building url");
        }
        return url;
    }

    public static String getImageAbsolutePath(String relativePath) {
        return IMAGE_BASE_URL + relativePath;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void startOrStopProgressBar(ProgressBar progressBar){
        if(progressBar!=null){
            if(progressBar.getVisibility()==View.VISIBLE){
                progressBar.setVisibility(View.INVISIBLE);
            }else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }
}
