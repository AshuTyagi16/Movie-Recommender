package com.sasuke.recommender.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sasuke.recommender.R;
import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.view.MoviesViewHolder;

import java.util.ArrayList;

/**
 * Created by abc on 4/24/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private ArrayList<Movie> mMovieList;

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.setMovie(mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mMovieList == null ? 0 : this.mMovieList.size();
    }

    public void setMovies(ArrayList<Movie> list) {
        this.mMovieList = list;
        notifyDataSetChanged();
    }
}
