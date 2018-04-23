package com.sasuke.recommender.view;

import com.sasuke.recommender.model.Movie;

import java.util.ArrayList;

/**
 * Created by abc on 4/23/2018.
 */

public interface AllMoviesView {
    void onGetAllMoviesSuccess(ArrayList<Movie> list);

    void onGetAllMoviesFailure(Throwable t);

    void showNetworkConnectionError();
}
