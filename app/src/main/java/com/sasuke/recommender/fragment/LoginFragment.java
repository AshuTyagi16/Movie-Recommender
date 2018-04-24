package com.sasuke.recommender.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.sasuke.recommender.R;
import com.sasuke.recommender.activity.MainActivity;
import com.sasuke.recommender.dialog.ErrorDialog;
import com.sasuke.recommender.dialog.ProgressDialog;
import com.sasuke.recommender.model.LoginPresenterImpl;
import com.sasuke.recommender.model.User;
import com.sasuke.recommender.presenter.LoginPresenter;
import com.sasuke.recommender.util.ValidationListener;
import com.sasuke.recommender.view.LoginView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by abc on 4/23/2018.
 */

public class LoginFragment extends BaseFragment implements LoginView {

    @Email
    @NotEmpty
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @Password
    @NotEmpty
    @BindView(R.id.et_password)
    EditText mEtPassword;

    private LoginPresenter mLoginPresenter;
    private Validator validator;

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
        progressDialog = new ProgressDialog(getContext(), getResources().getString(R.string.please_wait),
                null, true);
        validator = new Validator(this);
        validator.setValidationListener(new ValidationListener() {
            @Override
            public Context getContext() {
                return getActivity();
            }

            @Override
            public void onValidationSucceeded() {
                mLoginPresenter.login(mEtEmail.getText().toString(), mEtPassword.getText().toString());
            }

            @Override
            public void onValidationFailed(List<ValidationError> list) {
                super.onValidationFailed(list);
                progressDialog.dismissDialog();
            }
        });
        mLoginPresenter = new LoginPresenterImpl(this);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        hideKeyboard();
        progressDialog.showDialog();
        validator.validate();
    }

    @Override
    public void onLoginSuccess(User user) {
        if (getContext() != null) {
            progressDialog.dismissDialog();
            if (user.isSuccess()) {
                Toasty.success(getContext(), getResources().getString(R.string.login_success)).show();
                startActivity(MainActivity.newIntent(getContext()));
            } else
                Toasty.error(getContext(), getResources().getString(R.string.login_error)).show();
        }
    }

    @Override
    public void onLoginFailure(Throwable t) {
        progressDialog.dismissDialog();
        errorDialog = new ErrorDialog(getContext(), t.getMessage(), null,
                true, getResources().getString(R.string.ok), null);
        errorDialog.showDialog();
    }

    @Override
    public void showNetworkConnectionError() {
        progressDialog.dismissDialog();
        errorDialog = new ErrorDialog(getContext(), getResources().getString(R.string.no_network_available),
                getResources().getString(R.string.message_network_error), true,
                getResources().getString(R.string.ok), null);
        errorDialog.showDialog();
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
