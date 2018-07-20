package com.prograpy.app1.appdev1.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prograpy.app1.appdev1.R;

/**
 * Created by Note on 2018-07-19.
 */

public class RecommandAdapter extends RecyclerView.Adapter<RecommandHolder> {

    Context context;


    private View.OnClickListener onClickListener;

    public RecommandAdapter(Context context){
        context = this.context;
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecommandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommand_search, parent, false);
        return new RecommandHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommandHolder holder, int position) {

        holder.itemView.setOnClickListener(onClickListener);
        String data ="";

        if(position ==0){
            data = "김현주 코드";
        }

        else if(position ==1){
            data = "라미란 구두";
        }
        else if(position ==2){
            data = "정려원 가방";
        }

        else if(position ==3){
            data = "표예진 가방 ";
        }

        else if(position ==4){
            data = "장혁";
        }

        holder.setData(data);
        holder.itemView.setTag(data);
    }


    @Override
    public int getItemCount() {
        return 5;
    }
}
