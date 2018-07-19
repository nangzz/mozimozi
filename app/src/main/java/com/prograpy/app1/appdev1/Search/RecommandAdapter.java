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

    public RecommandAdapter(Context context){
        context = this.context;
    }

    @Override
    public RecommandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommand_search, parent, false);
        return new RecommandHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommandHolder holder, int position) {

        if(position ==0){
            holder.setData("  9회 김현주 코트");
        }

        else if(position ==1){

            holder.setData("  6회 라미란 구두");
        }
        else if(position ==2){

            holder.setData("  2회 정려원 가방 ");
        }

        else if(position ==3){

            holder.setData("  표예진 가방 ");
        }

        else if(position ==4){

            holder.setData("  장혁 ");
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
