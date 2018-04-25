package com.sasuke.recommender.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasuke.recommender.R;
import com.sasuke.recommender.util.ImageStubUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 4/24/2018.
 */

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_category_name)
    TextView mTvCategoryName;
    @BindView(R.id.iv_category)
    ImageView mIvCategory;


    public CategoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setCategory(String category) {
        mTvCategoryName.setText(category);
        Picasso.get()
                .load(ImageStubUtils.getImage())
                .placeholder(R.drawable.placeholder_image_loading)
                .error(R.drawable.placeholder_image_error)
                .into(mIvCategory);
    }
}
