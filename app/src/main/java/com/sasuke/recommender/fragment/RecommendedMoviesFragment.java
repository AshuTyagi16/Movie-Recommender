package com.sasuke.recommender.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.model.RecommendedMoviesPresenterImpl;
import com.sasuke.recommender.presenter.RecommendedMoviesPresenter;
import com.sasuke.recommender.view.RecommendedMoviesView;

/**
 * Created by abc on 4/23/2018.
 */

public class RecommendedMoviesFragment extends BaseFragment implements RecommendedMoviesView {

    private static final String EXTRA_MOVIE_ID = "movie_ids";

    private String commaSeperatedMovieIds;

    private RecommendedMoviesPresenter mRecommendedMoviesPresenter;

    public static RecommendedMoviesFragment newInstance(String commaSeperatedMovieIds) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE_ID, commaSeperatedMovieIds);
        RecommendedMoviesFragment fragment = new RecommendedMoviesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            commaSeperatedMovieIds = getArguments().getString(EXTRA_MOVIE_ID);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecommendedMoviesPresenter = new RecommendedMoviesPresenterImpl(this);
        mRecommendedMoviesPresenter.getResommendedMovies(commaSeperatedMovieIds);
    }

    @Override
    public void onGetRecommendedMoviesSuccess() {

    }

    @Override
    public void onGetRecommendedMoviesFailure() {

    }

    @Override
    public void showNetworkConnectionError() {
        errorDialog.setTitle(getResources().getString(R.string.please_connect_internet));
        errorDialog.setPositiveButtonText(getResources().getString(R.string.ok));
    }

    @Override
    public void onErrorDialogPositiveClick(MaterialDialog dialog) {

    }

    @Override
    public void onErrorDialogNegativeClick(MaterialDialog dialog) {

    }
}
