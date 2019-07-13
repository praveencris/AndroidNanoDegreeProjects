package com.sabkayar.praveen.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnListItemClickListener, FetchMovieDataTask.MovieResponse {
    private static final String SORT_BY_POPULARITY = "popular";
    private static final String SORT_BY_RATING = "top_rated";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private MovieAdapter mAdapter;
    private FetchMovieDataTask mFetchMovieDataTask;
    private URL mURL;

    private AppDatabase mDb;
    ActivityMainBinding mBinding;

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

        makeServerCall(SORT_BY_POPULARITY);
        mDb = AppDatabase.getInstance(getApplicationContext());

    }

    @Override
    public void onListItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mBinding.clNoInternet.getVisibility() == View.VISIBLE) {
            if (NetworkUtils.isConnected(this)) {
                mBinding.clNoInternet.setVisibility(View.GONE);
                startCountDownForCheckingInternet();
                mFetchMovieDataTask.execute(mURL);
            }
        }
    }

    private void startCountDownForCheckingInternet() {
        new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (mBinding.determinateBar.getProgress() == 10) {
                    Toast.makeText(MainActivity.this, "Please check your internet connectivity", Toast.LENGTH_LONG).show();
                    mBinding.clProgressLayout.setVisibility(View.GONE);
                    mBinding.clNoInternet.setVisibility(View.VISIBLE);
                    mFetchMovieDataTask.cancel(true);
                }
            }
        }.start();
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
                makeServerCall(SORT_BY_POPULARITY);
                break;
            case R.id.action_highest_rated:
                makeServerCall(SORT_BY_RATING);
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


    private void makeServerCall(String sortBy) {
        mURL = NetworkUtils.createUrl(sortBy);
        mFetchMovieDataTask = new FetchMovieDataTask(this);
        if (NetworkUtils.isConnected(this)) {
            mBinding.clNoInternet.setVisibility(View.GONE);
            startCountDownForCheckingInternet();
            mFetchMovieDataTask.execute(mURL);
        } else {
            mBinding.clNoInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPreExecute() {
        mBinding.clProgressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void publishProgress(int progress) {
        mBinding.determinateBar.setProgress(progress);
    }

    @Override
    public void movieResponse(List<Movie> movieDetails) {
        mBinding.clProgressLayout.setVisibility(View.GONE);
        if (movieDetails != null) {
            mAdapter.setMovieDetails(movieDetails);
        }
    }
}
