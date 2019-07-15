package com.sabkayar.praveen.popularmovies.ui.favourite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.popularmovies.AppExecutors;

import com.sabkayar.praveen.popularmovies.R;
import com.sabkayar.praveen.popularmovies.database.AppDatabase;
import com.sabkayar.praveen.popularmovies.database.Movie;
import com.sabkayar.praveen.popularmovies.databinding.ActivityMainBinding;
import com.sabkayar.praveen.popularmovies.ui.detail.DetailActivity;
import com.sabkayar.praveen.popularmovies.ui.main.MovieAdapter;
import com.sabkayar.praveen.popularmovies.utils.ItemOffsetDecoration;
import com.sabkayar.praveen.popularmovies.utils.Utils;

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


        FavouriteViewModel viewModel=ViewModelProviders.of(this).get(FavouriteViewModel.class);
        LiveData<List<Movie>> movieDetails = viewModel.getTasks();//On Background thread
        movieDetails.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {//Running on main thread called on insert/delete/update or any change in db
                Log.d(LOG_TAG, "Data changed in database");
                if(movies.isEmpty()){
                    mBinding.clNoData.setVisibility(View.VISIBLE);
                }else {
                    mBinding.clNoData.setVisibility(View.GONE);
                    mAdapter.setMovieDetails(movies);
                }
            }
        });

//We can still use executors for delete,insert,update operation on database as we do not need to check for change


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.START | ItemTouchHelper.END | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Movie movie = mAdapter.getMovieDetails().get(position);
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.movieDao().delete(movie);
                    }
                });

            }
        }).attachToRecyclerView(mBinding.recyclerView);

    }

    @Override
    public void onListItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
        startActivity(intent);
    }
}