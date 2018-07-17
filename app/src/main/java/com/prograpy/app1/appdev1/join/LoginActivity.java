package com.prograpy.app1.appdev1.join;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.prograpy.app1.appdev1.MainActivity;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.MyPageProductResult;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.MypageProductAsyncTask;
import com.prograpy.app1.appdev1.task.UserLoginAsyncTask;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.ProductVO;

public class LoginActivity extends Activity  {

    private Button loginBtn, joinBtn;
    private EditText loginId, loginPw;
    private CheckBox autoLogin;

    private NetworkProgressDialog networkProgressDialog;
    private TopbarView topbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle("로그인");
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });

        networkProgressDialog = new NetworkProgressDialog(this);

        loginBtn = (Button)findViewById(R.id.login_btn);
        joinBtn = (Button) findViewById(R.id.join_btn);
//        joinSearchBtn = (ImageView) findViewById(R.id.login_search);
        loginId = (EditText)findViewById(R.id.login_id);
        loginPw = (EditText)findViewById(R.id.login_pw);

        autoLogin = (CheckBox) findViewById(R.id.login_auto);
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceData.setKeyAutoLogin(isChecked);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(loginId.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(loginPw.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidId(loginId.getText().toString())){
                    Toast.makeText(LoginActivity.this, "아이디는 영소문자+숫자 조합으로 4~16자 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidPw(loginPw.getText().toString())){
                    Toast.makeText(LoginActivity.this, "비밀번호는 영소문자+숫자 조합으로 8~16자 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }


                networkProgressDialog.show();

                login();
            }
        });
    }


    private void login() {

        UserLoginAsyncTask task = new UserLoginAsyncTask(new UserLoginAsyncTask.UserLoginResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(ServerSuccessCheckResult result) {

                if (result.isSuccess()) {

                    PreferenceData.setKeyLoginSuccess(true);

                    PreferenceData.setKeyUserId(loginId.getText().toString());

                    DbController.deleteAll(LoginActivity.this);

                    // 자동 로그인 체크시 패스워드 저장
                    if (PreferenceData.getKeyAutoLogin()) {
                        PreferenceData.setKeyUserPw(loginPw.getText().toString());

                        callMyProduct();
                    }else{

                        networkProgressDialog.dismiss();

                        moveMain();
                    }


                } else {
                    networkProgressDialog.dismiss();

                    PreferenceData.setKeyLoginSuccess(false);
                    Toast.makeText(LoginActivity.this, result.message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailAppAsysncask() {
                networkProgressDialog.dismiss();

                PreferenceData.setKeyLoginSuccess(false);
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {
                networkProgressDialog.dismiss();

                PreferenceData.setKeyLoginSuccess(false);
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }
        });


        task.execute(ApiValue.API_USER_LOGIN, loginId.getText().toString(), loginPw.getText().toString());
    }


    public void onClick(View v)
    {
        Intent intent = new Intent(this, JoinUserInfoActivity.class);
        startActivity(intent);
    }

    private void moveMain(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    /**
     * 자동 로그인하고 내 찜목록 전부 받아오는 task
     */
    private void callMyProduct(){

        MypageProductAsyncTask mypageProductAsyncTask = new MypageProductAsyncTask(new MypageProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(MyPageProductResult result) {

                networkProgressDialog.dismiss();

                if (result.isSuccess()) {

                    if (result.getMypageProductList() != null && result.getMypageProductList().size() > 0) {

                        for(ProductVO item : result.getMypageProductList()){
                            DbController.addProductId(LoginActivity.this, item.getP_id());
                        }

                    }
                }

                moveMain();
            }

            @Override
            public void onFailAppAsysncask() {

                networkProgressDialog.dismiss();

                moveMain();
            }

            @Override
            public void onCancelAppAsyncTask() {

                networkProgressDialog.dismiss();

                moveMain();
            }
        });

        mypageProductAsyncTask.execute(ApiValue.API_MYPAGE, PreferenceData.getKeyUserId());
    }



    /**
     * 비밀번호 유효성 체크
     *
     * @param pw
     *         체크할 비밀번호
     * @return 유효성 여부
     */
    private boolean isValidPw(String pw) {

        // 대문자가 안걸러져서 대문자는 별도로
        if(java.util.regex.Pattern.compile("^(.*[A-Z].*).$").matcher(pw).matches()){
            return false;
        }

        String stricterFilterString = "^((?=.*[0-9])(?=.*[a-z])).{8,16}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(stricterFilterString);
        java.util.regex.Matcher m = p.matcher(pw);
        return m.matches();
    }

    /**
     * 아이디 유효성 체크
     *
     * @param id
     *         체크할 아이디
     * @return 유효성 여부
     */
    private boolean isValidId(String id) {

        // 대문자가 안걸러져서 대문자는 별도로
        if(java.util.regex.Pattern.compile("^(.*[A-Z].*).$").matcher(id).matches()){
            return false;
        }

        String stricterFilterString = "^((?=.*[0-9])(?=.*[a-z])|(?=.*[a-z])).{4,16}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(stricterFilterString);
        java.util.regex.Matcher m = p.matcher(id);
        return m.matches();
    }


}

