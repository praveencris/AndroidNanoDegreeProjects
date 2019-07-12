package com.sabkayar.praveen.popularmovies;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.popularmovies.Interfaces.TrailerDataResponse;
import com.sabkayar.praveen.popularmovies.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TrailerDataResponse, TrailerAdapter.OnItemClickListener {
    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    ActivityDetailBinding mBinding;
    private RecyclerView mRecyclerView;
    private TrailerAdapter mTrailerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Data binding implementation
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
      /*  ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mBinding.trailerLayout.recyclerView.addItemDecoration(itemDecoration);*/

        mBinding.trailerLayout.recyclerView.setLayoutManager(linearLayoutManager);
        mBinding.trailerLayout.recyclerView.setHasFixedSize(true);

        mTrailerAdapter = new TrailerAdapter(this);
        mBinding.trailerLayout.recyclerView.setAdapter(mTrailerAdapter);


        makeServerCall(movie.getId());

    }

    private void makeServerCall(String movieId) {
        URL url = NetworkUtils.createUrlForVideos(movieId);
        FetchTrailerDataTask fetchTrailerDataTask = new FetchTrailerDataTask(this);
        if (NetworkUtils.isConnected(this)) {
            mBinding.trailerLayout.progressBar.setVisibility(View.VISIBLE);
            fetchTrailerDataTask.execute(url);
        }
    }

    @Override
    public void trailerResponse(List<TrailerDetails> trailerDetails) {
        mBinding.trailerLayout.progressBar.setVisibility(View.GONE);
        mTrailerAdapter.setTrailerDetails(trailerDetails);
    }

    @Override
    public void onItemClick(TrailerDetails trailerDetails) {
        Toast.makeText(this, trailerDetails.getName(), Toast.LENGTH_SHORT).show();
    }
}
