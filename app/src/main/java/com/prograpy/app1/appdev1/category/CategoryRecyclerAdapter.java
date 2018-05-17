package com.prograpy.app1.appdev1.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.view.TopbarView;

import org.w3c.dom.Text;

/**
 * Created by SeungJun on 2018-04-05.
 */

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryRecyclerViewHolder>{

    private String[] category = {"자켓", "상의", "하의", "팬츠", "스커트", "신발", "가방", "악세서리"};
    private ImageView categoryItem;

    @Override
    public CategoryRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_item, parent,false);


        return new CategoryRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryRecyclerViewHolder holder, int position) {

        if(holder instanceof CategoryRecyclerViewHolder){
            ((CategoryRecyclerViewHolder)holder).setCategoryText(category[position]);

        }

    }

    @Override
    public int getItemCount() {
        return category.length;
    }


    public class CategoryRecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryText;


        public CategoryRecyclerViewHolder(View itemView) {
            super(itemView);

            categoryText = (TextView) itemView.findViewById(R.id.category_text);

        }


        private void setCategoryText(String text){
            categoryText.setText(text);
        }
    }

}
