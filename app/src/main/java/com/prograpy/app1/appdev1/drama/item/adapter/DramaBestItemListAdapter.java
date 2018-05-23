package com.prograpy.app1.appdev1.drama.item.adapter;

import android.content.Context;
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

    private Context mContext;

    @Override
    public BestItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_drama_best_item_child, parent,false);

        return new BestItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BestItemViewHolder holder, int position) {

        ((BestItemViewHolder)holder).setOnItemClick(onClickListener);
        ((BestItemViewHolder)holder).setRankImage(position);

    }

    @Override
    public int getItemCount() {
        return 10;
    }



    public void setOnItemClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public class BestItemViewHolder extends RecyclerView.ViewHolder {

        private int[] resRank = {R.drawable.img_best_n1, R.drawable.img_best_n2, R.drawable.img_best_n3, R.drawable.img_best_n4,
                                R.drawable.img_best_n5, R.drawable.img_best_n6, R.drawable.img_best_n7, R.drawable.img_best_n8,
                R.drawable.img_best_n9, R.drawable.img_best_n10};

        private LinearLayout parent;
        private ImageView itemImg;
        private ImageView itemRankImg;
        private TextView itemPrice;
        private TextView itemName;


        public BestItemViewHolder(View itemView) {
            super(itemView);

            parent = (LinearLayout) itemView.findViewById(R.id.view_parent);
            itemImg = (ImageView) itemView.findViewById(R.id.item_image);
            itemRankImg = (ImageView) itemView.findViewById(R.id.item_rank_img);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
        }

        public void setOnItemClick(View.OnClickListener listener){
            parent.setOnClickListener(listener);
        }


        public void setRankImage(int rank){
            itemRankImg.setImageDrawable(mContext.getResources().getDrawable(resRank[rank]));
        }
    }

}
