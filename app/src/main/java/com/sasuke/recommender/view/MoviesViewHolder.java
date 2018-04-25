package com.sasuke.recommender.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasuke.recommender.R;
import com.sasuke.recommender.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 4/24/2018.
 */

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_movie_image)
    ImageView mIvMovie;
    @BindView(R.id.tv_movie_name)
    TextView mTvMovieName;
    @BindView(R.id.tv_movie_genre)
    TextView mTvMovieGenre;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setMovie(Movie movie) {
        mTvMovieName.setText(movie.getTitle());
        String genres = "";
        for (int i = 0; i < movie.getGenres().size(); i++) {
            genres = genres.concat(movie.getGenres().get(i)).concat("\n");
        }
        mTvMovieGenre.setText(genres);
        Picasso.get()
                .load(movie.getThumbnail())
                .placeholder(R.drawable.placeholder_image_loading)
                .error(R.drawable.placeholder_image_error)
                .into(mIvMovie);
    }
}
