package com.sasuke.recommender.view;

/**
 * Created by abc on 4/23/2018.
 */

public interface RecommendedMoviesView {
    void onGetRecommendedMoviesSuccess();

    void onGetRecommendedMoviesFailure();

    void showNetworkConnectionError();
}
