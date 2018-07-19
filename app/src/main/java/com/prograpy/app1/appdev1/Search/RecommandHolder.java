package com.prograpy.app1.appdev1.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;

/**
 * Created by Note on 2018-07-19.
 */

public class RecommandHolder extends RecyclerView.ViewHolder {

    TextView textView1;

    public RecommandHolder(View itemView) {

        super(itemView);
        textView1 = (TextView)itemView.findViewById(R.id.recommand_search);

    }


    public void setData(String str){
        textView1.setText(str);
    }


}


