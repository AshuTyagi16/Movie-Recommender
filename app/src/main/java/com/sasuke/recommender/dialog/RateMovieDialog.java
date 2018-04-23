package com.sasuke.recommender.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.squareup.picasso.Picasso;

/**
 * Created by abc on 4/23/2018.
 */

//TODO:CONVERT DIALOG TO MVP..
public class RateMovieDialog {

    private MaterialDialog dialog;
    private View view;

    private OnRatingGivenListener onRatingGivenListener;

    public RateMovieDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_rate_movie, false)
                .build();
        view = dialog.getCustomView();
        setListeners();
    }

    public void setMovieName(String movieName) {
        TextView tvMovieName = (TextView) view.findViewById(R.id.tv_movie_name);
        tvMovieName.setText(movieName);
    }

    public void setMovieGenre(String movieGenre) {
        TextView tvMovieGenre = (TextView) view.findViewById(R.id.tv_movie_genre);
        tvMovieGenre.setText(movieGenre);
    }

    public void setMovieImage(int movieImage) {
        ImageView ivMovie = (ImageView) view.findViewById(R.id.iv_movie_image);
        Picasso.get()
                .load(movieImage)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivMovie);
    }

    public void showDialog() {
        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    private void setListeners() {
        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rb_movie);
        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRatingGivenListener != null)
                    onRatingGivenListener.onRatingGiven(ratingBar.getRating());
            }
        });
    }

    public void setOnRatingGivenListener(OnRatingGivenListener onRatingGivenListener) {
        this.onRatingGivenListener = onRatingGivenListener;
    }

    private interface OnRatingGivenListener {
        void onRatingGiven(float rating);
    }
}
