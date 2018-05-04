package com.prograpy.app1.appdev1.join;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prograpy.app1.appdev1.MainActivity;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.view.TopbarView;

/**
 * Created by SeungJun on 2018-05-04.
 */

public class JoinCompleteActivity extends AppCompatActivity implements View.OnClickListener {

    private TopbarView topbarView;
    private Button btnMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join_complete);

        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.ONLY_TITLE);
        topbarView.setTopBarTitle("회원 가입 완료");

        btnMain = (Button) findViewById(R.id.btn_main);
        btnMain.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_main:

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                break;
        }
    }
}
