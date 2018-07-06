package com.prograpy.app1.appdev1.join;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.MainActivity;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.view.TopbarView;

/**
 * Created by SeungJun on 2018-05-04.
 */

public class ProvisionActivity extends AppCompatActivity implements View.OnClickListener {

    private TopbarView topbarView;

    private CheckBox agree1;
    private CheckBox agree2;
    private CheckBox agree3;

    private Button btnCancel;
    private Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        setContentView(R.layout.activity_join_provision);

        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle("약관 동의");
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });


        agree1 = (CheckBox) findViewById(R.id.agree1);
        agree2 = (CheckBox) findViewById(R.id.agree2);
        agree3 = (CheckBox) findViewById(R.id.agree3);

        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnNext = (Button) findViewById(R.id.btn_next);

        btnCancel.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_cancel:
                finish();
                break;


            case R.id.btn_next:

                if(agree1.isChecked() && agree2.isChecked() && agree3.isChecked()){

                    Intent intent = new Intent(this, JoinUserInfoActivity.class);
                    intent.putExtra("type", ((TextView)v).getText().toString());
                    startActivity(intent);

                }
                else{
                    Toast.makeText(this, "약관에 모두 동의 해주셔야 합니다.", Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }


    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }


}
