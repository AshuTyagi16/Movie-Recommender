package com.sasuke.recommender.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.adapter.CategoriesAdapter;
import com.sasuke.recommender.dialog.ErrorDialog;
import com.sasuke.recommender.model.CategoriesPresenterImpl;
import com.sasuke.recommender.presenter.CategoriesPresenter;
import com.sasuke.recommender.view.CategoriesView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by abc on 4/23/2018.
 */

public class CategoriesFragment extends BaseFragment implements CategoriesView {

    @BindView(R.id.rv_categories)
    RecyclerView mRvCategories;

    private CategoriesPresenter mCategoriesPresenter;
    private CategoriesAdapter mAdapter;

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_categories;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CategoriesAdapter();
        mRvCategories.setAdapter(mAdapter);
        mCategoriesPresenter = new CategoriesPresenterImpl(this);
        mCategoriesPresenter.getCategories();
    }

    @Override
    public void onGetCategoriesSuccess(ArrayList<String> list) {
        mAdapter.setCategories(list);
    }

    @Override
    public void onGetCategoriesFailure(Throwable t) {
        errorDialog = new ErrorDialog(getContext(), t.getMessage(), "",
                true, getResources().getString(R.string.ok), "");
        errorDialog.showDialog();
    }

    @Override
    public void showNetworkConnectionError() {
        errorDialog = new ErrorDialog(getContext(), getResources().getString(R.string.please_connect_internet), "",
                true, getResources().getString(R.string.ok), "");
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
