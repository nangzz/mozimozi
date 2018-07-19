package com.prograpy.app1.appdev1.join;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.PwSearchResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.IdPwSearchAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;

public class PwSecurityActivity extends AppCompatActivity {
    private TopbarView topbarView;
    private NetworkProgressDialog networkProgressDialog;
    EditText name,email,id;
    Button pwSearch;
    TextView userid,userpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_security);

        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle("비밀번호 찾기");
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });
        networkProgressDialog = new NetworkProgressDialog(this);

        name = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.usermail);
        id = (EditText)findViewById(R.id.userid);
        pwSearch  = (Button)findViewById(R.id.pw_btn);
        userid = (TextView)findViewById(R.id.id);
        userpw = (TextView)findViewById(R.id.pw);


        pwSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    Toast.makeText(PwSecurityActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(email.getText().toString().isEmpty()){
                    Toast.makeText(PwSecurityActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(id.getText().toString().isEmpty()){
                    Toast.makeText(PwSecurityActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                PwSearch();
            }
        });

    }


    public void PwSearch(){
        IdPwSearchAsyncTask idPwSearchAsyncTask = new IdPwSearchAsyncTask(new IdPwSearchAsyncTask.IdPwSearchHandler() {


            @Override
            public void onSuccessAppAsyncTask(PwSearchResult result) {

                if(result != null){

                    if(result.success && result.PwInfoDataVo != null && result.PwInfoDataVo.size() > 0){
                        networkProgressDialog.dismiss();
                        userid.setText(id.getText().toString()+"님의 비밀번호는 ");
                        userpw.setText(result.PwInfoDataVo.get(0).user_pw+" 입니다.");
                    }else{
                        networkProgressDialog.dismiss();
                        userid.setText("입력하신 정보의 사용자가 없습니다.");

                    }

                }else{
                    userid.setText("입력하신 정보의 사용자가 없습니다.");
                    Toast.makeText(PwSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailAppAsysncask() {
                networkProgressDialog.dismiss();

                userid.setText("입력하신 정보의 사용자가 없습니다.");
                Toast.makeText(PwSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelAppAsyncTask() {
                networkProgressDialog.dismiss();
                userid.setText("입력하신 정보의 사용자가 없습니다.");
                Toast.makeText(PwSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });

        idPwSearchAsyncTask.execute(ApiValue.API_PwSearch, name.getText().toString(), email.getText().toString(),id.getText().toString());
    }
}

