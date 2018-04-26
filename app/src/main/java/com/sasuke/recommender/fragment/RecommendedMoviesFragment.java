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
import com.sasuke.recommender.event.ItemChangedEvent;
import com.sasuke.recommender.event.RecommendationDatasetReadyEvent;
import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.model.RecommendedMoviesPresenterImpl;
import com.sasuke.recommender.presenter.RecommendedMoviesPresenter;
import com.sasuke.recommender.util.ItemDecorator;
import com.sasuke.recommender.view.RecommendedMoviesView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by abc on 4/23/2018.
 */

public class RecommendedMoviesFragment extends BaseFragment implements RecommendedMoviesView {

    @BindView(R.id.rv_movies)
    RecyclerView mRvMovies;
    @BindView(R.id.iv_no_internet)
    ImageView mIvPlaceholder;
    @BindView(R.id.pb_movies)
    CircularProgressBar mPbMovies;

    private MoviesAdapter mAdapter;

    private static final int SPAN_COUNT = 2;
    private static final int GRID_SIZE = 100;
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
        return R.layout.fragment_movies;
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
        mRvMovies.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
        mRvMovies.addItemDecoration(new ItemDecorator(
                getResources().getDimensionPixelSize(R.dimen.item_list_spacing), GRID_SIZE));
        mAdapter = new MoviesAdapter();
        mRvMovies.setAdapter(mAdapter);
        mRecommendedMoviesPresenter = new RecommendedMoviesPresenterImpl(this);
        if (commaSeperatedMovieIds.equals("")) {
            showPlaceholderView();
        } else
            mRecommendedMoviesPresenter.getRecommendedMovies(commaSeperatedMovieIds);
    }

    @Override
    public void onGetRecommendedMoviesSuccess(ArrayList<Movie> list) {
        mAdapter.setMovies(list);
        mIvPlaceholder.setVisibility(View.GONE);
        mRvMovies.setVisibility(View.VISIBLE);
        mPbMovies.setVisibility(View.GONE);
    }

    @Override
    public void onGetRecommendedMoviesFailure(Throwable t) {
        mPbMovies.setVisibility(View.GONE);
        mIvPlaceholder.setImageResource(R.drawable.placeholder_error_new);
        mIvPlaceholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onItemChangedEvent(ItemChangedEvent event) {
        mAdapter.updateItem(event.movie);
    }

    private void showPlaceholderView() {
        if (getContext() != null)
            Toasty.normal(getContext(), "Please rate at least two movies to see the recommendations").show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onItemChangedEvent(RecommendationDatasetReadyEvent event) {
        mRecommendedMoviesPresenter.getRecommendedMovies(event.commaSeperatedMovieId);
    }
}
