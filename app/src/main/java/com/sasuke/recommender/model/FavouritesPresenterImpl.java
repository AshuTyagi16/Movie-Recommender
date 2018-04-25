package com.sasuke.recommender.model;

import com.sasuke.recommender.Recommender;
import com.sasuke.recommender.manager.NetworkManager;
import com.sasuke.recommender.network.RecommenderApi;
import com.sasuke.recommender.presenter.FavouritesPresenter;
import com.sasuke.recommender.view.FavouritesView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 4/26/2018.
 */

public class FavouritesPresenterImpl implements FavouritesPresenter {

    private FavouritesView mFavouritesView;

    public FavouritesPresenterImpl(FavouritesView favouritesView) {
        this.mFavouritesView = favouritesView;
    }

    @Override
    public void getFavouriteMovies(int userId) {
        if (NetworkManager.getInstance(Recommender.getAppContext()).isConnected()) {
            RecommenderApi.getInstance().fetchFavouriteMovies(userId).enqueue(new Callback<ArrayList<Movie>>() {
                @Override
                public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                    mFavouritesView.onGetFavouritesSuccess(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                    mFavouritesView.onGetFavouritesFailure(t);
                }
            });
        } else
            mFavouritesView.showNetworkConnectionError();
    }
}
