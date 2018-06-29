package com.prograpy.app1.appdev1.join;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.MainActivity;
import com.prograpy.app1.appdev1.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity  {

    Button loginBtn;
    ImageView joinBtn, joinSearchBtn;
    EditText loginId, loginPw;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        loginBtn = (Button)findViewById(R.id.login_btn);
        joinBtn = (ImageView) findViewById(R.id.login_join);
        joinSearchBtn = (ImageView) findViewById(R.id.login_search);
        loginId = (EditText)findViewById(R.id.login_id);
        loginPw = (EditText)findViewById(R.id.login_pw);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginActivity.this, "",
                        "Validating user...", true);
                new Thread(new Runnable() {
                    public void run() {
                        login();
                    }
                }).start();
            }
        });
    }


    void login() {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://52.78.118.92:8080/appDev1/user");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", loginId.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pw", loginPw.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    dialog.dismiss();
                }
            });


            if (response.equalsIgnoreCase("success") ) { //php 에서 보내는 success라는 로그인 성공 정보
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity((new Intent(LoginActivity.this, MainActivity.class)));
                finish();

            } else if (response.equalsIgnoreCase("fail")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "로그인 실패! 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                    }
                });
            } else if (response.equalsIgnoreCase("nouser")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "로그인 실패! 회원가입을 해주세요.", Toast.LENGTH_LONG).show();
                    }
                });
            } else if (response.equalsIgnoreCase("noinput")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "로그인 실패! 입력을 완료해주세요.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(Exception e)
        {
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }


    public void onClick(View v)
    {
        Intent intent = new Intent(this, ProvisionActivity.class);
        startActivity(intent);
        finish();
    }


}

