package com.sasuke.recommender.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.adapter.MoviesAdapter;
import com.sasuke.recommender.model.AllMoviesPresenterImpl;
import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.presenter.AllMoviesPresenter;
import com.sasuke.recommender.view.AllMoviesView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by abc on 4/23/2018.
 */

public class AllMoviesFragment extends BaseFragment implements AllMoviesView {

    @BindView(R.id.rv_movies)
    RecyclerView mRvMovies;

    private AllMoviesPresenter mAllMoviesPresenter;
    private MoviesAdapter mAdapter;

    public static AllMoviesFragment newInstance() {
        return new AllMoviesFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movies;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MoviesAdapter();
        mRvMovies.setAdapter(mAdapter);
        mAllMoviesPresenter = new AllMoviesPresenterImpl(this);
        mAllMoviesPresenter.getAllMovies();
    }

    @Override
    public void onGetAllMoviesSuccess(ArrayList<Movie> list) {
        mAdapter.setMovies(list);
    }

    @Override
    public void onGetAllMoviesFailure(Throwable t) {
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
