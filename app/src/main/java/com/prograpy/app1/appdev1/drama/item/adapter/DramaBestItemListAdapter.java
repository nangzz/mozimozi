package com.prograpy.app1.appdev1.drama.item.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;

public class DramaBestItemListAdapter extends RecyclerView.Adapter<DramaBestItemListAdapter.BestItemViewHolder> {

    private View.OnClickListener onClickListener;

    @Override
    public BestItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_drama_best_item_child, parent,false);

        return new BestItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BestItemViewHolder holder, int position) {

        ((BestItemViewHolder)holder).setOnItemClick(onClickListener);

    }

    @Override
    public int getItemCount() {
        return 10;
    }



    public void setOnItemClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public class BestItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout parent;
        private ImageView itemImg;
        private TextView itemPrice;
        private TextView itemName;


        public BestItemViewHolder(View itemView) {
            super(itemView);

            parent = (LinearLayout) itemView.findViewById(R.id.view_parent);
            itemImg = (ImageView) itemView.findViewById(R.id.item_image);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemName = (TextView) itemView.findViewById(R.id.item_name);


        }

        public void setOnItemClick(View.OnClickListener listener){
            parent.setOnClickListener(listener);
        }
    }

}
