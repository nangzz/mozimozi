package com.prograpy.app1.appdev1.category.item;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.popup.info.CustomPopup;
import com.prograpy.app1.appdev1.view.TopbarView;

/**
 * 새로 만든 Intent 테스트용 Activity
 * Activity를 추가하면 반드시 꼭 왼쪽 프로젝트 창에서
 * 'manifests' -> 'AndroidManifeat.xml'
 * 파일에 들어가서 Activity가 추가되었다는 걸
 * App에 알려줘야한다.
 *
 *
 * Created by SeungJun on 2018-03-26.
 */

public class NewIntentCategorySample extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TopbarView topbarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.category_sample);

        View view = findViewById(R.id.root_layout);
        imageView = (ImageView)findViewById(R.id.item_sample);
        imageView.setOnClickListener(this);
        view.setOnClickListener(this);

        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle(getIntent().getStringExtra("title")); //전달받은 데이터 받는 것
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_sample :

                final CustomPopup popup = new CustomPopup(NewIntentCategorySample.this);
                popup.show();
                break;

            case R.id.root_layout :

               setFinishOnTouchOutside(true);
               break;
        }


    }


}
