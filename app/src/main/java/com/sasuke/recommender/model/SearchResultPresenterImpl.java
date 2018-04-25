package com.sasuke.recommender.model;

import com.sasuke.recommender.Recommender;
import com.sasuke.recommender.manager.NetworkManager;
import com.sasuke.recommender.network.RecommenderApi;
import com.sasuke.recommender.presenter.SearchResultPresenter;
import com.sasuke.recommender.view.SearchResultView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 4/26/2018.
 */

public class SearchResultPresenterImpl implements SearchResultPresenter {

    private SearchResultView mSearchResultView;

    public SearchResultPresenterImpl(SearchResultView searchResultView) {
        this.mSearchResultView = searchResultView;
    }

    @Override
    public void getMoviesForQuery(String query) {
        if (NetworkManager.getInstance(Recommender.getAppContext()).isConnected()) {
            RecommenderApi.getInstance().getRecommendationsForQuery(query).enqueue(new Callback<ArrayList<Movie>>() {
                @Override
                public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                    mSearchResultView.onGetMoviesSuccess(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                    mSearchResultView.onGetMoviesFailure(t);
                }
            });
        } else
            mSearchResultView.showNetworkConnectionError();
    }
}
