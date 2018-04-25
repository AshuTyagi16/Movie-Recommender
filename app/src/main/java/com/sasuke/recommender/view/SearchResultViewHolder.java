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
 * Created by abc on 4/26/2018.
 */

public class SearchResultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_movie_image)
    ImageView mIvMovie;
    @BindView(R.id.tv_movie_name)
    TextView mTvMovieName;

    public SearchResultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setResult(Movie movie) {
        mTvMovieName.setText(movie.getTitle());
        if (movie.getThumbnail() != null) {
            Picasso.get()
                    .load(movie.getThumbnail())
                    .placeholder(R.drawable.placeholder_image_loading)
                    .error(R.drawable.placeholder_image_error)
                    .into(mIvMovie);
        }
    }
}
