package com.sabkayar.praveen.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnListItemClickListener {
    private static final String SORT_BY_POPULARITY = "popular";
    private static final String SORT_BY_RATING = "top_rated";
    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private ConstraintLayout mConstraintLayout;
    private ConstraintLayout mNoInternetConstraintLayout;
    private ProgressBar mProgressBar;
    private FetchMovieDataTask mFetchMovieDataTask;
    private URL mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mConstraintLayout = findViewById(R.id.cl_progress_layout);
        mNoInternetConstraintLayout = findViewById(R.id.cl_no_internet);
        mProgressBar = findViewById(R.id.determinateBar);

        int numOfColumns = Utils.calculateNoOfColumns(this, 100);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numOfColumns);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        makeServerCall(SORT_BY_POPULARITY);

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
        if (mNoInternetConstraintLayout.getVisibility() == View.VISIBLE) {
            if (NetworkUtils.isConnected(this)) {
                mNoInternetConstraintLayout.setVisibility(View.GONE);
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
                if (mProgressBar.getProgress() == 10) {
                    Toast.makeText(MainActivity.this, "Please check your internet connectivity", Toast.LENGTH_LONG).show();
                    mConstraintLayout.setVisibility(View.GONE);
                    mNoInternetConstraintLayout.setVisibility(View.VISIBLE);
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
            default:
                break;
        }
        return true;
    }


    private void makeServerCall(String sortBy) {
        mURL = NetworkUtils.createUrl(sortBy);
        mFetchMovieDataTask = new FetchMovieDataTask(mAdapter, mConstraintLayout, mProgressBar);
        if (NetworkUtils.isConnected(this)) {
            mNoInternetConstraintLayout.setVisibility(View.GONE);
            startCountDownForCheckingInternet();
            mFetchMovieDataTask.execute(mURL);
        } else {
            mNoInternetConstraintLayout.setVisibility(View.VISIBLE);
        }
    }
}
