package com.sasuke.recommender.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.event.TextChangedEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import xyz.sahildave.widget.SearchViewLayout;

/**
 * Created by abc on 4/26/2018.
 */

public class SearchFragment extends BaseFragment {

    @BindView(R.id.search_view_container)
    SearchViewLayout searchViewLayout;

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
        if (getActivity() != null)
            searchViewLayout.setExpandedContentSupportFragment(getActivity(), SearchResultFragment.newInstance());

        if (getContext() != null) {
            ColorDrawable collapsed = new ColorDrawable(
                    ContextCompat.getColor(getContext(), R.color.default_color_expanded));

            ColorDrawable expanded = new ColorDrawable(
                    ContextCompat.getColor(getContext(), R.color.default_color_expanded));

            searchViewLayout.setTransitionDrawables(collapsed, expanded);
            searchViewLayout.setCollapsedHint(getString(R.string.search_movie));
            searchViewLayout.setExpandedHint(getString(R.string.enter_movie_name));
        }

        searchViewLayout.setSearchBoxListener(new SearchViewLayout.SearchBoxListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EventBus.getDefault().post(new TextChangedEvent(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onErrorDialogPositiveClick(MaterialDialog dialog) {

    }

    @Override
    public void onErrorDialogNegativeClick(MaterialDialog dialog) {

    }
}
