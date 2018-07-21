package com.prograpy.app1.appdev1.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.vo.DramaVO;

import java.util.ArrayList;

public class HomeDramaAdapter extends RecyclerView.Adapter<HomeDramaAdapter.HomeDramaPosterViewHolder> {

    private Context context;

    private View.OnClickListener onClickListener;
    private ArrayList<DramaVO> dramaData = new ArrayList<DramaVO>();


    public HomeDramaAdapter(Context context, View.OnClickListener onClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public void setDramaData(ArrayList<DramaVO> dramaData){
        this.dramaData = dramaData;
    }

    @Override
    public HomeDramaPosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_main_drama_poster, parent,false);

        return new HomeDramaPosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeDramaPosterViewHolder holder, int position) {

        DramaVO item = dramaData.get(position);

        Glide.with(context).load(item.getD_img()).into( ((HomeDramaPosterViewHolder)holder).itemDramaImg);
        ((HomeDramaPosterViewHolder)holder).itemDramaName.setText(item.getD_name());

        ((HomeDramaPosterViewHolder)holder).itemView.setTag(item);
        ((HomeDramaPosterViewHolder)holder).itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        if(dramaData.size() == 0){
            return 0;
        }
        else{return dramaData.size();}
    }

    public class HomeDramaPosterViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemDramaImg;
        private TextView itemDramaName;

        public HomeDramaPosterViewHolder(View itemView) {
            super(itemView);

            itemDramaImg = (ImageView) itemView.findViewById(R.id.image);
            itemDramaName = (TextView)itemView.findViewById(R.id.image_name);
        }
    }

}