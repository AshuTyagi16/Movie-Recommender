package com.sasuke.recommender.view;

import com.sasuke.recommender.model.User;

/**
 * Created by abc on 4/23/2018.
 */

public interface LoginView {
    void onLoginSuccess(User user);

    void onLoginFailure(Throwable t);

    void showNetworkConnectionError();
}
