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
import com.sasuke.recommender.manager.PreferenceManager;
import com.sasuke.recommender.model.AllMoviesPresenterImpl;
import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.presenter.AllMoviesPresenter;
import com.sasuke.recommender.util.ItemDecorator;
import com.sasuke.recommender.view.AllMoviesView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by abc on 4/23/2018.
 */

public class AllMoviesFragment extends BaseFragment implements AllMoviesView {

    @BindView(R.id.rv_movies)
    RecyclerView mRvMovies;
    @BindView(R.id.iv_no_internet)
    ImageView mIvPlaceholder;
    @BindView(R.id.pb_movies)
    CircularProgressBar mPbMovies;

    private AllMoviesPresenter mAllMoviesPresenter;
    private MoviesAdapter mAdapter;

    private static final int SPAN_COUNT = 2;
    private static final int GRID_SIZE = 100;

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
        mRvMovies.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
        mRvMovies.addItemDecoration(new ItemDecorator(
                getResources().getDimensionPixelSize(R.dimen.item_list_spacing), GRID_SIZE));
        mAdapter = new MoviesAdapter();
        mRvMovies.setAdapter(mAdapter);
        mAllMoviesPresenter = new AllMoviesPresenterImpl(this);
        mAllMoviesPresenter.getAllMovies(PreferenceManager.getInstance().getUser().getId());
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
    public void onGetAllMoviesSuccess(ArrayList<Movie> list) {
        mAdapter.setMovies(list);
        mPbMovies.setVisibility(View.GONE);
        mIvPlaceholder.setVisibility(View.GONE);
        mRvMovies.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetAllMoviesFailure(Throwable t) {
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onItemChangedEvent(ItemChangedEvent event) {
        mAdapter.updateItem(event.movie);
    }


}
