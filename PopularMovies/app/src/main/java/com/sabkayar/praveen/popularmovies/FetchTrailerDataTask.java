package com.sabkayar.praveen.popularmovies;

import android.content.Context;
import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

public class FetchTrailerDataTask extends AsyncTask<URL, Void, List<TrailerDetails>> {
    private Context mContext;
    private TrailerResponse mTrailerDataResponse;
    public interface TrailerResponse{
        void trailerResponse(List<TrailerDetails> trailerDetails);
    }
    FetchTrailerDataTask(Context context) {
        mContext = context;
        mTrailerDataResponse = (TrailerResponse) context;
    }

    @Override
    protected List<TrailerDetails> doInBackground(URL... urls) {
        return NetworkUtils.getMovieTrailers(urls[0]);
    }

    @Override
    protected void onPostExecute(List<TrailerDetails> trailerDetails) {
        mTrailerDataResponse.trailerResponse(trailerDetails);
    }
}
