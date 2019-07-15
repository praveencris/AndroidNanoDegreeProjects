package com.sabkayar.praveen.popularmovies.ui.main;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sabkayar.praveen.popularmovies.utils.NetworkUtils;
import com.sabkayar.praveen.popularmovies.database.Movie;

import java.net.URL;
import java.util.List;

public class MainViewModel extends ViewModel {
    private static final String LOG_TAG = MainViewModel.class.getSimpleName();
    private MutableLiveData<List<Movie>> mMutableLiveData = new MutableLiveData<List<Movie>>();

    MainViewModel(String sortBy) {
        loadData(sortBy);
    }

    public LiveData<List<Movie>> getData() {
        return mMutableLiveData;
    }

    public void setData(String sortBy) {
        loadData(sortBy);
    }


    private void loadData(String sortBy) {
        URL url = NetworkUtils.createUrl(sortBy);
        Log.d(LOG_TAG, "Network call performed");
        new AsyncTask<URL, Void, List<Movie>>() {
            @Override
            protected List<Movie> doInBackground(URL... urls) {
                return NetworkUtils.getMovieDetails(urls[0]);
            }

            @Override
            protected void onPostExecute(List<Movie> data) {
                mMutableLiveData.setValue(data);
            }
        }.execute(url);
    }
}
