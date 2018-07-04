package com.prograpy.app1.appdev1.mypage;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;

import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.CategoryResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.popup.info.CustomPopup;
import com.prograpy.app1.appdev1.task.MypageProductAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;


public class MypageMainActivity extends AppCompatActivity {

    private NetworkProgressDialog networkProgressDialog;

    private MyPageListAdapter myPageListAdapter;
    private RecyclerView recyclerView;

    private TopbarView topbarView;

    private View.OnClickListener itemPopupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CustomPopup infoPopup = new CustomPopup(MypageMainActivity.this);
            infoPopup.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        setContentView(R.layout.activity_mypage_main);


        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle(getIntent().getStringExtra("type"));
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });

        networkProgressDialog = new NetworkProgressDialog(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_info);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MypageMainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);


/*        List<MyPageItemData> myPageItemData = new ArrayList<>();
        MyPageItemData[] myPageItemData_array = new MyPageItemData[7];
        myPageItemData_array[0] = new MyPageItemData(R.drawable.my_golden_life_bag1, "52회 서은수 가방", "빈치스", "239,230원");
        myPageItemData_array[1] = new MyPageItemData(R.drawable.my_golden_life_bag2, "33회 서은수 가방", "만다리나덕", "297,310원");
        myPageItemData_array[2] = new MyPageItemData(R.drawable.my_golden_life_acc1, "52회 서은수 안경", "젠틀몬스터", "250,000원");
        myPageItemData_array[3] = new MyPageItemData(R.drawable.my_golden_life_acc2, "52회 신혜선 귀걸이", "러브캣 비쥬", "142,560원");
        myPageItemData_array[4] = new MyPageItemData(R.drawable.my_golden_life_shirt1, "52회 서은수 티셔츠", "코인코즈", "31,200원");
        myPageItemData_array[5] = new MyPageItemData(R.drawable.my_golden_life_shoes1, "52회 신혜선 운동화", "슈콤마보니", "305,760원");
        myPageItemData_array[6] = new MyPageItemData(R.drawable.my_golden_life_pants1, "37회 신혜선 청바지", "레이브", "89,180원");


        for(int i = 0; i < 7; i++)
            myPageItemData.add(myPageItemData_array[i]);

        myPageListAdapter = new MyPageListAdapter(getApplicationContext(), myPageItemData, R.layout.activity_mypage_main);
        recyclerView.setAdapter(myPageListAdapter);
*/

        myPageListAdapter = new MyPageListAdapter(getApplicationContext(), itemPopupListener);
        recyclerView.setAdapter(myPageListAdapter);

        networkProgressDialog.show();

        MypageProductAsyncTask mypageProductAsyncTask = new MypageProductAsyncTask(new MypageProductAsyncTask.CategoryResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(CategoryResult result) {

                networkProgressDialog.dismiss();

                if(result != null){
                    Log.d("TAG", result.success + "\n" + result.mypageProductList );

                    if(result.success){

                        if(result.mypageProductList != null && result.mypageProductList.size() > 0){
                            myPageListAdapter.setMyPageItemData(result.mypageProductList);
                            myPageListAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(MypageMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(MypageMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(MypageMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailAppAsysncask() {

                networkProgressDialog.dismiss();

                Toast.makeText(MypageMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                networkProgressDialog.dismiss();

                Toast.makeText(MypageMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });

        mypageProductAsyncTask.execute(ApiValue.API_MYPAGE, getIntent().getStringExtra("type"));

    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
