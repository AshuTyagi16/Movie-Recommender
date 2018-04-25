package com.sasuke.recommender.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.adapter.CategoriesAdapter;
import com.sasuke.recommender.model.CategoriesPresenterImpl;
import com.sasuke.recommender.presenter.CategoriesPresenter;
import com.sasuke.recommender.view.CategoriesView;

import java.util.ArrayList;

import butterknife.BindView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by abc on 4/23/2018.
 */

public class CategoriesFragment extends BaseFragment implements CategoriesView {

    @BindView(R.id.rv_categories)
    RecyclerView mRvCategories;
    @BindView(R.id.iv_no_internet)
    ImageView mIvPlaceholder;
    @BindView(R.id.pb_categories)
    CircularProgressBar mPbMovies;

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
        mPbMovies.setVisibility(View.GONE);
        mIvPlaceholder.setVisibility(View.GONE);
        mRvCategories.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetCategoriesFailure(Throwable t) {
        mPbMovies.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_error_new);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNetworkConnectionError() {
        mPbMovies.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_no_internet_connection);
        mIvPlaceholder.setVisibility(View.VISIBLE);
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
