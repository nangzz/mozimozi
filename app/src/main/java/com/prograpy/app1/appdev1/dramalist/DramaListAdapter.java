package com.prograpy.app1.appdev1.dramalist;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;

import java.util.List;

public class DramaListAdapter extends RecyclerView.Adapter<DramaListAdapter.DramaListViewHolder> {

    private Context context;
    private List<DramaItemData> dramaItemData;
    private int item_layout = 0;

    private View.OnClickListener listener;

    public DramaListAdapter(Context context, List<DramaItemData> dramaItemData, int item_layout, View.OnClickListener listener) {
        this.context = context;
        this.dramaItemData = dramaItemData;
        this.item_layout = item_layout;
        this.listener = listener;
    }

    @Override
    public DramaListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new DramaListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DramaListViewHolder holder, int position) {
        DramaItemData item = dramaItemData.get(position);

        holder.listImage.setImageResource(dramaItemData.get(position).getImage());
        holder.listTitle.setText(item.getTitle());
        holder.listActor.setText(item.getActor());
        holder.listTag.setText(item.getTag());

        holder.cardView.setOnClickListener(listener);
        holder.cardView.setTag(item.getTitle());
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
