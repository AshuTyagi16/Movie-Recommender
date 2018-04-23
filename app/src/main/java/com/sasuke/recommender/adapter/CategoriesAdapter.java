package com.sasuke.recommender.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sasuke.recommender.R;
import com.sasuke.recommender.view.CategoriesViewHolder;

import java.util.ArrayList;

/**
 * Created by abc on 4/24/2018.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    private ArrayList<String> mCategoryList;

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoriesViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.card_genres, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        holder.setCategory(mCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mCategoryList == null ? 0 : this.mCategoryList.size();
    }

    public void setCategories(ArrayList<String> list) {
        this.mCategoryList = list;
        notifyDataSetChanged();
    }
}
