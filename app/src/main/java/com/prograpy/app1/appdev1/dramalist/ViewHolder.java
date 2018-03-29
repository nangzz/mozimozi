package com.prograpy.app1.appdev1.dramalist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView listTitle;
    TextView listActor;
    TextView listTag;
    ImageView listImage;

    public ViewHolder(View itemView) {
        super(itemView);
        listTitle = (TextView) itemView.findViewById(R.id.list_title);
        listActor = (TextView) itemView.findViewById(R.id.list_actor);
        listTag =  (TextView) itemView.findViewById(R.id.list_tag);
        listImage = (ImageView) itemView.findViewById(R.id.list_image);
    }
}
