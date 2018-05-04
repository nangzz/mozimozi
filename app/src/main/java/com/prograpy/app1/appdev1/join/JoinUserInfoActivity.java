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
import com.prograpy.app1.appdev1.view.TopbarView;

/**
 * Created by SeungJun on 2018-05-04.
 */

public class JoinUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TopbarView topbarView;

    private Button btnCancel;
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

        setContentView(R.layout.activity_join_userinfo);

        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle("회원 정보 입력");
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });

        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnIdCheck = (Button) findViewById(R.id.btn_check_id);

        editId = (EditText) findViewById(R.id.user_id);
        editPw = (EditText) findViewById(R.id.user_pw);
        editPw2 = (EditText) findViewById(R.id.user_pw2);
        editName = (EditText) findViewById(R.id.user_name);
        editEmail = (EditText) findViewById(R.id.user_email);

        btnCancel.setOnClickListener(this);
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

                isCheckId = true;

                break;


            case R.id.btn_cancel:
                finish();
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

                if(!isValidPw(userPw) && !isValidId(userPw2)){
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

                Intent intent = new Intent(this, JoinCompleteActivity.class);
                intent.putExtra("type", ((TextView) v).getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;

        }
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

        String stricterFilterString = "^((?=.*[0-9])(?=.*[a-z])).{4,16}$";
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
