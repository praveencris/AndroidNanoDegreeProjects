package com.sabkayar.praveen.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMovieDetails = null;
    private OnListItemClickListener mOnListItemClickListener;

    public interface OnListItemClickListener {
        void onListItemClick(Movie movie);
    }

    public void setMovieDetails(List<Movie> movieDetails) {
        mMovieDetails = movieDetails;
        notifyDataSetChanged();
    }

    public MovieAdapter(OnListItemClickListener listItemClickListener) {
        mOnListItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mMovieDetails.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (mMovieDetails == null) {
            return 0;
        }
        return mMovieDetails.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mMoviePosterView;
        ProgressBar mProgressBar;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mMoviePosterView = itemView.findViewById(R.id.image_poster);
            mProgressBar = itemView.findViewById(R.id.image_progress);
            mMoviePosterView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            mProgressBar.setVisibility(View.GONE);
            Picasso.get().load(NetworkUtils.getImageAbsolutePath(movie.getMoviePosterRelativePath())).placeholder(R.drawable.progress_animation)
                    .error(R.drawable.error_placeholder).into(mMoviePosterView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            Movie movie = mMovieDetails.get(position);
            mOnListItemClickListener.onListItemClick(movie);
        }
    }
}
