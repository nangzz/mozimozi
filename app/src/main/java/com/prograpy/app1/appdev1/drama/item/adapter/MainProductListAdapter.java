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

public class MainProductListAdapter extends RecyclerView.Adapter<MainProductListAdapter.DramaItemViewHolder> {

    private Context context;
    private List<ProductVO> ProductData = new ArrayList<ProductVO>();
    private int item_layout = 0;

    private View.OnClickListener onClickListener;
    private View.OnClickListener onHeartClickListener;


   public MainProductListAdapter(Context context, View.OnClickListener listener, View.OnClickListener heartListener) {
        this.context = context;
       this.onClickListener = listener;
       onHeartClickListener = heartListener;
    }

    public void setProductData(List<ProductVO> ProductData){
        this.ProductData = ProductData;
    }

    @Override
    public DramaItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_drama_item_child, parent,false);

        return new DramaItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DramaItemViewHolder holder, int position) {

        ProductVO item = ProductData.get(position);

        Glide.with(context).load(item.p_img).into(holder.itemImg);
        ((DramaItemViewHolder)holder).itemName.setText(item.getP_name());
        ((DramaItemViewHolder)holder).itemPrice.setText(String.valueOf(item.getP_price()));
        ((DramaItemViewHolder)holder).itemHeart.setOnClickListener(onHeartClickListener);
        ((DramaItemViewHolder)holder).itemHeart.setTag(item);

        ((DramaItemViewHolder)holder).itemView.setTag(item);
        ((DramaItemViewHolder)holder).setOnItemClick(onClickListener);

    }

    @Override
    public int getItemCount() {
        return this.ProductData.size();
    }


    public void setOnItemClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public class DramaItemViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout parent;
        private ImageView itemImg;
        private TextView itemPrice;
        private TextView itemName;
        private ImageView itemHeart;

        public DramaItemViewHolder(View itemView) {
            super(itemView);

            parent = (LinearLayout) itemView.findViewById(R.id.view_parent);
            itemImg = (ImageView) itemView.findViewById(R.id.item_image);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemHeart = (ImageView) itemView.findViewById(R.id.heart_btn);

        }

        public void setOnItemClick(View.OnClickListener listener){
            parent.setOnClickListener(listener);
        }
    }

}
