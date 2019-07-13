package com.sabkayar.praveen.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sabkayar.praveen.popularmovies.database.Movie;

import java.net.URL;
import java.util.List;

public class FetchMovieDataTask extends AsyncTask<URL, Integer, List<Movie>> {
    private static final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();
    private MovieResponse mMovieResponse;
    public interface MovieResponse {
        void onPreExecute();
        void publishProgress(int progress);
        void movieResponse(List<Movie> movieDetails);
    }

    FetchMovieDataTask(Context context) {
        mMovieResponse = (MovieResponse) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mMovieResponse.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(URL... urls) {
        return NetworkUtils.getMovieDetails(urls[0], this);
    }


    private int i = 0;

    void doProgress(int progress) {
        while (i <= progress) {
            try {
                if (progress == 100) {
                    publishProgress(progress);
                    Thread.sleep(10);
                    break;
                }
                publishProgress(i);
                i++;
            } catch (Exception e) {
                Log.i(LOG_TAG, e.getMessage());
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mMovieResponse.publishProgress(values[0]);
    }

    @Override
    protected void onPostExecute(List<Movie> moviesDetails) {
        mMovieResponse.movieResponse(moviesDetails);
    }

//    void checkConnectivity() {
//        if (mProgressBar.get().getProgress() == 10) {
//            Toast.makeText(mConstraintLayoutWeakReference.get().getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
//        }
//    }
}