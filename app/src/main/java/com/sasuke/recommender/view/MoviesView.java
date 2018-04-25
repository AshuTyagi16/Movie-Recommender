package com.sasuke.recommender.view;

import com.sasuke.recommender.model.Movie;

import java.util.ArrayList;

/**
 * Created by abc on 4/25/2018.
 */

public interface MoviesView {
    void onGetMoviesSuccess(ArrayList<Movie> list);

    void onGerMoviesFailure(Throwable t);

    void showNetworkConnectionError();
}
