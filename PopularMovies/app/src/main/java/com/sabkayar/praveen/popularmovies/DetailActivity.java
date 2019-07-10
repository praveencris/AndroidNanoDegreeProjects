package com.sabkayar.praveen.popularmovies;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.sabkayar.praveen.popularmovies.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    ActivityDetailBinding mBinding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Data binding implementation
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_detail);

        Movie movie = getIntent().getParcelableExtra(MOVIE_EXTRA);


        mBinding.tvTitle.setText(movie.getTitle());
        Picasso.get().load(NetworkUtils.getImageAbsolutePath(movie.getMoviePosterRelativePath()))
                .placeholder(R.drawable.progress_animation).error(R.drawable.error_placeholder).into(mBinding.imvPoster);
        mBinding.tvYear.setText(Utils.getFormattedDate(movie.getReleaseDate(), "yyyy"));
        mBinding.tvDateMonth.setText(Utils.getFormattedDate(movie.getReleaseDate(), "MMM, dd"));

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        mBinding.tvRating.append(decimalFormat.format(Double.valueOf(movie.getAverageVoting())));
        mBinding.tvRating.append("/10.0");
        mBinding.tvOverview.setText(movie.getMovieOverView());

        Log.d(LOG_TAG, movie.getTitle() +
                "\n" + movie.getAverageVoting() +
                "\n" + movie.getReleaseDate() +
                "\n" + movie.getMoviePosterRelativePath() +
                "\n" + movie.getMovieOverView());

    }
}
