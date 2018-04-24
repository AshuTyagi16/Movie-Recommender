package com.sasuke.recommender.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.sasuke.recommender.R;
import com.sasuke.recommender.fragment.LoginFragment;
import com.sasuke.recommender.fragment.RegisterUserFragment;
import com.sasuke.recommender.manager.PreferenceManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by abc on 4/23/2018.
 */

public class LoginPromptActivity extends AppCompatActivity {

    @BindView(R.id.ll_login_prompt)
    LinearLayout mLlLoginPrompt;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private List<Fragment> mFragmentList;
    private ActionBar mActionBar;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginPromptActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prompt);
        ButterKnife.bind(this);
        if (PreferenceManager.getInstance().isUserLoggedIn()) {
            startActivity(MainActivity.newIntent(this));
        }
        mActionBar = getSupportActionBar();
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onBackPressed() {
        if ((mFragmentManager.findFragmentByTag(LoginFragment.class.getName()) != null
                && mFragmentManager.findFragmentByTag(LoginFragment.class.getName()).isVisible())
                || (mFragmentManager.findFragmentByTag(RegisterUserFragment.class.getName()) != null
                && mFragmentManager.findFragmentByTag(RegisterUserFragment.class.getName()).isVisible()))
            hideAll();
        else
            super.onBackPressed();
    }

    @OnClick(R.id.btn_login)
    public void openLoginFragment() {
        replaceFragment(LoginFragment.newInstance(), getResources().getString(R.string.login));
    }

    @OnClick(R.id.btn_sign_up)
    public void openRegisterFragment() {
        replaceFragment(RegisterUserFragment.newInstance(), getResources().getString(R.string.signup));
    }

    private void replaceFragment(Fragment fragment, String name) {
        mLlLoginPrompt.setVisibility(View.GONE);
        if (mActionBar != null)
            mActionBar.setTitle(name);
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentList = mFragmentManager.getFragments();
        if (mFragmentList != null) {
            for (Fragment f : mFragmentList) {
                if (f != null) {
                    mFragmentTransaction.hide(f);
                }
            }
        }
        Fragment f1 = mFragmentManager.findFragmentByTag(fragment.getClass().getName());
        if (f1 == null) {
            f1 = fragment;
            mFragmentTransaction.add(R.id.container, f1, fragment.getClass().getName());
        } else {
            mFragmentTransaction.show(f1);
        }
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideAll() {
        if (mActionBar != null)
            mActionBar.setTitle(getString(R.string.app_name));
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentList = mFragmentManager.getFragments();
        if (mFragmentList != null) {
            for (Fragment f : mFragmentList) {
                if (f != null) {
                    mFragmentTransaction.hide(f);
                }
            }
        }
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLlLoginPrompt.setVisibility(View.VISIBLE);
    }

}
