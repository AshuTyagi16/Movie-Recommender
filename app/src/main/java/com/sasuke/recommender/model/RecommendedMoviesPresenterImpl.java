package com.sasuke.recommender.model;

import com.sasuke.recommender.Recommender;
import com.sasuke.recommender.manager.NetworkManager;
import com.sasuke.recommender.network.RecommenderApi;
import com.sasuke.recommender.presenter.RecommendedMoviesPresenter;
import com.sasuke.recommender.view.RecommendedMoviesView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 4/23/2018.
 */

public class RecommendedMoviesPresenterImpl implements RecommendedMoviesPresenter {

    private RecommendedMoviesView mRecommendedMoviesView;

    public RecommendedMoviesPresenterImpl(RecommendedMoviesView recommendedMoviesView) {
        this.mRecommendedMoviesView = recommendedMoviesView;
    }

    @Override
    public void getRecommendedMovies(String commaSeperatedMovieIds) {
        if (NetworkManager.getInstance(Recommender.getAppContext()).isConnected()) {
            RecommenderApi.getInstance().getMovieById(commaSeperatedMovieIds).enqueue(new Callback<ArrayList<Movie>>() {
                @Override
                public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                    mRecommendedMoviesView.onGetRecommendedMoviesSuccess(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                    mRecommendedMoviesView.onGetRecommendedMoviesFailure(t);
                }
            });
        } else
            mRecommendedMoviesView.showNetworkConnectionError();
    }
}
