package com.sabkayar.praveen.popularmovies.ui.reviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.popularmovies.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> mReviewList;
    private OnItemClickListener mOnItemClickListener;

    void setReviews(List<Review> reviewList) {
        mReviewList = reviewList;
        notifyDataSetChanged();
    }

    ReviewAdapter(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = mReviewList.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        if (mReviewList != null) {
            return mReviewList.size();
        }
        return 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mAuthorTextView;
        private TextView mContentTextView;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mAuthorTextView = itemView.findViewById(R.id.tv_author);
            mContentTextView = itemView.findViewById(R.id.tv_content);
        }

        void bind(Review review) {
            mAuthorTextView.setText(review.getAuthor());
            mContentTextView.setText(review.getContent());
        }

        @Override
        public void onClick(View v) {
            Review review = mReviewList.get(this.getAdapterPosition());
            mOnItemClickListener.onItemClick(review);
        }
    }

    interface OnItemClickListener {
        void onItemClick(Review review);
    }
}
