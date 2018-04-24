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

    private static final int POSITION_HOME = 0;

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
        setupViewPagerAdapter();
        tabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(POSITION_HOME);
    }

    private void setupViewPagerAdapter() {
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(AllMoviesFragment.newInstance(), getResources().getString(R.string.home));
        tabAdapter.addFragment(CategoriesFragment.newInstance(), getResources().getString(R.string.categories));
        tabAdapter.addFragment(AllMoviesFragment.newInstance(), getResources().getString(R.string.search));
        tabAdapter.addFragment(AllMoviesFragment.newInstance(), getResources().getString(R.string.trending));
        viewPager.setAdapter(tabAdapter);
    }

    private void setCustomTabLayout() {
        final LayoutInflater inflater = LayoutInflater.from(tabLayout.getContext());

        tabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View itemView = inflater.inflate(R.layout.tab_icon, container, false);
                ImageView icon = (ImageView) itemView.findViewById(R.id.custom_tab_icon);
                icon.setImageResource(R.drawable.ic_check_white_48dp);
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


}
