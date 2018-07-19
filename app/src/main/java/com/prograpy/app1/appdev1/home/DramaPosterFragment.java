package com.prograpy.app1.appdev1.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.drama.item.DramaItemListActivity;
import com.prograpy.app1.appdev1.vo.DramaVO;

public class DramaPosterFragment extends Fragment {

    private ImageView imageView;
    private TextView imageName;

    private static Context mContext;

    public static DramaPosterFragment newInstance(Context context, DramaVO mainDramaData, int position) {

        DramaPosterFragment fragment = new DramaPosterFragment();

        Bundle b = new Bundle();
        b.putInt("position", position);

        if(mainDramaData != null){
            b.putString("image", mainDramaData.getD_img());
            b.putString("title", mainDramaData.getD_name());
            b.putInt("dramaId", mainDramaData.getD_id());
            b.putString("actors", mainDramaData.getD_act());
            b.putString("description", mainDramaData.getD_name());
        }

        fragment.setArguments(b);

        mContext = context;

        return fragment;

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        final String title = getArguments().getString("title");
        final int dramaId = getArguments().getInt("dramaId");
        final String actors = "전체," + getArguments().getString("actors");

        View view = (View) inflater.inflate(R.layout.view_main_drama_poster, container, false);

        imageView = (ImageView) view.findViewById(R.id.image);

        Glide.with(mContext).load(getArguments().getString("image")).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DramaItemListActivity.class);
                i.putExtra("title", title);
                i.putExtra("dramaId", dramaId);
                i.putExtra("actors", actors);
                startActivity(i);

            }
        });

        imageName = (TextView) view.findViewById(R.id.image_name);
        imageName.setText(getArguments().getString("description"));

        return view;
    }
}