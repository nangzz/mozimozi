package com.prograpy.app1.appdev1.drama.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.drama.item.adapter.DramaBestItemListAdapter;
import com.prograpy.app1.appdev1.drama.item.adapter.DramaItemListAdapter;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.DramaItemListResult;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.productInfo.ProductInfoActivity;
import com.prograpy.app1.appdev1.task.DramaProductAsyncTask;
import com.prograpy.app1.appdev1.task.DramaTopProductAsyncTask;
import com.prograpy.app1.appdev1.task.HeartAsyncTask;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.CategoryVO;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;


public class DramaItemListActivity extends AppCompatActivity {

    private String[] categoryNames = null;
    private String[] categoryValues = null;
    private String[] actorNames = null;
    String[] items = {"카테고리", "출연배우"};
    private NetworkProgressDialog networkProgressDialog;

    private TopbarView topbarView;

    private RecyclerView dramaBestItemListView;
    private DramaBestItemListAdapter bestItemListAdapter;

    private RecyclerView dramaItemListView;
    private DramaItemListAdapter dramaItemListAdapter;

    private Spinner oneDepthSpinner;
    private Spinner twoDepthSpinner;
    String actorname = " ";
    String category = " ";
    String select = "카테고리";

    private boolean isFinishTopTask = false;
    private boolean isFinishProductTask = false;

    private boolean isSelectCate = true;

    private int dramaId = 0;


    private View.OnClickListener itemActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            ProductVO vo = (ProductVO) v.getTag();

            intent = new Intent(DramaItemListActivity.this, ProductInfoActivity.class);

            intent.putExtra("title", vo.getP_name());
            intent.putExtra("dramaId", dramaId);
            intent.putExtra("img", vo.getP_img());

            startActivity(intent);
        }
    };

    private View.OnClickListener itemHeartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // 로그인에 성공하지 못한 사용자가 눌러버림
            if(!PreferenceData.getKeyLoginSuccess()){
                Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.not_login_click_heart), Toast.LENGTH_SHORT).show();
                return;
            }

            ProductVO vo = (ProductVO) v.getTag();

            HeartAsyncTask heartAsyncTask = new HeartAsyncTask(DramaItemListActivity.this, new HeartAsyncTask.TaskResultHandler() {
                @Override
                public void onSuccessAppAsyncTask(ServerSuccessCheckResult result) {
                    networkProgressDialog.dismiss();

                    bestItemListAdapter.notifyDataSetChanged();
                    dramaItemListAdapter.notifyDataSetChanged();

                    if(!result.isSuccess()){
                        Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailAppAsysncask() {
                    networkProgressDialog.dismiss();
                    Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelAppAsyncTask() {
                    networkProgressDialog.dismiss();
                    Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }
            });


            networkProgressDialog.show();

            heartAsyncTask.execute(DbController.isOverlapData(DramaItemListActivity.this, vo.getP_id()) ? ApiValue.API_HEART_REMOVE : ApiValue.API_HEART_CHECK,
                    PreferenceData.getKeyUserId(), String.valueOf(vo.getP_id()));

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        setContentView(R.layout.activity_item_list);


        ArrayList<CategoryVO> categoryList = DbController.getAllCategoryData(this);

        categoryNames = new String[categoryList.size()];
        categoryValues = new String[categoryList.size()];


        for(int i = 0; i < categoryList.size(); i ++){
            categoryNames[i] = categoryList.get(i).getP_cat_name();
            categoryValues[i] = categoryList.get(i).getP_cat();
        }

        dramaId = getIntent().getIntExtra("dramaId", 0);
        actorNames = getIntent().getStringExtra("actors").trim().split(",");

        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle(getIntent().getStringExtra("title"));
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });

        dramaBestItemListView = (RecyclerView) findViewById(R.id.best_item_list);
        dramaItemListView = (RecyclerView) findViewById(R.id.item_list);
        oneDepthSpinner = (Spinner) findViewById(R.id.one_depth_spinner);
        twoDepthSpinner = (Spinner) findViewById(R.id.two_depth_spinner);


        dramaItemListView.setNestedScrollingEnabled(false);

        bestItemListAdapter = new DramaBestItemListAdapter(getApplicationContext(), itemActivityListener, itemHeartListener);
        dramaItemListAdapter = new DramaItemListAdapter(getApplicationContext(), itemActivityListener, itemHeartListener);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dramaBestItemListView.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        dramaItemListView.setLayoutManager(gridLayoutManager);

        dramaBestItemListView.setAdapter(bestItemListAdapter);
        dramaItemListView.setAdapter(dramaItemListAdapter);

        networkProgressDialog = new NetworkProgressDialog(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oneDepthSpinner.setAdapter(adapter);

        oneDepthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = items[position];
                select();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        networkProgressDialog.show();


        callDramaTopItemList();

    }


    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }


    public void select() {

        if (select.equals("카테고리")) {

            isSelectCate = true;

            Log.d("TAG", String.valueOf(oneDepthSpinner.getSelectedItem().toString()));

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames);

            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            twoDepthSpinner.setAdapter(adapter1);
            twoDepthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    category = categoryValues[position];

                    networkProgressDialog.show();
                    callDramaItemList();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });
        }

        if (select.equals("출연배우")) {

            isSelectCate = false;

            Log.d("TAG", String.valueOf(oneDepthSpinner.getSelectedItem().toString()));

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, actorNames);

            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            twoDepthSpinner.setAdapter(adapter2);
            twoDepthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(actorNames[position].equals("전체"))
                        actorname = " ";
                    else
                        actorname = actorNames[position].trim();

                    networkProgressDialog.show();
                    callDramaItemList();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });
        }
    }


    private void callDramaTopItemList() {
        DramaTopProductAsyncTask dramaTopProductAsyncTask = new DramaTopProductAsyncTask(new DramaTopProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(DramaItemListResult result) {
                isFinishTopTask = true;

                if (isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                if (result != null) {

                    if (result.isSuccess()) {

                        if (result.getProducts() != null && result.getProducts().size() > 0) {
                            bestItemListAdapter.setDramaProductData(result.getProducts());
                            bestItemListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailAppAsysncask() {

                isFinishTopTask = true;

                if (isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                isFinishTopTask = true;

                if (isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });
        dramaTopProductAsyncTask.execute(ApiValue.API_DRAMA_TOP_PRODUCT, String.valueOf(dramaId));
    }


    private void callDramaItemList() {
        DramaProductAsyncTask dramaProductAsyncTask = new DramaProductAsyncTask(new DramaProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(DramaItemListResult result) {

                isFinishProductTask = true;

                if (isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                if (result.isSuccess()) {

                    if (result.getProducts() != null && result.getProducts().size() > 0) {
                        dramaItemListAdapter.setDramaProductData(result.getProducts());
                        dramaItemListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DramaItemListActivity.this,
                                isSelectCate ? getResources().getString(R.string.not_cate_product) : getResources().getString(R.string.not_act_product),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {

                    if(result.getProducts() == null)
                        Toast.makeText(DramaItemListActivity.this,
                                isSelectCate ? getResources().getString(R.string.not_cate_product) : getResources().getString(R.string.not_act_product),
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailAppAsysncask() {

                isFinishProductTask = true;

                if (isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();
                Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                isFinishProductTask = true;

                if (isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });
        dramaProductAsyncTask.execute(isSelectCate ? ApiValue.API_DRAMA_PRODUCT : ApiValue.API_ACTOR_PRODUCT,
                String.valueOf(dramaId), isSelectCate ? category : actorname);
    }


}
