package com.sasuke.recommender.view;

import java.util.ArrayList;

/**
 * Created by abc on 4/23/2018.
 */

public interface CategoriesView {
    void onGetCategoriesSuccess(ArrayList<String> list);

    void onGetCategoriesFailure(Throwable t);

    void showNetworkConnectionError();
}
