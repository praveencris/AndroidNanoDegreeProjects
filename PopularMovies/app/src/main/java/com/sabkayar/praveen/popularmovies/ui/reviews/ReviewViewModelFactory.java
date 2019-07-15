package com.sabkayar.praveen.popularmovies.ui.reviews;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ReviewViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private String mMovieId;

    ReviewViewModelFactory(String movieId) {
        mMovieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ReviewViewModel(mMovieId);
    }
}
