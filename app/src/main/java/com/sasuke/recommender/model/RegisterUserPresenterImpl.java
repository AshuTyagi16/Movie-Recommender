package com.sasuke.recommender.model;

import com.sasuke.recommender.Recommender;
import com.sasuke.recommender.manager.NetworkManager;
import com.sasuke.recommender.network.RecommenderApi;
import com.sasuke.recommender.presenter.RegisterUserPresenter;
import com.sasuke.recommender.view.RegisterUserView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abc on 4/23/2018.
 */

public class RegisterUserPresenterImpl implements RegisterUserPresenter {

    private RegisterUserView mRegisterUserView;

    public RegisterUserPresenterImpl(RegisterUserView mRegisterUserView) {
        this.mRegisterUserView = mRegisterUserView;
    }

    @Override
    public void register(String email, String password, String name, int age) {
        if (NetworkManager.getInstance(Recommender.getAppContext()).isConnected()) {
            RecommenderApi.getInstance().register(email, password, name, age).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    mRegisterUserView.onRegisterUserSuccess(response.body());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    mRegisterUserView.onRegisterUserFailure(t);
                }
            });
        } else
            mRegisterUserView.showNetworkConnectionError();
    }
}
