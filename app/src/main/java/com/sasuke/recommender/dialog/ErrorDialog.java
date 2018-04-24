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

    public ErrorDialog(Context context, String title, String content, boolean cancellable, String positiveButtonText, String negativeButtonText) {
        init(context, title, content, cancellable, positiveButtonText, negativeButtonText);
    }

    private void init(Context context, String title, String content, boolean cancellable, String positiveButtonText, String negativeButtonText) {
        dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .cancelable(cancellable)
                .positiveText(positiveButtonText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onButtonsClickListener != null)
                            onButtonsClickListener.onErrorDialogPositiveClick(dialog);
                    }
                })
                .negativeText(negativeButtonText)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onButtonsClickListener != null)
                            onButtonsClickListener.onErrorDialogNegativeClick(dialog);
                    }
                })
                .build();
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
