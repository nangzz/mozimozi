package com.prograpy.app1.appdev1.join;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.CheckIdAsyncTask;
import com.prograpy.app1.appdev1.task.JoinAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;

/**
 * Created by SeungJun on 2018-05-04.
 */

public class JoinUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TopbarView topbarView;

    private NetworkProgressDialog networkProgressDialog;

    private Button btnNext;
    private Button btnIdCheck;

    private EditText editId;
    private EditText editPw;
    private EditText editPw2;
    private EditText editName;
    private EditText editEmail;

    private String userId = "";
    private String userPw = "";
    private String userPw2 = "";
    private String userName = "";
    private String userEmail = "";


    private boolean isCheckId = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        setContentView(R.layout.activity_join_userinfo);

        networkProgressDialog = new NetworkProgressDialog(this);

        btnNext = (Button) findViewById(R.id.btn_next);
        btnIdCheck = (Button) findViewById(R.id.btn_check_id);

        editId = (EditText) findViewById(R.id.user_id);
        editPw = (EditText) findViewById(R.id.user_pw);
        editPw2 = (EditText) findViewById(R.id.user_pw2);
        editName = (EditText) findViewById(R.id.user_name);
        editEmail = (EditText) findViewById(R.id.user_email);

        btnNext.setOnClickListener(this);
        btnIdCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        userId = editId.getText().toString();
        userPw = editPw.getText().toString();
        userPw2 = editPw2.getText().toString();
        userName = editName.getText().toString();
        userEmail = editEmail.getText().toString();

        switch (v.getId()){

            case R.id.btn_check_id:

                CheckIdAsyncTask checkIdAsyncTask = new CheckIdAsyncTask(new CheckIdAsyncTask.CheckIdResultHandler() {
                    @Override
                    public void onSuccessAppAsyncTask(ServerSuccessCheckResult result) {

                        networkProgressDialog.dismiss();

                        if(result != null){
                            if(result.success){
                                isCheckId = true;

                                Toast.makeText(JoinUserInfoActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                            }else{

                                isCheckId = false;
                                Toast.makeText(JoinUserInfoActivity.this, result.message, Toast.LENGTH_SHORT).show();
                            }

                        }else{

                            Toast.makeText(JoinUserInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailAppAsysncask() {

                        networkProgressDialog.dismiss();

                        isCheckId = false;

                        Toast.makeText(JoinUserInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelAppAsyncTask() {

                        networkProgressDialog.dismiss();

                        isCheckId = false;
                    }
                });

                networkProgressDialog.show();

                checkIdAsyncTask.execute(ApiValue.API_ID_CHECK, userId);


                break;



            case R.id.btn_next:

                if(TextUtils.isEmpty(userId) || TextUtils.isEmpty(userPw) || TextUtils.isEmpty(userPw2)
                        || TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail)){
                    Toast.makeText(this, "회원정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidId(userId)){
                    Toast.makeText(this, "아이디는 영소문자+숫자 조합으로 4~16자 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidPw(userPw) && !isValidPw(userPw2)){
                    Toast.makeText(this, "비밀번호는 영소문자+숫자 조합으로 8~16자 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!userPw.equals(userPw2)){
                    Toast.makeText(this, "입력하신 비밀번호가 다릅니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidName(userName)){
                    Toast.makeText(this, "이름은 한글로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidEmailAddress(userEmail)){
                    Toast.makeText(this, "이메일 형식을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isCheckId){
                    Toast.makeText(this, "아이디 중복체크를 진행 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                JoinAsyncTask joinAsyncTask = new JoinAsyncTask(new JoinAsyncTask.JoinResultHandler() {
                    @Override
                    public void onSuccessAppAsyncTask(ServerSuccessCheckResult result) {

                        networkProgressDialog.dismiss();

                        if(result != null){
                            if(result.success){

                                Intent intent = new Intent(JoinUserInfoActivity.this, JoinCompleteActivity.class);
                                startActivity(intent);
                                finish();
                            }else{

                                Toast.makeText(JoinUserInfoActivity.this, result.message, Toast.LENGTH_SHORT).show();

                            }

                        }else{

                            Toast.makeText(JoinUserInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailAppAsysncask() {
                        networkProgressDialog.dismiss();

                        Toast.makeText(JoinUserInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelAppAsyncTask() {
                        networkProgressDialog.dismiss();

                        Toast.makeText(JoinUserInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }
                });

                networkProgressDialog.show();

                joinAsyncTask.execute(ApiValue.API_JOIN, userId, userPw, userName, userEmail);


                break;

        }
    }



    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }


    /**
     * 이메일 유효성 체크
     *
     * @param email
     *         체크할 이메일
     * @return 유효성 여부
     */
    private boolean isValidEmailAddress(String email) {
        boolean stricterFilter = true;
        String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
        String emailRegex = stricterFilter ? stricterFilterString : laxString;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailRegex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
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


    /**
     * 이름 유효성 체크
     * @param nickname 체크할 이름
     * @return 유효성 여부
     */
    private boolean isValidName(String nickname) {
        String stricterFilterString = "^[ㄱ-힣\\s]*$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(stricterFilterString);
        java.util.regex.Matcher m = p.matcher(nickname);
        return m.matches();
    }
}
