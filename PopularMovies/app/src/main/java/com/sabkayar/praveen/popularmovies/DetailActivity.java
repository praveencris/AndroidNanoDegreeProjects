package com.sabkayar.praveen.popularmovies;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;

public class DetailActivity extends AppCompatActivity {
    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    private TextView mTitleTextView;
    private ImageView mPosterImageView;
    private TextView mYearTextView;
    private TextView mDateMonthTextView;
    private TextView mRatingTextView;
    private TextView mOverViewTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleTextView = findViewById(R.id.tv_title);
        mPosterImageView = findViewById(R.id.imv_poster);
        mYearTextView = findViewById(R.id.tv_year);
        mDateMonthTextView = findViewById(R.id.tv_date_month);
        mRatingTextView = findViewById(R.id.tv_rating);
        mOverViewTextView = findViewById(R.id.tv_overview);

        Movie movie = (Movie) getIntent().getParcelableExtra(MOVIE_EXTRA);


        mTitleTextView.setText(movie.getTitle());
        Picasso.get().load(NetworkUtils.getImageAbsolutePath(movie.getMoviePosterRelativePath()))
                .placeholder(R.drawable.progress_animation).error(R.drawable.error_placeholder).into(mPosterImageView);
        mYearTextView.setText(Utils.getFormattedDate(movie.getReleaseDate(), "yyyy"));
        mDateMonthTextView.setText(Utils.getFormattedDate(movie.getReleaseDate(), "MMM, dd"));

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        mRatingTextView.append(decimalFormat.format(Double.valueOf(movie.getAverageVoting())));
        mRatingTextView.append("/10.0");
        mOverViewTextView.setText(movie.getMovieOverView());

        Log.d(LOG_TAG, movie.getTitle() +
                "\n" + movie.getAverageVoting() +
                "\n" + movie.getReleaseDate() +
                "\n" + movie.getMoviePosterRelativePath() +
                "\n" + movie.getMovieOverView());

    }
}
