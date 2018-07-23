package com.prograpy.app1.appdev1.join;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.IdSearchResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.IdSearchAsyncTask;
import com.prograpy.app1.appdev1.utils.Utils;

public class IdSecurityActivity extends AppCompatActivity {
    EditText name, email;
    Button idSearch, pwSearch;

    private TextView pwText, goLogin;

    private LinearLayout resultView;

    TextView id1;
    private NetworkProgressDialog networkProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_security);

        id1 = (TextView) findViewById(R.id.id1);

        networkProgressDialog = new NetworkProgressDialog(this);

        name = (EditText) findViewById(R.id.login_name);
        email = (EditText) findViewById(R.id.login_mail);

        idSearch = (Button) findViewById(R.id.id_btn);
        pwText = (TextView) findViewById(R.id.find_pw_text);

        pwText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IdSecurityActivity.this, PwSecurityActivity.class);
                startActivity(intent);
                finish();
            }
        });

        goLogin = (TextView) findViewById(R.id.go_login);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IdSecurityActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        resultView = (LinearLayout) findViewById(R.id.find_id_result);

        idSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(IdSecurityActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(IdSecurityActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(!Utils.isValidName(name.getText().toString())){
                    Toast.makeText(IdSecurityActivity.this, "이름은 한글로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Utils.isValidEmailAddress(email.getText().toString())){
                    Toast.makeText(IdSecurityActivity.this, "이메일 형식을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                IdSearch();


            }
        });

    }

    void IdSearch() {
        IdSearchAsyncTask idSearchAsyncTask = new IdSearchAsyncTask(new IdSearchAsyncTask.IdSearchHandler() {


            @Override
            public void onSuccessAppAsyncTask(IdSearchResult result) {

                resultView.setVisibility(View.VISIBLE);

                if (result.success && result.IdInfoVOArrayList != null && result.IdInfoVOArrayList.size() > 0) {
                    networkProgressDialog.dismiss();
                    id1.setText(name.getText().toString() + "님의 아이디는\n" + result.IdInfoVOArrayList.get(0).user_id + " 입니다.");
                } else {
                    networkProgressDialog.dismiss();
                    id1.setText("입력하신 정보의 사용자가 없습니다.");

                }
            }

            @Override
            public void onFailAppAsysncask() {
                resultView.setVisibility(View.VISIBLE);
                networkProgressDialog.dismiss();

                id1.setText("입력하신 정보의 사용자가 없습니다.");

                Toast.makeText(IdSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelAppAsyncTask() {
                resultView.setVisibility(View.VISIBLE);
                networkProgressDialog.dismiss();
                id1.setText("입력하신 정보의 사용자가 없습니다.");

                Toast.makeText(IdSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });

        networkProgressDialog.show();
        idSearchAsyncTask.execute(ApiValue.API_IdSearch, name.getText().toString(), email.getText().toString());
    }
}

