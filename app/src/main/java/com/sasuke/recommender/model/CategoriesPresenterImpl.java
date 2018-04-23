package com.sasuke.recommender.model;

import com.sasuke.recommender.Recommender;
import com.sasuke.recommender.manager.NetworkManager;
import com.sasuke.recommender.network.RecommenderApi;
import com.sasuke.recommender.presenter.CategoriesPresenter;
import com.sasuke.recommender.view.CategoriesView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 4/23/2018.
 */

public class CategoriesPresenterImpl implements CategoriesPresenter {

    private CategoriesView mCategoriesView;

    public CategoriesPresenterImpl(CategoriesView mCategoriesView) {
        this.mCategoriesView = mCategoriesView;
    }

    @Override
    public void getCategories() {
        if (NetworkManager.getInstance(Recommender.getAppContext()).isConnected()) {
            RecommenderApi.getInstance().getCategories().enqueue(new Callback<ArrayList<String>>() {
                @Override
                public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                    mCategoriesView.onGetCategoriesSuccess(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                    mCategoriesView.onGetCategoriesFailure(t);
                }
            });
        } else
            mCategoriesView.showNetworkConnectionError();
    }
}
