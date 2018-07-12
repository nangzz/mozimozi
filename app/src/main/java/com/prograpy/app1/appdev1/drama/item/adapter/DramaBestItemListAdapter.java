package com.prograpy.app1.appdev1.drama.item.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;
import java.util.List;

public class DramaBestItemListAdapter extends RecyclerView.Adapter<DramaBestItemListAdapter.BestItemViewHolder> {

    private View.OnClickListener onClickListener;
    private ArrayList<ProductVO> dramaProductData = new ArrayList<ProductVO>();

    private Context mContext;


    public DramaBestItemListAdapter(Context context, View.OnClickListener listener) {
        this.mContext = context;
        this.onClickListener = listener;
    }


    @Override
    public BestItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_drama_best_item_child, parent,false);

        return new BestItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BestItemViewHolder holder, int position) {

        ProductVO item = dramaProductData.get(position);

        ((BestItemViewHolder)holder).setOnItemClick(onClickListener);
        ((BestItemViewHolder)holder).setRankImage(position);

        Glide.with(mContext).load(item.p_img).into( ((BestItemViewHolder)holder).itemImg);
        ((BestItemViewHolder)holder).itemName.setText(item.getP_name());
        ((BestItemViewHolder)holder).itemPrice.setText(String.valueOf(item.getP_price()));

        ((BestItemViewHolder)holder).itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return dramaProductData.size();
    }


    public void setDramaProductData(ArrayList<ProductVO> dramaProductData){
        this.dramaProductData = dramaProductData;
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
