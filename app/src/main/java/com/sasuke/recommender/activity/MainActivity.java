package com.sasuke.recommender.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.sasuke.recommender.R;
import com.sasuke.recommender.adapter.TabAdapter;
import com.sasuke.recommender.fragment.AllMoviesFragment;
import com.sasuke.recommender.fragment.CategoriesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewpagertab)
    SmartTabLayout tabLayout;

    private ImageView lastSelectedImageView;
    private ImageView currentSelectedImageView;

    private int lastSelectedTabPosition;

    private static final int POSITION_HOME = 0;
    private static final int POSITION_SEARCH = 1;
    private static final int POSITION_CATEGORIES = 2;
    private static final int POSITION_FAVOURITE = 3;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setCustomTabLayout();
        setCustomTabClickListener();
        setViewPagerListener();
        setupViewPagerAdapter();
        tabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(POSITION_HOME);
        viewPager.setOffscreenPageLimit(5);
        updateTab(POSITION_HOME);
    }

    private void setupViewPagerAdapter() {
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(AllMoviesFragment.newInstance(), getResources().getString(R.string.home));
        tabAdapter.addFragment(CategoriesFragment.newInstance(), getResources().getString(R.string.categories));
        tabAdapter.addFragment(AllMoviesFragment.newInstance(), getResources().getString(R.string.search));
        tabAdapter.addFragment(AllMoviesFragment.newInstance(), getResources().getString(R.string.favourite));
        tabAdapter.addFragment(AllMoviesFragment.newInstance(), getResources().getString(R.string.favourite));
        viewPager.setAdapter(tabAdapter);
    }

    private void setCustomTabLayout() {
        final LayoutInflater inflater = LayoutInflater.from(tabLayout.getContext());

        tabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View itemView = inflater.inflate(R.layout.tab_icon, container, false);
                ImageView icon = (ImageView) itemView.findViewById(R.id.custom_tab_icon);
                icon.setImageResource(getDisabledIconForPosition(position));
                return itemView;
            }
        });
    }

    private void setCustomTabClickListener() {
        tabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                viewPager.setCurrentItem(position);
            }
        });
    }

    private void setViewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateTab(int pos) {
        lastSelectedImageView = (ImageView) tabLayout.getTabAt(lastSelectedTabPosition).findViewById(R.id.custom_tab_icon);
        lastSelectedImageView.setImageResource(getDisabledIconForPosition(lastSelectedTabPosition));

        currentSelectedImageView = (ImageView) tabLayout.getTabAt(pos).findViewById(R.id.custom_tab_icon);
        currentSelectedImageView.setImageResource(getEnabledIconForPosition(pos));

        lastSelectedTabPosition = pos;
    }

    private int getDisabledIconForPosition(int position) {
        switch (position) {
            case POSITION_HOME:
                return R.drawable.ic_home_disabled;
            case POSITION_SEARCH:
                return R.drawable.ic_search_disabled;
            case POSITION_CATEGORIES:
                return R.drawable.ic_home_disabled;
            case POSITION_FAVOURITE:
                return R.drawable.ic_favorite_disabled;
            default:
                return R.drawable.ic_home_disabled;
        }
    }

    private int getEnabledIconForPosition(int position) {
        switch (position) {
            case POSITION_HOME:
                return R.drawable.ic_home;
            case POSITION_SEARCH:
                return R.drawable.ic_search;
            case POSITION_CATEGORIES:
                return R.drawable.ic_home;
            case POSITION_FAVOURITE:
                return R.drawable.ic_favouite;
            default:
                return R.drawable.ic_home;
        }
    }

}
