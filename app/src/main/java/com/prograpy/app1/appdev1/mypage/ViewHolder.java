package com.prograpy.app1.appdev1.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView listTitleInfo;
    TextView listBrand;
    TextView listPrice;
    ImageView listImageInfo;

    public ViewHolder(View itemView) {
        super(itemView);
        listTitleInfo = (TextView) itemView.findViewById(R.id.list_title_info);
        listBrand = (TextView) itemView.findViewById(R.id.list_brand_info);
        listPrice =  (TextView) itemView.findViewById(R.id.list_price_info);
        listImageInfo = (ImageView) itemView.findViewById(R.id.list_image_info);
    }
}
