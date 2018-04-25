package com.sasuke.recommender.view;

import com.sasuke.recommender.model.Movie;

import java.util.ArrayList;

/**
 * Created by abc on 4/26/2018.
 */

public interface SearchResultView {
    void onGetMoviesSuccess(ArrayList<Movie> list);

    void onGetMoviesFailure(Throwable t);

    void showNetworkConnectionError();
}
