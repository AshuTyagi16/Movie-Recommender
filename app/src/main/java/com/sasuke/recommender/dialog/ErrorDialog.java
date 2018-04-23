package com.sasuke.recommender.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by abc on 4/24/2018.
 */

public class ErrorDialog {

    private MaterialDialog dialog;
    private OnButtonsClickListener onButtonsClickListener;

    public ErrorDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        dialog = new MaterialDialog.Builder(context)
                .build();
        setListeners();
    }

    private void setListeners() {
        dialog.getBuilder().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (onButtonsClickListener != null)
                    onButtonsClickListener.onErrorDialogPositiveClick(dialog);
            }
        });

        dialog.getBuilder().onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (onButtonsClickListener != null)
                    onButtonsClickListener.onErrorDialogNegativeClick(dialog);
            }
        });
    }

    public void setTitle(String title) {
        dialog.setTitle(title);
    }

    public void setContent(String content) {
        dialog.setContent(content);
    }

    public void setCancellable(boolean isCancellable) {
        dialog.setCancelable(isCancellable);
    }

    public void setPositiveButtonText(String positiveButtonText) {
        dialog.getBuilder().positiveText(positiveButtonText);
    }

    public void setNegativeButtonText(String negativeButtonText) {
        dialog.getBuilder().negativeText(negativeButtonText);
    }

    public void setOnButtonsClickListener(OnButtonsClickListener onButtonsClickListener) {
        this.onButtonsClickListener = onButtonsClickListener;
    }

    public void showDialog() {
        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public interface OnButtonsClickListener {
        void onErrorDialogPositiveClick(MaterialDialog dialog);

        void onErrorDialogNegativeClick(MaterialDialog dialog);
    }
}
