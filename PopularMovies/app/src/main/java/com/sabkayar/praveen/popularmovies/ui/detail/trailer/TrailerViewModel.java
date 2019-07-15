package com.sabkayar.praveen.popularmovies.ui.detail.trailer;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class TrailerViewModel extends ViewModel {
    private static final String LOG_TAG = TrailerViewModel.class.getSimpleName();
    private MutableLiveData<List<TrailerDetail>> mMutableLiveData=new MutableLiveData<>();
    TrailerViewModel(String movieId){
      loadTrailerData(movieId);
    }
    public LiveData<List<TrailerDetail>> getTrailerData(){
        return mMutableLiveData;
    }
    @SuppressLint("StaticFieldLeak")
    private void loadTrailerData(String movieId) {
        URL url = NetworkUtils.createUrlForVideos(movieId);
        new AsyncTask<URL, Void, List<TrailerDetail>>(){
            @Override
            protected List<TrailerDetail> doInBackground(URL... urls) {
                Log.d(LOG_TAG,"Trailer Network call performed");
                return NetworkUtils.getMovieTrailers(urls[0]);
            }
            @Override
            protected void onPostExecute(List<TrailerDetail> trailerDetails) {
                mMutableLiveData.setValue(trailerDetails);
            }
        }.execute(url);
    }

}
