package info.firozansari.nested_recyclerview.helper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.firozansari.nested_recyclerview.model.Movie;
import info.firozansari.nested_recyclerview.model.MoviesResponse;
import retrofit2.*;
import retrofit2.Response;

/**
 * Created by topcashback on 23/10/2017.
 */

public class TopMovieLoader extends AsyncTaskLoader<List<Movie>> {

    private List<Movie> movieList = null;
    private ApiService service;

    public TopMovieLoader(Context context) {
        super(context);
        service = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    protected void onStartLoading() {
        if (movieList != null) {
            deliverResult(movieList);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Movie> loadInBackground() {
        Call<MoviesResponse> call = service.getTopRatedMovies(Config.API_KEY);
        try {
            MoviesResponse moviesResponse = call.execute().body();
            return moviesResponse.getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
