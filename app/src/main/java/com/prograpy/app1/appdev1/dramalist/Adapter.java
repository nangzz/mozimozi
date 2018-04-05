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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<ItemData> itemData;
    int item_layout;

    private View.OnClickListener listener;

    public Adapter(Context context, List<ItemData> itemData, int item_layout, View.OnClickListener listener) {
        this.context = context;
        this.itemData = itemData;
        this.item_layout = item_layout;
        this.listener = listener;
    }

    Adapter(List data) {
        if (data == null) {
            throw new IllegalArgumentException (
                    "data must not be null");
        }
        this.itemData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ItemData item = itemData.get(position);
       // Drawable drawable = context.getResources().getDrawable(item.getImage());
        holder.listImage.setImageResource(itemData.get(position).image);
        holder.listTitle.setText(item.getTitle());
        holder.listActor.setText(item.getActor());
        holder.listTag.setText(item.getTag());

        holder.cardView.setOnClickListener(listener);
        holder.cardView.setTag(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return this.itemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView listTitle;
        TextView listActor;
        TextView listTag;
        ImageView listImage;
        CardView cardView;

        public ViewHolder(View itemView) {
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
