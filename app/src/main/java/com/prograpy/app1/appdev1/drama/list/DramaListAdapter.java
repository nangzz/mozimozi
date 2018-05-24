package com.prograpy.app1.appdev1.drama.list;

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

import java.util.ArrayList;
import java.util.List;

public class DramaListAdapter extends RecyclerView.Adapter<DramaListAdapter.DramaListViewHolder> {

    private Context context;
    private List<DramaVO> dramaItemData = new ArrayList<DramaVO>();
    private int item_layout = 0;

    private View.OnClickListener listener;

    public DramaListAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setDramaItemData(List<DramaVO> dramaItemData){
        this.dramaItemData = dramaItemData;
    }

    @Override
    public DramaListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_drama_layout, parent, false);
        return new DramaListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DramaListViewHolder holder, int position) {
        DramaVO item = dramaItemData.get(position);

        Glide.with(context).load(item.d_img).into(holder.listImage);
        holder.listTitle.setText(item.getD_name());
        holder.listActor.setText(item.getD_act());
//        holder.listTag.setText(item.getTag());

        holder.cardView.setOnClickListener(listener);
        holder.cardView.setTag(item.getD_name());
    }

    @Override
    public int getItemCount() {
        return this.dramaItemData.size();
    }


    public class DramaListViewHolder extends RecyclerView.ViewHolder {

        TextView listTitle;
        TextView listActor;
        TextView listTag;
        ImageView listImage;
        CardView cardView;

        public DramaListViewHolder(View itemView) {
            super(itemView);

            listTitle = (TextView) itemView.findViewById(R.id.list_title);
            listActor = (TextView) itemView.findViewById(R.id.list_actor);
            listTag =  (TextView) itemView.findViewById(R.id.list_tag);
            listImage = (ImageView) itemView.findViewById(R.id.list_image);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }

        public void setText(String text) {
            listTitle.setText(text);
            listActor.setText(text);
            listTag.setText(text);
        }
    }


}
