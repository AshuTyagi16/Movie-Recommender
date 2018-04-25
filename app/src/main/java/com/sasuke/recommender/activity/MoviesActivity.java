package com.sasuke.recommender.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.sasuke.recommender.fragment.MoviesFragment;

/**
 * Created by abc on 4/25/2018.
 */

public class MoviesActivity extends SingleFragmentActivity {

    private static final String EXTRA_CATEGORY = "category";

    private String mCategory;

    public static Intent newIntent(Context context, String category) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(EXTRA_CATEGORY, category);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mCategory = getIntent().getStringExtra(EXTRA_CATEGORY);
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected Fragment createFragment() {
        return MoviesFragment.newInstance(mCategory);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
