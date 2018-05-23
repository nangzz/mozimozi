package com.prograpy.app1.appdev1.dramaItem.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;

public class DramaItemListAdapter extends RecyclerView.Adapter<DramaItemListAdapter.DramaItemViewHolder> {


    @Override
    public DramaItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_drama_item_child, parent,false);

        return new DramaItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DramaItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }



    public class DramaItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemImg;
        private TextView itemPrice;
        private TextView itemName;

        public DramaItemViewHolder(View itemView) {
            super(itemView);

            itemImg = (ImageView) itemView.findViewById(R.id.item_image);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemName = (TextView) itemView.findViewById(R.id.item_name);

        }
    }

}
