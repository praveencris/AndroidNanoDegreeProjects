package com.sabkayar.praveen.popularmovies.ui.detail;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String mMovieId;
    private Context mContext;

    DetailViewModelFactory(Context context, String movieId) {
        mMovieId = movieId;
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(mContext, mMovieId);
    }
}
