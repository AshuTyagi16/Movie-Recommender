package com.sasuke.recommender.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sasuke.recommender.R;
import com.sasuke.recommender.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 4/23/2018.
 */

//TODO:CONVERT DIALOG TO MVP..
public class RateMovieDialog {

    private MaterialDialog dialog;
    private View view;

    private OnRatingGivenListener onRatingGivenListener;
    private Movie mMovie;
    private float rating;

    public RateMovieDialog(Context context, Movie movie) {
        mMovie = movie;
        init(context);
    }

    private void init(Context context) {
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_rate_movie, true)
                .build();
        view = dialog.getCustomView();
        setMovieImage(mMovie.getThumbnail());
        setMovieName(mMovie.getTitle());
        setMovieGenre(mMovie.getGenres());
        setListeners();
    }

    private void setMovieName(String movieName) {
        TextView tvMovieName = (TextView) view.findViewById(R.id.tv_movie_name);
        tvMovieName.setText(movieName);
    }

    private void setMovieGenre(List<String> movieGenre) {
        String genre = "";
        for (int i = 0; i < movieGenre.size(); i++) {
            genre = genre.concat(movieGenre.get(i)).concat("\n");
        }
        TextView tvMovieGenre = (TextView) view.findViewById(R.id.tv_movie_genre);
        tvMovieGenre.setText(genre);
    }

    private void setMovieImage(String movieImage) {
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
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v;
            }
        });

        Button button = (Button) view.findViewById(R.id.btn_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRatingGivenListener != null)
                    onRatingGivenListener.onRatingGiven(mMovie, rating);
            }
        });
    }

    public void setOnRatingGivenListener(OnRatingGivenListener onRatingGivenListener) {
        this.onRatingGivenListener = onRatingGivenListener;
    }

    public interface OnRatingGivenListener {
        void onRatingGiven(Movie movie, float rating);
    }
}
