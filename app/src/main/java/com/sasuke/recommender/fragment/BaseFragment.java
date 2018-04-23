package com.sasuke.recommender.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.dialog.ErrorDialog;

import butterknife.ButterKnife;

/**
 * Created by abc on 4/23/2018.
 */

public abstract class BaseFragment extends Fragment implements ErrorDialog.OnButtonsClickListener {


    protected ErrorDialog errorDialog;

    @LayoutRes
    abstract protected int getLayoutResId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        errorDialog = new ErrorDialog(getContext());
        errorDialog.setOnButtonsClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected void hideKeyboard() {
        if (getActivity() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getView() != null) {
            if (imm != null) {
                imm.hideSoftInputFromWindow(getView().getApplicationWindowToken(), 0);
            }
        }
    }
}
