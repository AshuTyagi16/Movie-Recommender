package com.sasuke.recommender.view;

import com.sasuke.recommender.model.Movie;

import java.util.ArrayList;

/**
 * Created by abc on 4/23/2018.
 */

public interface RecommendedMoviesView {
    void onGetRecommendedMoviesSuccess(ArrayList<Movie> movies);

    void onGetRecommendedMoviesFailure(Throwable t);

    void showNetworkConnectionError();
}
