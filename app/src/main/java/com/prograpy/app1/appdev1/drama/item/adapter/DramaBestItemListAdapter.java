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
import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.utils.Utils;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;
import java.util.List;

public class DramaBestItemListAdapter extends RecyclerView.Adapter<DramaBestItemListAdapter.BestItemViewHolder> {

    private View.OnClickListener onClickListener;
    private View.OnClickListener onHeartClickListener;
    private ArrayList<ProductVO> dramaProductData = new ArrayList<ProductVO>();

    private Context mContext;


    public DramaBestItemListAdapter(Context context, View.OnClickListener listener, View.OnClickListener heartListener) {
        this.mContext = context;
        this.onClickListener = listener;
        onHeartClickListener = heartListener;
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

        Glide.with(mContext).load(item.getP_img()).into( ((BestItemViewHolder)holder).itemImg);
        ((BestItemViewHolder)holder).itemName.setText(item.getP_name());
        ((BestItemViewHolder)holder).itemDrama.setText(item.getD_name());
        ((BestItemViewHolder)holder).itemPrice.setText(Utils.moneyFormatToWon(item.getP_price()) + "원");
        ((BestItemViewHolder)holder).itemHeart.setOnClickListener(onHeartClickListener);
        ((BestItemViewHolder)holder).itemZzim.setOnClickListener(onHeartClickListener);
        ((BestItemViewHolder)holder).itemHeart.bringToFront();
        ((BestItemViewHolder)holder).itemHeart.setTag(item);
        ((BestItemViewHolder)holder).itemZzim.setTag(item);

        if(DbController.isOverlapData(mContext, item.getP_id())){
            ((BestItemViewHolder)holder).itemHeart.setSelected(true);
            ((BestItemViewHolder)holder).itemZzim.setText("찜 해제하기");
        }else{
            ((BestItemViewHolder)holder).itemHeart.setSelected(false);
            ((BestItemViewHolder)holder).itemZzim.setText("찜하기");
        }

        ((BestItemViewHolder)holder).itemDetail.setTag(item);
        ((BestItemViewHolder)holder).itemDetail.setOnClickListener(onClickListener);

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
        private ImageView itemHeart;
        private TextView itemZzim;
        private TextView itemDetail;
        private TextView itemDrama;


        public BestItemViewHolder(View itemView) {
            super(itemView);

            parent = (LinearLayout) itemView.findViewById(R.id.view_parent);
            itemDrama = (TextView) itemView.findViewById(R.id.item_drama_name);
            itemImg = (ImageView) itemView.findViewById(R.id.item_image);
            itemRankImg = (ImageView) itemView.findViewById(R.id.item_rank_img);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemHeart = (ImageView) itemView.findViewById(R.id.heart_btn);
            itemZzim = (TextView) itemView.findViewById(R.id.item_zzim);
            itemDetail = (TextView) itemView.findViewById(R.id.item_url);
        }

        public void setOnItemClick(View.OnClickListener listener){
            parent.setOnClickListener(listener);
        }


        public void setRankImage(int rank){
            itemRankImg.setImageDrawable(mContext.getResources().getDrawable(resRank[rank]));
        }
    }

}
