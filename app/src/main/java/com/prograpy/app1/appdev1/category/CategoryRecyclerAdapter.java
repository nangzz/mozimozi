package com.prograpy.app1.appdev1.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prograpy.app1.appdev1.R;

/**
 * Created by SeungJun on 2018-04-05.
 */

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryRecyclerViewHolder> {


    @Override
    public CategoryRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_item, parent,false);

        return new CategoryRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryRecyclerViewHolder holder, int position) {

        if(holder instanceof CategoryRecyclerViewHolder){


        }

    }

    @Override
    public int getItemCount() {
        return 8;
    }


    public class CategoryRecyclerViewHolder extends RecyclerView.ViewHolder{



        public CategoryRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }

}
