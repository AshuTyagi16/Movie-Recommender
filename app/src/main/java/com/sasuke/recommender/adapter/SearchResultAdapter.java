package com.sasuke.recommender.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sasuke.recommender.R;
import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.view.SearchResultViewHolder;

import java.util.ArrayList;

/**
 * Created by abc on 4/26/2018.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> implements SearchResultViewHolder.OnItemClickListener {

    private ArrayList<Movie> mResults;
    private OnItemClickListener onItemClickListener;

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        holder.setResult(mResults.get(position));
        holder.setOnItemClickListsner(this);
    }

    @Override
    public int getItemCount() {
        return mResults == null ? 0 : mResults.size();
    }

    public void setMovies(ArrayList<Movie> list) {
        mResults = list;
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Movie movie) {
        onItemClickListener.onItemClick(movie);
    }

    public void setOnItemClickListsner(OnItemClickListener onItemClickListsner) {
        this.onItemClickListener = onItemClickListsner;
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}
