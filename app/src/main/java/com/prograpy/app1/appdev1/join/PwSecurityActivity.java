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
import com.prograpy.app1.appdev1.network.response.PwSearchResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.IdPwSearchAsyncTask;
import com.prograpy.app1.appdev1.utils.Utils;
import com.prograpy.app1.appdev1.view.TopbarView;

public class PwSecurityActivity extends AppCompatActivity {

    private NetworkProgressDialog networkProgressDialog;
    EditText name,email,id;
    Button pwSearch, goLogin;
    TextView resultText, loginText;

    private LinearLayout resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_security);

        networkProgressDialog = new NetworkProgressDialog(this);

        name = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.usermail);
        id = (EditText)findViewById(R.id.userid);

        pwSearch  = (Button)findViewById(R.id.pw_btn);

        loginText = (TextView) findViewById(R.id.login_text);
        resultText = (TextView)findViewById(R.id.id);

        resultView = (LinearLayout) findViewById(R.id.find_pw_result);


        goLogin = (Button) findViewById(R.id.go_login);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwSecurityActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PwSecurityActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

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

                if(!Utils.isValidId(id.getText().toString())){
                    Toast.makeText(PwSecurityActivity.this, "아이디는 영소문자+숫자 조합으로 4~16자 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(!Utils.isValidName(name.getText().toString())){
                    Toast.makeText(PwSecurityActivity.this, "이름은 한글로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Utils.isValidEmailAddress(email.getText().toString())){
                    Toast.makeText(PwSecurityActivity.this, "이메일 형식을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
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

                resultView.setVisibility(View.VISIBLE);


                if (result.success && result.PwInfoDataVo != null && result.PwInfoDataVo.size() > 0) {
                    networkProgressDialog.dismiss();
                    resultText.setText(id.getText().toString() + "님의 비밀번호는\n" + result.PwInfoDataVo.get(0).user_pw + " 입니다.");

                } else {
                    networkProgressDialog.dismiss();
                    resultText.setText("입력하신 정보의 사용자가 없습니다.");

                }

            }

            @Override
            public void onFailAppAsysncask() {
                resultView.setVisibility(View.VISIBLE);
                networkProgressDialog.dismiss();

                resultText.setText("입력하신 정보의 사용자가 없습니다.");
                Toast.makeText(PwSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelAppAsyncTask() {
                resultView.setVisibility(View.VISIBLE);
                networkProgressDialog.dismiss();
                resultText.setText("입력하신 정보의 사용자가 없습니다.");
                Toast.makeText(PwSecurityActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });

        networkProgressDialog.show();
        idPwSearchAsyncTask.execute(ApiValue.API_PwSearch, name.getText().toString(), email.getText().toString(),id.getText().toString());
    }
}

