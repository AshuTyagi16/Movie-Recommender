package com.sasuke.recommender.view;

/**
 * Created by abc on 4/23/2018.
 */

public interface RegisterUserView {
    void onRegisterUserSuccess();

    void onRegisterUserFailure(Throwable t);

    void showNetworkConnectionError();
}
