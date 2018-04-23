package com.sasuke.recommender.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.activity.MainActivity;
import com.sasuke.recommender.model.LoginPresenterImpl;
import com.sasuke.recommender.model.User;
import com.sasuke.recommender.presenter.LoginPresenter;
import com.sasuke.recommender.view.LoginView;

/**
 * Created by abc on 4/23/2018.
 */

public class LoginFragment extends BaseFragment implements LoginView {

    private LoginPresenter mLoginPresenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginPresenter = new LoginPresenterImpl(this);
        mLoginPresenter.login("", "");
    }

    @Override
    public void onLoginSuccess(User user) {
        startActivity(MainActivity.newIntent(getContext()));
    }

    @Override
    public void onLoginFailure(Throwable t) {
        errorDialog.setTitle(t.getMessage());
        errorDialog.setPositiveButtonText(getResources().getString(R.string.ok));
    }

    @Override
    public void showNetworkConnectionError() {
        errorDialog.setTitle(getResources().getString(R.string.please_connect_internet));
        errorDialog.setPositiveButtonText(getResources().getString(R.string.ok));
    }

    @Override
    public void onErrorDialogPositiveClick(MaterialDialog dialog) {
        errorDialog.dismissDialog();
    }

    @Override
    public void onErrorDialogNegativeClick(MaterialDialog dialog) {
        errorDialog.dismissDialog();
    }
}
