package com.prograpy.app1.appdev1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.join.LoginActivity;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.CategoryResult;
import com.prograpy.app1.appdev1.network.response.DramaListResult;
import com.prograpy.app1.appdev1.network.response.LoginResult;
import com.prograpy.app1.appdev1.network.response.MyPageProductResult;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.task.CategoryAsyncTask;
import com.prograpy.app1.appdev1.task.MainDListAsyncTask;
import com.prograpy.app1.appdev1.task.MypageProductAsyncTask;
import com.prograpy.app1.appdev1.task.UserLoginAsyncTask;
import com.prograpy.app1.appdev1.utils.D;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.vo.CategoryVO;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity{

    private static final int NEXT_LOGIN = 100;
    private static final int NEXT_GET_CATEGORY = 200;
    private static final int NEXT_MAIN = 300;
    private static final int NEXT_GET_MYPRODUCT = 400;

    private ArrayList<DramaVO> dramaVOS = new ArrayList<DramaVO>();

    private Handler nextStepHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {


            switch (msg.what){

                case NEXT_LOGIN:

                    // 자동 로그인 여부 판단
                    if(PreferenceData.getKeyAutoLogin()){

                        // 자동 로그인시 로그인 요청 바로 들어가게끔
                        // 성공하면 메인으로 바로 이동
                        // 실패하면 로그인 페이지 노출
                        login();
                    }else{
                        PreferenceData.setKeyAutoLogin(false);
                        PreferenceData.setKeyLoginSuccess(false);
                        PreferenceData.setKeyUserId("");
                        PreferenceData.setKeyUserPw("");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);

                                if(dramaVOS != null && dramaVOS.size() > 0)
                                    intent.putParcelableArrayListExtra("dramaList", dramaVOS);

                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }


                    break;


                case NEXT_GET_CATEGORY:

                    callCategory();

                    break;


                case NEXT_MAIN:


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(IntroActivity.this, MainActivity.class);

                            if(dramaVOS != null && dramaVOS.size() > 0)
                                intent.putParcelableArrayListExtra("dramaList", dramaVOS);

                            startActivity(intent);
                            finish();
                        }
                    }, 2000);

                    break;

                //자동 로그인 성공했을 때만
                case NEXT_GET_MYPRODUCT:
                    callMyProduct();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_intro);


        callMainDramaTask();

    }


    /**
     * 자동 로그인에 따른 로그인 task
     */
    private void login() {

        UserLoginAsyncTask task = new UserLoginAsyncTask(new UserLoginAsyncTask.UserLoginResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(LoginResult result) {

                if (result.isSuccess()) {

                    PreferenceData.setKeyLoginSuccess(true);

                    if(result.getUserInfo() != null && result.getUserInfo().size() > 0){

                        PreferenceData.setKeyUserName(result.getUserInfo().get(0).getUser_name());
                        PreferenceData.setKeyUserEmail(result.getUserInfo().get(0).getUser_email());
                    }

                    nextStepHandler.sendEmptyMessage(NEXT_GET_MYPRODUCT);

                } else {
                    PreferenceData.setKeyLoginSuccess(false);
                    PreferenceData.setKeyAutoLogin(false);
                    PreferenceData.setKeyUserId("");
                    PreferenceData.setKeyUserPw("");

                    Toast.makeText(IntroActivity.this, "로그인 실패. 아이디와 비밀번호를 확인 해주세요.", Toast.LENGTH_SHORT).show();

                    nextStepHandler.sendEmptyMessage(NEXT_LOGIN);
                }


            }

            @Override
            public void onFailAppAsysncask() {

                PreferenceData.setKeyLoginSuccess(false);
                Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

                nextStepHandler.sendEmptyMessage(NEXT_LOGIN);
            }

            @Override
            public void onCancelAppAsyncTask() {

                PreferenceData.setKeyLoginSuccess(false);
                Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

                nextStepHandler.sendEmptyMessage(NEXT_LOGIN);
            }
        });

        task.execute(ApiValue.API_USER_LOGIN, PreferenceData.getKeyUserId(), PreferenceData.getKeyUserPw());
    }


    /**
     * 메인 드라마 리스트를 받아오는 task
     */
    private void callMainDramaTask() {

        MainDListAsyncTask MainDListAsyncTask = new MainDListAsyncTask(new MainDListAsyncTask.MainDListResultHandler() {

            @Override
            public void onSuccessAppAsyncTask(DramaListResult result) {

                if(result != null){

                    if(result.isSuccess()){

                        dramaVOS = result.getDramaVOArrayList();

                    }else{
                        Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }



                nextStepHandler.sendEmptyMessage(NEXT_GET_CATEGORY);
            }

            @Override
            public void onFailAppAsysncask() {


                Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

                nextStepHandler.sendEmptyMessage(NEXT_GET_CATEGORY);
            }

            @Override
            public void onCancelAppAsyncTask() {

                Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

                nextStepHandler.sendEmptyMessage(NEXT_GET_CATEGORY);
            }

        });

        MainDListAsyncTask.execute(ApiValue.API_RANDOM_DRAMA);
    }

    /**
     * 자동 로그인하고 내 찜목록 전부 받아오는 task
     */
    private void callMyProduct(){

        MypageProductAsyncTask mypageProductAsyncTask = new MypageProductAsyncTask(new MypageProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(MyPageProductResult result) {

                D.log("TAG", result.isSuccess() + "\n" + result.getMypageProductList());

                if (result.isSuccess()) {

                    if (result.getMypageProductList() != null && result.getMypageProductList().size() > 0) {

                        DbController.deleteAll(IntroActivity.this);

                        for(ProductVO item : result.getMypageProductList()){
                            DbController.addProductId(IntroActivity.this, item.getP_id());
                        }

                    }
                }

                nextStepHandler.sendEmptyMessage(NEXT_MAIN);
            }

            @Override
            public void onFailAppAsysncask() {


                nextStepHandler.sendEmptyMessage(NEXT_MAIN);
                Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                nextStepHandler.sendEmptyMessage(NEXT_MAIN);
                Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });

        mypageProductAsyncTask.execute(ApiValue.API_MYPAGE, PreferenceData.getKeyUserId());
    }


    /**
     * 카테고리 리스트 받아오는 task
     */
    private void callCategory(){
        CategoryAsyncTask categoryAsyncTask = new CategoryAsyncTask(new CategoryAsyncTask.CategoryResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(CategoryResult result) {

                if(result.isSuccess()){

                    if(result.getCategoryList() != null && result.getCategoryList().size() > 0){

                        DbController.categoryDeleteAll(IntroActivity.this);

                        DbController.addCategoryData(IntroActivity.this, " ", "전체");

                        for(CategoryVO item : result.getCategoryList()){
                            DbController.addCategoryData(IntroActivity.this, item.getP_cat(), item.getP_cat_name());
                        }

                    }


                }else{

                    Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }


                nextStepHandler.sendEmptyMessage(NEXT_LOGIN);
            }

            @Override
            public void onFailAppAsysncask() {

                Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();


                nextStepHandler.sendEmptyMessage(NEXT_LOGIN);
            }

            @Override
            public void onCancelAppAsyncTask() {

                Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();


                nextStepHandler.sendEmptyMessage(NEXT_LOGIN);
            }
        });


        categoryAsyncTask.execute(ApiValue.API_CATEGORY);
    }
}
