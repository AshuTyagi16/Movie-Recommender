package com.sasuke.recommender.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasuke.recommender.R;
import com.sasuke.recommender.event.ItemChangedEvent;
import com.sasuke.recommender.manager.PreferenceManager;
import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.network.RecommenderApi;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.iv_favourite)
    ImageView mIvFavourite;
    @BindView(R.id.pb_favourite)
    CircularProgressBar mPbFavourite;

    private Movie mMovie;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mIvFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIvFavourite.setVisibility(View.GONE);
                mPbFavourite.setVisibility(View.VISIBLE);
                if (mMovie.isFavourite()) {
                    RecommenderApi.getInstance()
                            .unfavouriteMovie(PreferenceManager.getInstance().getUser().getId(), mMovie.getMovieId())
                            .enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    mMovie.setFavourite(false);
                                    EventBus.getDefault().postSticky(new ItemChangedEvent(mMovie));
                                    mIvFavourite.setImageResource(R.drawable.ic_favorite_disabled);
                                    mIvFavourite.setVisibility(View.VISIBLE);
                                    mPbFavourite.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    mIvFavourite.setVisibility(View.VISIBLE);
                                    mPbFavourite.setVisibility(View.GONE);
                                }
                            });
                } else {
                    RecommenderApi.getInstance()
                            .favouriteMovie(PreferenceManager.getInstance().getUser().getId(), mMovie.getMovieId())
                            .enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if (response.body().equals(true)) {
                                        mMovie.setFavourite(true);
                                        EventBus.getDefault().postSticky(new ItemChangedEvent(mMovie));
                                        mIvFavourite.setImageResource(R.drawable.ic_favouite);
                                        mIvFavourite.setVisibility(View.VISIBLE);
                                        mPbFavourite.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    mIvFavourite.setVisibility(View.VISIBLE);
                                    mPbFavourite.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
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
        if (movie.isFavourite())
            mIvFavourite.setImageResource(R.drawable.ic_favouite);
        else
            mIvFavourite.setImageResource(R.drawable.ic_favorite_disabled);
    }
}
