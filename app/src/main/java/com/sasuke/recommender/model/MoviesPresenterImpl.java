package com.sasuke.recommender.model;

import android.support.annotation.NonNull;

import com.sasuke.recommender.Recommender;
import com.sasuke.recommender.manager.NetworkManager;
import com.sasuke.recommender.network.RecommenderApi;
import com.sasuke.recommender.presenter.MoviesPresenter;
import com.sasuke.recommender.view.MoviesView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 4/25/2018.
 */

public class MoviesPresenterImpl implements MoviesPresenter {

    private MoviesView mMoviesView;

    public MoviesPresenterImpl(MoviesView moviesView) {
        this.mMoviesView = moviesView;
    }

    @Override
    public void getMoviesForCategory(String category) {
        if (NetworkManager.getInstance(Recommender.getAppContext()).isConnected()) {
            RecommenderApi.getInstance().getMoviesForGenre(category).enqueue(new Callback<ArrayList<Movie>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Movie>> call, @NonNull Response<ArrayList<Movie>> response) {
                    mMoviesView.onGetMoviesSuccess(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Movie>> call, @NonNull Throwable t) {
                    mMoviesView.onGerMoviesFailure(t);
                }
            });
        } else {
            mMoviesView.showNetworkConnectionError();
        }
    }
}
