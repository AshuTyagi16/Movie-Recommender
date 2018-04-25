package com.sasuke.recommender.view;

import com.sasuke.recommender.model.Movie;

import java.util.ArrayList;

/**
 * Created by abc on 4/26/2018.
 */

public interface FavouritesView {
    void onGetFavouritesSuccess(ArrayList<Movie> list);

    void onGetFavouritesFailure(Throwable throwable);

    void showNetworkConnectionError();
}
