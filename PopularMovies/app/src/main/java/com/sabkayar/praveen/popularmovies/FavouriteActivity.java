package com.sabkayar.praveen.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sabkayar.praveen.popularmovies.database.AppDatabase;
import com.sabkayar.praveen.popularmovies.database.Movie;
import com.sabkayar.praveen.popularmovies.databinding.ActivityMainBinding;

import java.net.URL;
import java.util.List;


public class FavouriteActivity extends AppCompatActivity implements MovieAdapter.OnListItemClickListener {
    private static final String LOG_TAG = FavouriteActivity.class.getSimpleName();
    private MovieAdapter mAdapter;
    ActivityMainBinding mBinding;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        int numOfColumns = Utils.calculateNoOfColumns(this, 100);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numOfColumns);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mBinding.recyclerView.addItemDecoration(itemDecoration);

        mBinding.recyclerView.setLayoutManager(gridLayoutManager);
        mBinding.recyclerView.setHasFixedSize(true);

        mAdapter = new MovieAdapter(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mDb = AppDatabase.getInstance(getApplicationContext());

        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Movie> movieDetails = mDb.movieDao().getAllMovieDetails();
                if (movieDetails != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setMovieDetails(movieDetails);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onListItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
        startActivity(intent);
    }
}