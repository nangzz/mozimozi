package com.prograpy.app1.appdev1.mypage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageButton;

import com.prograpy.app1.appdev1.R;

/**
 * Created by Note on 2018-07-21.
 */

public class CustomPopUp extends Dialog{
    private static final int LAYOUT = R.layout.custom_popup;

    private Context context;

    private ImageButton cancelTv;

    public CustomPopUp(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CustomPopUp(Context context,String name){
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);





    }


}

