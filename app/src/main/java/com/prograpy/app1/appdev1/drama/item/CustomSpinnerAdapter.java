package com.prograpy.app1.appdev1.drama.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter {

    private String[] spData;
    private Context context;

    public CustomSpinnerAdapter(Context context, String[] spData){
        this.context = context;
        this.spData = spData;
    }

    public void setSpData(String[] spData) {
        this.spData = spData;
    }

    @Override
    public int getCount() {
        if(spData == null || spData.length <= 0)
            return 0;

        return spData.length;
    }

    @Override
    public Object getItem(int position) {
        return spData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_custom_spinner_main, parent, false);
        }

        ((TextView)convertView.findViewById(R.id.spinner_title)).setText(spData[position]);


        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_custom_spinner_drop, parent, false);
        }

        ((TextView)convertView.findViewById(R.id.spinner_drop_title)).setText(spData[position]);

        return convertView;

    }
}
