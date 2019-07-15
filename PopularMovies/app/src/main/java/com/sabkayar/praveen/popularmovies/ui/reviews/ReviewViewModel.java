package com.sabkayar.praveen.popularmovies.ui.reviews;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sabkayar.praveen.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class ReviewViewModel extends ViewModel {
    private MutableLiveData<List<Review>> mListMutableLiveData = new MutableLiveData<>();

    ReviewViewModel(String movieId) {
        loadReviews(movieId);
    }

    LiveData<List<Review>> getData() {
        return mListMutableLiveData;
    }

    void loadReviewsAgain(String movieId) {
        loadReviews(movieId);
    }

    private void loadReviews(String movieId) {
        URL url = NetworkUtils.createUrlForReviews(movieId);
        new AsyncTask<URL, Void, List<Review>>() {
            @Override
            protected List<Review> doInBackground(URL... urls) {
                return NetworkUtils.getMovieReviews(urls[0]);
            }
            @Override
            protected void onPostExecute(List<Review> reviews) {
                mListMutableLiveData.setValue(reviews);
            }
        }.execute(url);
    }
}
