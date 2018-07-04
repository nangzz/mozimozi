package com.prograpy.app1.appdev1.mypage;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;
import java.util.List;

public class MyPageListAdapter extends RecyclerView.Adapter<MyPageListAdapter.MyPageListViewHolder> {

    private Context context;
    private List<ProductVO> myPageItemData = new ArrayList<ProductVO>();
    private int item_layout = 0;
    private View.OnClickListener listener;

/* 리스트 형식일 때
    public MyPageListAdapter(Context context, List<MyPageItemData> myPageItemData, int item_layout) {
        this.context = context;
        this.myPageItemData = myPageItemData;
        this.item_layout = item_layout;
    }
*/

    public MyPageListAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setMyPageItemData(List<ProductVO> myPageItemData){
        this.myPageItemData = myPageItemData;
    }


    @Override
    public MyPageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mypage_item_layout, parent, false);
        return new MyPageListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyPageListViewHolder holder, int position) {

        ProductVO item = myPageItemData.get(position);

        Glide.with(context).load(item.p_img).into(holder.listImageInfo);
        holder.listTitleInfo.setText(item.getP_name());
        holder.listBrand.setText(item.getP_brand());
        holder.listPrice.setText(item.getP_price());

        holder.cardView.setOnClickListener(listener);
        holder.cardView.setTag(item.getP_name());

    }

    @Override
    public int getItemCount() {
        return this.myPageItemData.size();
    }

    public class MyPageListViewHolder extends RecyclerView.ViewHolder {

        TextView listTitleInfo;
        TextView listBrand;
        TextView listPrice;
        ImageView listImageInfo;
        CardView cardView;

        public MyPageListViewHolder(View itemView) {
            super(itemView);

            listTitleInfo = (TextView) itemView.findViewById(R.id.list_title_info);
            listBrand = (TextView) itemView.findViewById(R.id.list_brand_info);
            listPrice =  (TextView) itemView.findViewById(R.id.list_price_info);
            listImageInfo = (ImageView) itemView.findViewById(R.id.list_image_info);
            cardView = (CardView) itemView.findViewById(R.id.cardview_info);
        }

        public void setText(String text) {
            listTitleInfo.setText(text);
            listBrand.setText(text);
            listPrice.setText(text);
        }
    }


}
