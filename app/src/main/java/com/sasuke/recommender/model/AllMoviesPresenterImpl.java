package com.sasuke.recommender.model;

import android.support.annotation.NonNull;

import com.sasuke.recommender.Recommender;
import com.sasuke.recommender.manager.NetworkManager;
import com.sasuke.recommender.network.RecommenderApi;
import com.sasuke.recommender.presenter.AllMoviesPresenter;
import com.sasuke.recommender.view.AllMoviesView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 4/23/2018.
 */

public class AllMoviesPresenterImpl implements AllMoviesPresenter {

    private AllMoviesView allMoviesView;

    public AllMoviesPresenterImpl(AllMoviesView allMoviesView) {
        this.allMoviesView = allMoviesView;
    }

    @Override
    public void getAllMovies() {
        if (NetworkManager.getInstance(Recommender.getAppContext()).isConnected()) {
            RecommenderApi.getInstance().getAllMovies().enqueue(new Callback<ArrayList<Movie>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Movie>> call, @NonNull Response<ArrayList<Movie>> response) {
                    allMoviesView.onGetAllMoviesSuccess(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Movie>> call, @NonNull Throwable t) {
                    allMoviesView.onGetAllMoviesFailure(t);
                }
            });
        } else {
            allMoviesView.showNetworkConnectionError();
        }
    }
}
