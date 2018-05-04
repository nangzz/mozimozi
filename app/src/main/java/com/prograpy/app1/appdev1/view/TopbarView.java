package com.prograpy.app1.appdev1.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;

/**
 * Created by SeungJun on 2018-03-29.
 */

public class TopbarView extends LinearLayout {

    public static enum TOPBAR_TYPE {
        MAIN,
        BACK_TITLE,
        ONLY_TITLE
    }

    public interface ItemClick{
        public void onItemClick();
    }


    private ItemClick menuClick;
    private ItemClick menuBackClick;

    public void setTopMenuClick(ItemClick menuClick){
        this.menuClick = menuClick;
    }
    public void setTopMenuBackClick(ItemClick menuBackClick){
        this.menuBackClick = menuBackClick;
    }


    private ImageView topMenu;
    private ImageView topSearch;
    private ImageView topBack;

    private TextView topBarTitle;


    public TopbarView(Context context) {
        super(context);
        setId(R.id.title);
    }

    public TopbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setId(R.id.title);
    }

    public TopbarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setId(R.id.title);
    }


    public void setType(TOPBAR_TYPE type){

        switch (type){

            case MAIN:
                setView(R.layout.topbar);
                initMainType();
                break;


            case BACK_TITLE:
                setView(R.layout.topbar_back);
                initBackType();
                break;

            case ONLY_TITLE:
                setView(R.layout.topbar_back);
                initOnlyTitleType();
                break;        }

    }


    private void initMainType(){
        topMenu = (ImageView) findViewById(R.id.topbar_menu);
        topSearch = (ImageView) findViewById(R.id.topbar_search);
        topBarTitle = (TextView) findViewById(R.id.topbar_title);

        topMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuClick != null){
                    menuClick.onItemClick();
                }
            }
        });
    }


    private void initBackType(){
        topBack = (ImageView) findViewById(R.id.topbar_back);
        topBarTitle = (TextView) findViewById(R.id.topbar_title);

        topBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuBackClick != null){
                    menuBackClick.onItemClick();
                }
            }
        });
    }

    private void initOnlyTitleType(){
        topBarTitle = (TextView) findViewById(R.id.topbar_title);
    }


    public void setTopBarTitle(String title){
        topBarTitle.setText(title);
    }

    private void setView(int view_id){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view_title_bar = inflater.inflate(view_id, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(view_title_bar, lp);
    }

}
