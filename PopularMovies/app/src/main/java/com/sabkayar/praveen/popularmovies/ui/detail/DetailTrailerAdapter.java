package com.sabkayar.praveen.popularmovies.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.popularmovies.R;

import java.util.List;

public class DetailTrailerAdapter extends RecyclerView.Adapter<DetailTrailerAdapter.TrailerViewHolder> {
    private List<TrailerDetail> mTrailerDetails = null;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(TrailerDetail trailerDetails);
    }

    public DetailTrailerAdapter(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setTrailerDetails(List<TrailerDetail> trailerDetails) {
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
        TrailerDetail trailerDetail = mTrailerDetails.get(position);
        holder.bindData(trailerDetail);
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
            mVideoTypeTextView = itemView.findViewById(R.id.textViewVideoType);
            itemView.setOnClickListener(this);
        }

        public void bindData(TrailerDetail trailerDetail) {
            mTrailerTextView.setText(trailerDetail.getName());
            mVideoTypeTextView.setText(trailerDetail.getVideoType());
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(mTrailerDetails.get(getAdapterPosition()));
        }
    }
}
