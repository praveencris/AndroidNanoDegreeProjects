package com.sabkayar.praveen.popularmovies;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

public class FetchMovieDataTask extends AsyncTask<URL, Integer, List<Movie>> {
    private static final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();
    private WeakReference<MovieAdapter> mAdapter;
    private WeakReference<ConstraintLayout> mConstraintLayoutWeakReference;
    private WeakReference<ProgressBar> mProgressBar;

    FetchMovieDataTask(MovieAdapter adapter, ConstraintLayout constraintLayout,ProgressBar progressBar) {
        mAdapter = new WeakReference<>(adapter);
        mConstraintLayoutWeakReference = new WeakReference<>(constraintLayout);
        mProgressBar=new WeakReference<>(progressBar);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mConstraintLayoutWeakReference.get().setVisibility(View.VISIBLE);
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
        mProgressBar.get().setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(List<Movie> moviesDetails) {
        mConstraintLayoutWeakReference.get().setVisibility(View.GONE);
        if (moviesDetails != null) {
            mAdapter.get().setMovieDetails(moviesDetails);
        }
    }

    void checkConnectivity() {
        if(mProgressBar.get().getProgress()==10){
            Toast.makeText(mConstraintLayoutWeakReference.get().getContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
        }
    }
}