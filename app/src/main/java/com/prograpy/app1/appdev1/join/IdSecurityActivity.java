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
import com.prograpy.app1.appdev1.network.response.IdSearchResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.IdSearchAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;

public class IdSecurityActivity extends AppCompatActivity {
    private TopbarView topbarView;
    EditText name,email;
    Button idSearch;

    TextView id1,id2;
    private NetworkProgressDialog networkProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_security);

        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle("아이디 찾기");
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });
        id1 = (TextView)findViewById(R.id.id1);
        id2 = (TextView)findViewById(R.id.id2);

        networkProgressDialog = new NetworkProgressDialog(this);

        name = (EditText)findViewById(R.id.login_id);
        email = (EditText)findViewById(R.id.login_mail);

        idSearch = (Button)findViewById(R.id.id_btn);


        idSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().isEmpty()){
                    Toast.makeText(IdSecurityActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(email.getText().toString().isEmpty()){
                    Toast.makeText(IdSecurityActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                IdSearch();


            }
        });

    }

    void IdSearch(){
        IdSearchAsyncTask idSearchAsyncTask = new IdSearchAsyncTask(new IdSearchAsyncTask.IdSearchHandler() {


            @Override
            public void onSuccessAppAsyncTask(IdSearchResult result) {

                if(result != null){

                    if(result.success && result.IdInfoVOArrayList != null && result.IdInfoVOArrayList.size() > 0){
                        networkProgressDialog.dismiss();
                        id1.setText(name.getText().toString()+"님의 아이디는 ");
                        id2.setText(result.IdInfoVOArrayList.get(0).user_id+" 입니다.");
                    }else{
                        networkProgressDialog.dismiss();
                        id1.setText("입력하신 정보의 사용자가 없습니다.");

                    }

                }else{
                    id1.setText("입력하신 정보의 사용자가 없습니다.");
                    Toast.makeText(IdSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailAppAsysncask() {
                networkProgressDialog.dismiss();

                id1.setText("입력하신 정보의 사용자가 없습니다.");
                Toast.makeText(IdSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelAppAsyncTask() {
                networkProgressDialog.dismiss();
                id1.setText("입력하신 정보의 사용자가 없습니다.");
                Toast.makeText(IdSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });

        idSearchAsyncTask.execute(ApiValue.API_IdSearch, name.getText().toString(), email.getText().toString());
    }
}

