package com.prograpy.app1.appdev1.Search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prograpy.app1.appdev1.R;

import java.util.Random;

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

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(RecommandHolder holder, int position) {

        holder.itemView.setOnClickListener(onClickListener);
        String data ="";
        String recommand [] = new String[20];

        recommand = new String[]{"김선아 원피스", "top","outer","서강준 니트","공승연 신발","장혁 코트","김현주 코트","정려원 가방","표예진 가방","조보아 블라우스","채시라 가방"
        ,"이준영 티셔츠","박민영 블라우스","박서준 티셔츠","박민영 귀걸이","skirts","onepiece","accessary","bags","shirts"};

        Random random = new Random();

        if(position ==0){
            data = recommand[random.nextInt(20)+1];
        }

        else if(position ==1){
            data = recommand[random.nextInt(20)+1];
        }
        else if(position ==2){
            data = recommand[random.nextInt(20)+1];
        }

        else if(position ==3){
            data = recommand[random.nextInt(20)+1];
        }

        else if(position ==4){
            data = recommand[random.nextInt(20)+1];
        }

        holder.setData(data);
        holder.itemView.setTag(data);
    }


    @Override
    public int getItemCount() {
        return 5;
    }
}
