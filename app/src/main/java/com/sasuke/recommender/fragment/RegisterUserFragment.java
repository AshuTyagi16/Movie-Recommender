package com.sasuke.recommender.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.model.RegisterUserPresenterImpl;
import com.sasuke.recommender.presenter.RegisterUserPresenter;
import com.sasuke.recommender.view.RegisterUserView;

import es.dmoral.toasty.Toasty;

/**
 * Created by abc on 4/23/2018.
 */

public class RegisterUserFragment extends BaseFragment implements RegisterUserView {

    private RegisterUserPresenter mRegisterUserPresenter;

    public static RegisterUserFragment newInstance() {
        return new RegisterUserFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_register_user;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRegisterUserPresenter = new RegisterUserPresenterImpl(this);
//        mRegisterUserPresenter.register("", "", "", 0);
    }

    @Override
    public void onRegisterUserSuccess() {
        if (getContext() != null)
            Toasty.success(getContext(), getResources().getString(R.string.account_created_successfully)).show();
    }

    @Override
    public void onRegisterUserFailure(Throwable t) {
        if (getContext() != null) {
            Toasty.error(getContext(), getResources().getString(R.string.error)).show();
            errorDialog.setTitle(t.getMessage());
            errorDialog.setPositiveButtonText(getResources().getString(R.string.ok));
        }
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
