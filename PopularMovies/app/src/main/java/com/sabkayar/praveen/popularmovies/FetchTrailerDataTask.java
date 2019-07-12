package com.sabkayar.praveen.popularmovies;

import android.content.Context;
import android.os.AsyncTask;

import com.sabkayar.praveen.popularmovies.Interfaces.TrailerDataResponse;

import java.net.URL;
import java.util.List;

public class FetchTrailerDataTask extends AsyncTask<URL, Void, List<TrailerDetails>> {
    private Context mContext;
    private TrailerDataResponse mTrailerDataResponse;

    FetchTrailerDataTask(Context context) {
        mContext = context;
        mTrailerDataResponse = (TrailerDataResponse) context;
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
