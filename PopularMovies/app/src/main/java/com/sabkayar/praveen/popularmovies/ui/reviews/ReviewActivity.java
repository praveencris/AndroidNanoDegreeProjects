package com.sabkayar.praveen.popularmovies.ui.reviews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.popularmovies.R;
import com.sabkayar.praveen.popularmovies.databinding.ActivityReviewBinding;

import java.util.List;

public class ReviewActivity extends AppCompatActivity implements ReviewAdapter.OnItemClickListener {
    private ActivityReviewBinding mReviewBinding;
    private ReviewAdapter mReviewAdapter;
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //Data binding
        mReviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        String movieId = getIntent().getStringExtra(EXTRA_MOVIE_ID);
        setupReviewRecyclerView();
        setupReviewViewModel(movieId);

    }

    private void setupReviewViewModel(String movieId) {
        ReviewViewModelFactory reviewViewModelFactory = new ReviewViewModelFactory(movieId);
        ReviewViewModel reviewViewModel = ViewModelProviders.of(this, reviewViewModelFactory).get(ReviewViewModel.class);
        reviewViewModel.getData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                mReviewAdapter.setReviews(reviews);
            }
        });
    }

    private void setupReviewRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mReviewBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mReviewAdapter = new ReviewAdapter(this);
        mReviewBinding.recyclerView.setAdapter(mReviewAdapter);
    }

    @Override
    public void onItemClick(Review review) {
        Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
        startActivity(implicit);
    }
}
