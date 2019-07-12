package com.sabkayar.praveen.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private List<TrailerDetails> mTrailerDetails = null;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(TrailerDetails trailerDetails);
    }

    TrailerAdapter(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setTrailerDetails(List<TrailerDetails> trailerDetails) {
        mTrailerDetails = trailerDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        TrailerDetails trailerDetails = mTrailerDetails.get(position);
        holder.bindData(trailerDetails);
    }

    @Override
    public int getItemCount() {
        if (mTrailerDetails == null) {
            return 0;
        }
        return mTrailerDetails.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTrailerTextView;
        private TextView mVideoTypeTextView;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrailerTextView = itemView.findViewById(R.id.textViewTrailer);
            mVideoTypeTextView=itemView.findViewById(R.id.textViewVideoType);
            itemView.setOnClickListener(this);
        }

        public void bindData(TrailerDetails trailerDetail) {
            mTrailerTextView.setText(trailerDetail.getName());
            mVideoTypeTextView.setText(trailerDetail.getVideoType());
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(mTrailerDetails.get(getAdapterPosition()));
        }
    }
}
