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
import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.utils.Utils;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;
import java.util.List;

public class MyPageListAdapter extends RecyclerView.Adapter<MyPageListAdapter.MyPageListViewHolder> {

    private Context context;
    private List<ProductVO> myPageItemData = new ArrayList<ProductVO>();
    private int item_layout = 0;
    private View.OnClickListener urlListener;
    private View.OnClickListener delListener;

/* 리스트 형식일 때
    public MyPageListAdapter(Context context, List<MyPageItemData> myPageItemData, int item_layout) {
        this.context = context;
        this.myPageItemData = myPageItemData;
        this.item_layout = item_layout;
    }
*/

    public MyPageListAdapter(Context context, View.OnClickListener urlListener, View.OnClickListener delListener) {
        this.context = context;
        this.urlListener = urlListener;
        this.delListener = delListener;
    }

    public void setMyPageItemData(List<ProductVO> myPageItemData){
        this.myPageItemData = myPageItemData;
    }


    @Override
    public MyPageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mypage_item_child, parent, false);
        return new MyPageListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyPageListViewHolder holder, int position) {

        ProductVO item = myPageItemData.get(position);

        Glide.with(context).load(item.getP_img()).into(holder.listImageInfo);
        holder.listTitleInfo.setText(item.getP_name());
        holder.listPrice.setText(Utils.moneyFormatToWon(item.getP_price())+"원");
        holder.listDrama.setText("#" + item.getD_name());
        holder.listAct.setText("#" + item.getP_act());
        holder.listCate.setText("#" + DbController.getCategoryName(context, item.getP_cat()));

        holder.item_del.setOnClickListener(delListener);
        holder.item_del.setTag(item);

        holder.item_url.setOnClickListener(urlListener);
        holder.item_url.setTag(item);

    }

    @Override
    public int getItemCount() {
        return this.myPageItemData.size();
    }

    public class MyPageListViewHolder extends RecyclerView.ViewHolder {

        TextView listTitleInfo;
        TextView listPrice;
        TextView listDrama;
        TextView listAct;
        TextView listCate;
        ImageView listImageInfo;

        ImageView item_del;
        ImageView item_url;

        public MyPageListViewHolder(View itemView) {
            super(itemView);

            listTitleInfo = (TextView) itemView.findViewById(R.id.item_name);
            listDrama = (TextView) itemView.findViewById(R.id.item_tag1);
            listAct = (TextView) itemView.findViewById(R.id.item_tag2);
            listCate = (TextView) itemView.findViewById(R.id.item_tag3);
            listPrice =  (TextView) itemView.findViewById(R.id.item_price);
            listImageInfo = (ImageView) itemView.findViewById(R.id.item_image);

            item_del = (ImageView) itemView.findViewById(R.id.item_del);
            item_url = (ImageView) itemView.findViewById(R.id.item_url);
        }
    }


}

