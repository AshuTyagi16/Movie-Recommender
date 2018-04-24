package com.sasuke.recommender.view;

import com.sasuke.recommender.model.User;

/**
 * Created by abc on 4/23/2018.
 */

public interface RegisterUserView {
    void onRegisterUserSuccess(User user);

    void onRegisterUserFailure(Throwable t);

    void showNetworkConnectionError();
}
