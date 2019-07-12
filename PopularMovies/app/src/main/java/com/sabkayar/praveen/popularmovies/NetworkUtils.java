package com.sabkayar.praveen.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

    public static List<Movie> getMovieDetails(URL url, final FetchMovieDataTask fetchMovieDataTask) {
        HttpURLConnection urlConnection;
        List<Movie> moviesDetails = null;
        try {
            fetchMovieDataTask.doProgress(10);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            fetchMovieDataTask.doProgress(50);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String jsonString = getResponseString(urlConnection.getInputStream());
                fetchMovieDataTask.doProgress(75);
                moviesDetails = JsonUtils.getParsedListFromJson(jsonString);
            } else {
                Log.e(LOG_TAG, "Error occurred with response code " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fetchMovieDataTask.doProgress(100);
        return moviesDetails;
    }

    public static List<TrailerDetails> getMovieTrailers(URL url) {
        HttpURLConnection urlConnection;
        List<TrailerDetails> trailerDetails = null;
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
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE_VALUE)
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
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE_VALUE)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error occurred in building url");
        }
        return url;
    }

    static String getImageAbsolutePath(String relativePath) {
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
