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
import android.widget.Spinner;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.drama.item.adapter.DramaBestItemListAdapter;
import com.prograpy.app1.appdev1.drama.item.adapter.DramaItemListAdapter;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.DramaItemListResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.productInfo.ProductInfoActivity;
import com.prograpy.app1.appdev1.task.DramaProductAsyncTask;
import com.prograpy.app1.appdev1.task.DramaTopProductAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;


public class DramaItemListActivity extends AppCompatActivity{

    private NetworkProgressDialog networkProgressDialog;

    private TopbarView topbarView;

    private RecyclerView dramaBestItemListView;
    private DramaBestItemListAdapter bestItemListAdapter;

    private RecyclerView dramaItemListView;
    private DramaItemListAdapter dramaItemListAdapter;

    private Spinner oneDepthSpinner;
    private Spinner twoDepthSpinner;

    String category = null;
    private boolean isFinishTopTask = false;
    private boolean isFinishProductTask = false;


    private ArrayList<ProductVO> productItem = new ArrayList<ProductVO>();
    private ArrayList<DramaVO> dramaList = new ArrayList<DramaVO>();

    private View.OnClickListener itemActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            intent = new Intent(DramaItemListActivity.this, ProductInfoActivity.class);

            intent.putExtra("title", (String)v.getTag());
            intent.putExtra("dramaId", v.getId());

            startActivity(intent);
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        setContentView(R.layout.activity_item_list);


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

        bestItemListAdapter = new DramaBestItemListAdapter(getApplicationContext(), itemActivityListener);
        dramaItemListAdapter = new DramaItemListAdapter(getApplicationContext(), itemActivityListener);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dramaBestItemListView.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        dramaItemListView.setLayoutManager(gridLayoutManager);

        dramaBestItemListView.setAdapter(bestItemListAdapter);
        dramaItemListView.setAdapter(dramaItemListAdapter);

        networkProgressDialog = new NetworkProgressDialog(this);


        category = twoDepthSpinner.getSelectedItem().toString();



        networkProgressDialog.show();


        callDramaTopItemList();
        callDramaItemList();


    }


    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }



    private void callDramaTopItemList(){
        DramaTopProductAsyncTask dramaTopProductAsyncTask = new DramaTopProductAsyncTask(new DramaTopProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(DramaItemListResult result) {
                isFinishTopTask = true;

                if(isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                if(result != null){
                    Log.d("TAG", result.isSuccess() + "\n" + result.getProducts());

                    if(result.isSuccess()){

                        if(result.getProducts() != null && result.getProducts().size() > 0){
                            bestItemListAdapter.setDramaProductData(result.getProducts());
                            bestItemListAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailAppAsysncask() {

                isFinishTopTask = true;

                if(isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                isFinishTopTask = true;

                if(isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });
        dramaTopProductAsyncTask.execute(ApiValue.API_DRAMA_TOP_PRODUCT, String.valueOf(getIntent().getIntExtra("dramaId", 0)));
    }


    private void callDramaItemList(){
        DramaProductAsyncTask dramaProductAsyncTask = new DramaProductAsyncTask(new DramaProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(DramaItemListResult result) {

                isFinishProductTask = true;

                if(isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                if(result != null){
                    Log.d("TAG", result.isSuccess() + "\n" + result.getProducts());

                    if(result.isSuccess()){

                        if(result.getProducts() != null && result.getProducts().size() > 0){
                            dramaItemListAdapter.setDramaProductData(result.getProducts());
                            dramaItemListAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailAppAsysncask() {

                isFinishProductTask = true;

                if(isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();
                Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                isFinishProductTask = true;

                if(isFinishTopTask && isFinishProductTask)
                    networkProgressDialog.dismiss();

                Toast.makeText(DramaItemListActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });
        dramaProductAsyncTask.execute(ApiValue.API_DRAMA_PRODUCT, String.valueOf(getIntent().getIntExtra("dramaId", 0)), category);
    }


}
