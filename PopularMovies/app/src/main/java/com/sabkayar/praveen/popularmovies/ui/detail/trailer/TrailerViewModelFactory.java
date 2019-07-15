package com.sabkayar.praveen.popularmovies.ui.detail.trailer;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrailerViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String mMovieId;
    public TrailerViewModelFactory(String movieId) {
        mMovieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TrailerViewModel(mMovieId);
    }
}
