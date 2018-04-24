package com.sasuke.recommender.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;


/**
 * Created by abc on 3/13/2018.
 */

public class ProgressDialog {

    private MaterialDialog progressDialog;

    public ProgressDialog(Context context, String title, String description, boolean isCancellable) {
        init(context, title, description, isCancellable);
    }

    private void init(Context context, String title, String description, boolean isCancellable) {
        progressDialog = new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(ContextCompat.getColor(context, R.color.black))
                .content(description)
                .contentColor(ContextCompat.getColor(context, R.color.text_light))
                .cancelable(isCancellable)
                .canceledOnTouchOutside(isCancellable)
                .progress(true, 0)
                .build();
    }

    public void showDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
