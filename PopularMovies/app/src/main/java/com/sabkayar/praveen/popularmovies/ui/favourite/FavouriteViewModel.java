package com.sabkayar.praveen.popularmovies.ui.favourite;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sabkayar.praveen.popularmovies.database.AppDatabase;
import com.sabkayar.praveen.popularmovies.database.Movie;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {
    private static final String LOG_TAG = FavouriteViewModel.class.getSimpleName();
    private LiveData<List<Movie>> tasks;

    public LiveData<List<Movie>> getTasks() {
        return tasks;
    }

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        Log.d(LOG_TAG,"Activity retrieving the tasks from the Database");
        tasks = appDatabase.movieDao().getAllMovieDetails();
    }
}
