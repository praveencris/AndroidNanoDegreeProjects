package com.sabkayar.praveen.popularmovies.ui.detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.popularmovies.AppExecutors;
import com.sabkayar.praveen.popularmovies.R;
import com.sabkayar.praveen.popularmovies.database.AppDatabase;
import com.sabkayar.praveen.popularmovies.database.Movie;
import com.sabkayar.praveen.popularmovies.databinding.ActivityDetailBinding;
import com.sabkayar.praveen.popularmovies.ui.detail.trailer.TrailerAdapter;
import com.sabkayar.praveen.popularmovies.ui.detail.trailer.TrailerDetail;
import com.sabkayar.praveen.popularmovies.ui.detail.trailer.TrailerViewModel;
import com.sabkayar.praveen.popularmovies.ui.detail.trailer.TrailerViewModelFactory;
import com.sabkayar.praveen.popularmovies.ui.reviews.ReviewActivity;
import com.sabkayar.praveen.popularmovies.utils.NetworkUtils;
import com.sabkayar.praveen.popularmovies.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.OnItemClickListener {
    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    ActivityDetailBinding mBinding;
    private RecyclerView mRecyclerView;
    private TrailerAdapter mTrailerAdapter;
    private AppDatabase mDb;
    private Movie movie;
    private TrailerViewModel mTrailerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Data binding implementation
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setupDetailView();
        mDb = AppDatabase.getInstance(getApplicationContext());
        mBinding.textViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorite();
            }
        });
        mBinding.textViewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                intent.putExtra(ReviewActivity.EXTRA_MOVIE_ID,movie.getId());
                startActivity(intent);
            }
        });
        setupRecyclerView();
        setupViewModel(movie.getId());
    }

    private void setupDetailView() {
        movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
        mBinding.tvTitle.setText(movie.getTitle());
        Picasso.get().load(NetworkUtils.getImageAbsolutePath(movie.getMoviePosterRelativePath()))
                .placeholder(R.drawable.progress_animation).error(R.drawable.error_placeholder).into(mBinding.imvPoster);
        mBinding.tvYear.setText(Utils.getFormattedDate(movie.getReleaseDate(), "yyyy"));
        mBinding.tvDateMonth.setText(Utils.getFormattedDate(movie.getReleaseDate(), "MMM, dd"));

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        mBinding.tvRating.append(decimalFormat.format(Double.valueOf(movie.getAverageVoting())));
        mBinding.tvRating.append("/10.0");
        mBinding.tvOverview.setText(movie.getMovieOverView());
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mBinding.trailerLayout.recyclerView.setLayoutManager(linearLayoutManager);
        mBinding.trailerLayout.recyclerView.setHasFixedSize(true);
        mTrailerAdapter = new TrailerAdapter(this);
        mBinding.trailerLayout.recyclerView.setAdapter(mTrailerAdapter);
    }

    private void setupViewModel(String movieId) {
        TrailerViewModelFactory trailerViewModelFactory = new TrailerViewModelFactory(movieId);
        mTrailerViewModel = ViewModelProviders.of(this, trailerViewModelFactory).get(TrailerViewModel.class);
        if (NetworkUtils.isConnected(this)) {
            mBinding.trailerLayout.progressBar.setVisibility(View.VISIBLE);
            mTrailerViewModel.getTrailerData().observe(this, new Observer<List<TrailerDetail>>() {
                @Override
                public void onChanged(List<TrailerDetail> trailerDetails) {
                    mBinding.trailerLayout.progressBar.setVisibility(View.GONE);
                    mTrailerAdapter.setTrailerDetails(trailerDetails);
                }
            });

        }
    }

    @Override
    public void onItemClick(TrailerDetail trailerDetails) {

        Toast.makeText(this, trailerDetails.getName(), Toast.LENGTH_SHORT).show();
        watchYoutubeVideo(this,trailerDetails.getKey());

    }
    public void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }


    private void addToFavorite() {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().insert(movie);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "Added to Favorite", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
