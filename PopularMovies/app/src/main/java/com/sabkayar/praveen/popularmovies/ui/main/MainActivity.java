package com.sabkayar.praveen.popularmovies.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sabkayar.praveen.popularmovies.R;
import com.sabkayar.praveen.popularmovies.database.Movie;
import com.sabkayar.praveen.popularmovies.databinding.ActivityMainBinding;
import com.sabkayar.praveen.popularmovies.ui.detail.DetailActivity;
import com.sabkayar.praveen.popularmovies.ui.favourite.FavouriteActivity;
import com.sabkayar.praveen.popularmovies.utils.ItemOffsetDecoration;
import com.sabkayar.praveen.popularmovies.utils.NetworkUtils;
import com.sabkayar.praveen.popularmovies.utils.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnListItemClickListener {
    private static final String SORT_BY_POPULARITY = "popular";
    private static final String SORT_BY_RATING = "top_rated";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private MovieAdapter mAdapter;


    ActivityMainBinding mBinding;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupRecyclerView();
        setupViewModel();
    }

    private void setupRecyclerView() {
        int numOfColumns = Utils.calculateNoOfColumns(this, 100);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numOfColumns);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mBinding.recyclerView.addItemDecoration(itemDecoration);
        mBinding.recyclerView.setLayoutManager(gridLayoutManager);
        mBinding.recyclerView.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this);
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private void setupViewModel() {
        if (NetworkUtils.isConnected(this)) {
            setupOnConnected(true);
            MainViewModelFactory mainViewModelFactory = new MainViewModelFactory(SORT_BY_POPULARITY);
            mMainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel.class);
            mMainViewModel.getData().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movies) {
                    mBinding.clProgressLayout.setVisibility(View.GONE);
                    if (movies.isEmpty()) {
                        mBinding.clNoData.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.clNoData.setVisibility(View.GONE);
                        mAdapter.setMovieDetails(movies);
                    }
                }
            });
        } else {
            setupOnConnected(false);
        }
    }

    @Override
    public void onListItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_most_popular:
                setSortBy(SORT_BY_POPULARITY);
                break;
            case R.id.action_highest_rated:
                setSortBy(SORT_BY_RATING);
                break;
            case R.id.action_favourite:
                Intent intent = new Intent(this, FavouriteActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void setSortBy(String sortBy) {
        if (NetworkUtils.isConnected(this)) {
            setupOnConnected(true);
            mMainViewModel.setData(sortBy);
        } else {
            setupOnConnected(false);
        }
    }

    private void setupOnConnected(boolean connected) {
        if (connected) {
            mBinding.clProgressLayout.setVisibility(View.VISIBLE);
            mBinding.clNoInternet.setVisibility(View.GONE);
        } else {
            mBinding.clNoInternet.setVisibility(View.VISIBLE);
        }
    }


}
