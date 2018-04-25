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
import com.sasuke.recommender.adapter.SearchResultAdapter;
import com.sasuke.recommender.event.TextChangedEvent;
import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.model.SearchResultPresenterImpl;
import com.sasuke.recommender.presenter.SearchResultPresenter;
import com.sasuke.recommender.view.SearchResultView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by abc on 4/26/2018.
 */

public class SearchResultFragment extends BaseFragment implements SearchResultView {

    @BindView(R.id.rv_search_result)
    RecyclerView mRvSearchResult;
    @BindView(R.id.iv_no_internet)
    ImageView mIvPlaceholder;
    @BindView(R.id.pb_movies)
    CircularProgressBar mPbMovies;

    private SearchResultAdapter mAdapter;
    private SearchResultPresenter mSearchResultPresenter;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchResultPresenter = new SearchResultPresenterImpl(this);
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SearchResultAdapter();
        mRvSearchResult.setAdapter(mAdapter);
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
    public void onErrorDialogPositiveClick(MaterialDialog dialog) {
        errorDialog.dismissDialog();
    }

    @Override
    public void onErrorDialogNegativeClick(MaterialDialog dialog) {
        errorDialog.dismissDialog();
    }

    @Override
    public void onGetMoviesSuccess(ArrayList<Movie> list) {
        mPbMovies.setVisibility(View.GONE);
        if (list.size() == 0) {
            mIvPlaceholder.setImageResource(R.drawable.no_result_found);
            mRvSearchResult.setVisibility(View.GONE);
            mIvPlaceholder.setVisibility(View.VISIBLE);
        } else {
            mAdapter.setMovies(list);
            mIvPlaceholder.setVisibility(View.GONE);
            mRvSearchResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetMoviesFailure(Throwable t) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTextChangedEvent(TextChangedEvent event) {
        mPbMovies.setVisibility(View.VISIBLE);
        mSearchResultPresenter.getMoviesForQuery(event.text);
    }
}
