package com.sabkayar.praveen.popularmovies.ui.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sabkayar.praveen.popularmovies.database.AppDatabase;
import com.sabkayar.praveen.popularmovies.database.Movie;
import com.sabkayar.praveen.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class DetailViewModel extends ViewModel {
    private static final String LOG_TAG = DetailViewModel.class.getSimpleName();
    private MutableLiveData<List<TrailerDetail>> mMutableLiveData = new MutableLiveData<>();

    DetailViewModel(Context context, String movieId) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        mLiveData = database.movieDao().getMovieById(movieId);
        loadTrailerData(movieId);
    }

    public LiveData<List<TrailerDetail>> getTrailerData() {
        return mMutableLiveData;
    }

    @SuppressLint("StaticFieldLeak")
    private void loadTrailerData(String movieId) {
        URL url = NetworkUtils.createUrlForVideos(movieId);
        new AsyncTask<URL, Void, List<TrailerDetail>>() {
            @Override
            protected List<TrailerDetail> doInBackground(URL... urls) {
                Log.d(LOG_TAG, "Trailer Network call performed");
                return NetworkUtils.getMovieTrailers(urls[0]);
            }

            @Override
            protected void onPostExecute(List<TrailerDetail> trailerDetails) {
                mMutableLiveData.setValue(trailerDetails);
            }
        }.execute(url);
    }

    private LiveData<List<Movie>> mLiveData;

    LiveData<List<Movie>> getFavMovieData() {
        return mLiveData;
    }

}
