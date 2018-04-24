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
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.sasuke.recommender.R;
import com.sasuke.recommender.dialog.ErrorDialog;
import com.sasuke.recommender.dialog.ProgressDialog;
import com.sasuke.recommender.manager.PreferenceManager;
import com.sasuke.recommender.model.RegisterUserPresenterImpl;
import com.sasuke.recommender.model.User;
import com.sasuke.recommender.presenter.RegisterUserPresenter;
import com.sasuke.recommender.util.ValidationListener;
import com.sasuke.recommender.view.RegisterUserView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by abc on 4/23/2018.
 */

public class RegisterUserFragment extends BaseFragment implements RegisterUserView {

    @NotEmpty
    @BindView(R.id.et_name)
    EditText mEtName;
    @NotEmpty
    @Email
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @NotEmpty
    @BindView(R.id.et_age)
    EditText mEtAge;
    @NotEmpty
    @Password
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @NotEmpty
    @ConfirmPassword
    @BindView(R.id.et_confirm_password)
    EditText mEtConfirmPassword;

    private RegisterUserPresenter mRegisterUserPresenter;
    private Validator validator;

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
        validator = new Validator(this);
        progressDialog = new ProgressDialog(getContext(), getResources().getString(R.string.please_wait),
                null, true);
        mRegisterUserPresenter = new RegisterUserPresenterImpl(this);

        validator.setValidationListener(new ValidationListener() {
            @Override
            public Context getContext() {
                return getActivity();
            }

            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                mRegisterUserPresenter.register(mEtEmail.getText().toString(),
                        mEtPassword.getText().toString(),
                        mEtName.getText().toString(),
                        Integer.valueOf(mEtAge.getText().toString()));
            }

            @Override
            public void onValidationFailed(List<ValidationError> list) {
                super.onValidationFailed(list);
                progressDialog.dismissDialog();
            }
        });
    }

    @OnClick(R.id.btn_sign_up)
    public void signUp() {
        hideKeyboard();
        progressDialog.showDialog();
        validator.validate();
    }

    @Override
    public void onRegisterUserSuccess(User user) {
        if (getContext() != null) {
            progressDialog.dismissDialog();
            if (user.isSuccess()) {
                saveUser(user);
                Toasty.success(getContext(), getResources().getString(R.string.account_created_successfully)).show();
            } else
                Toasty.error(getContext(), user.getError()).show();
        }
    }

    @Override
    public void onRegisterUserFailure(Throwable t) {
        if (getContext() != null) {
            progressDialog.dismissDialog();
            Toasty.error(getContext(), getResources().getString(R.string.error)).show();
            errorDialog = new ErrorDialog(getContext(), t.getMessage(), null,
                    true, getResources().getString(R.string.ok), null);
            errorDialog.showDialog();
        }
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

    private void saveUser(User user) {
        PreferenceManager.getInstance().setUser(user);
    }
}
