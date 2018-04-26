package com.sasuke.recommender.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.adapter.MoviesAdapter;
import com.sasuke.recommender.event.ItemChangedEvent;
import com.sasuke.recommender.event.MovieRatedEvent;
import com.sasuke.recommender.event.RecommendationDatasetReadyEvent;
import com.sasuke.recommender.event.TextChangedEvent;
import com.sasuke.recommender.model.MovieRating;
import com.sasuke.recommender.util.ItemDecorator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import xyz.sahildave.widget.SearchViewLayout;

/**
 * Created by abc on 4/26/2018.
 */

public class SearchFragment extends BaseFragment {

    @BindView(R.id.search_view_container)
    SearchViewLayout searchViewLayout;
    @BindView(R.id.btn_recommend)
    Button mBtnRecommend;
    @BindView(R.id.rv_rated_movies)
    RecyclerView mRvRatedMovies;

    private ArrayList<MovieRating> mMovieRatingList;

    private MoviesAdapter mAdapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvRatedMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRvRatedMovies.addItemDecoration(new ItemDecorator(
                getResources().getDimensionPixelSize(R.dimen.item_list_spacing), 100));
        mAdapter = new MoviesAdapter();
        mRvRatedMovies.setAdapter(mAdapter);

        mMovieRatingList = new ArrayList<>();
        if (getActivity() != null)
            searchViewLayout.setExpandedContentSupportFragment(getActivity(), SearchResultFragment.newInstance());

        if (getContext() != null) {
            ColorDrawable collapsed = new ColorDrawable(
                    ContextCompat.getColor(getContext(), R.color.default_color_expanded));

            ColorDrawable expanded = new ColorDrawable(
                    ContextCompat.getColor(getContext(), R.color.default_color_expanded));

            searchViewLayout.setTransitionDrawables(collapsed, expanded);
            searchViewLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            searchViewLayout.setCollapsedHint(getString(R.string.search_movie));
            searchViewLayout.setExpandedHint(getString(R.string.enter_movie_name));
        }

        searchViewLayout.setSearchBoxListener(new SearchViewLayout.SearchBoxListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                EventBus.getDefault().post(new TextChangedEvent(s.toString()));
            }
        });

        searchViewLayout.setOnToggleAnimationListener(new SearchViewLayout.OnToggleAnimationListener() {
            @Override
            public void onStart(boolean expanding) {
                if (searchViewLayout.isExpanded()) {
                    mBtnRecommend.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFinish(boolean expanded) {
                if (!searchViewLayout.isExpanded()) {
                    mBtnRecommend.setVisibility(View.VISIBLE);
                }
            }
        });

        mBtnRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMovieRatingList.size() > 1) {
                    String commaSeperatedMovieId = "";
                    for (int i = 0; i < mMovieRatingList.size(); i++) {
                        if (i == mMovieRatingList.size() - 1)
                            commaSeperatedMovieId = commaSeperatedMovieId.concat("" + mMovieRatingList.get(i).getMovie().getMovieId());
                        else
                            commaSeperatedMovieId = commaSeperatedMovieId.concat("" + mMovieRatingList.get(i).getMovie().getMovieId()).concat(",");
                    }
                    EventBus.getDefault().postSticky(new RecommendationDatasetReadyEvent(commaSeperatedMovieId));
                }
            }
        });
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

    }

    @Override
    public void onErrorDialogNegativeClick(MaterialDialog dialog) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onItemChangedEvent(MovieRatedEvent event) {
        mBtnRecommend.setVisibility(View.VISIBLE);
        searchViewLayout.collapse();
        mMovieRatingList.add(event.movieRating);
        mAdapter.setRatedMovies(event.movieRating);
        if (getContext() != null && mMovieRatingList.size() > 1) {
            mBtnRecommend.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onItemChangedEvent(ItemChangedEvent event) {
        mAdapter.updateItem(event.movie);
    }
}
