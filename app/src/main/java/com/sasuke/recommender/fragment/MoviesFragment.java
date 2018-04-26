package com.sasuke.recommender.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.adapter.MoviesAdapter;
import com.sasuke.recommender.manager.PreferenceManager;
import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.model.MoviesPresenterImpl;
import com.sasuke.recommender.presenter.MoviesPresenter;
import com.sasuke.recommender.util.ItemDecorator;
import com.sasuke.recommender.view.MoviesView;

import java.util.ArrayList;

import butterknife.BindView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by abc on 4/25/2018.
 */

public class MoviesFragment extends BaseFragment implements MoviesView {

    @BindView(R.id.rv_movies)
    RecyclerView mRvMovies;
    @BindView(R.id.iv_no_internet)
    ImageView mIvPlaceholder;
    @BindView(R.id.pb_movies)
    CircularProgressBar mPbMovies;

    private MoviesPresenter mMoviesPresenter;
    private MoviesAdapter mAdapter;

    private static final int SPAN_COUNT = 2;
    private static final int GRID_SIZE = 100;
    private static final String EXTRA_CATEGORY = "category";

    private String mCategory;

    public static MoviesFragment newInstance(String category) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CATEGORY, category);
        MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movies;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mCategory = getArguments().getString(EXTRA_CATEGORY);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvMovies.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
        mRvMovies.addItemDecoration(new ItemDecorator(
                getResources().getDimensionPixelSize(R.dimen.item_list_spacing), GRID_SIZE));
        mAdapter = new MoviesAdapter();
        mRvMovies.setAdapter(mAdapter);
        mMoviesPresenter = new MoviesPresenterImpl(this);
        mMoviesPresenter.getMoviesForCategory(PreferenceManager.getInstance().getUser().getId(), mCategory);
    }

    @Override
    public void onGetMoviesSuccess(ArrayList<Movie> list) {
        mAdapter.setMovies(list);
        mPbMovies.setVisibility(View.GONE);
        mIvPlaceholder.setVisibility(View.GONE);
        mRvMovies.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGerMoviesFailure(Throwable t) {
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
