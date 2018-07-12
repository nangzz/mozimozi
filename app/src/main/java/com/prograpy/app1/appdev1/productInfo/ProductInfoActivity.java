package com.prograpy.app1.appdev1.productInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.drama.item.adapter.DramaItemListAdapter;
import com.prograpy.app1.appdev1.mypage.MypageMainActivity;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.DramaItemListResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.DramaProductAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;


public class ProductInfoActivity extends AppCompatActivity{

    private NetworkProgressDialog networkProgressDialog;

    private TopbarView topbarView;

    private RecyclerView dramaItemListView;
    private DramaItemListAdapter dramaItemListAdapter;

    private View.OnClickListener itemActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            intent = new Intent(ProductInfoActivity.this, ProductInfoActivity.class);
            intent.putExtra("type", ((TextView) v).getText().toString());
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        setContentView(R.layout.activity_product_info);


        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle(getIntent().getStringExtra("title"));
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });

        dramaItemListView = (RecyclerView) findViewById(R.id.item_list);
        dramaItemListView.setNestedScrollingEnabled(false);

        dramaItemListAdapter = new DramaItemListAdapter(getApplicationContext(), itemActivityListener);
        dramaItemListAdapter.setOnItemClickListener(itemActivityListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        dramaItemListView.setLayoutManager(gridLayoutManager);

        dramaItemListView.setAdapter(dramaItemListAdapter);

        networkProgressDialog = new NetworkProgressDialog(this);


        networkProgressDialog.show();

        DramaProductAsyncTask dramaProductAsyncTask = new DramaProductAsyncTask(new DramaProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(DramaItemListResult result) {

                networkProgressDialog.dismiss();

                if(result != null){
                    Log.d("TAG", result.isSuccess() + "\n" + result.getProducts());

                    if(result.isSuccess()){

                        if(result.getProducts() != null && result.getProducts().size() > 0){
                            dramaItemListAdapter.setDramaProductData(result.getProducts());
                            dramaItemListAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(ProductInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(ProductInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(ProductInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailAppAsysncask() {

                networkProgressDialog.dismiss();

                Toast.makeText(ProductInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                networkProgressDialog.dismiss();

                Toast.makeText(ProductInfoActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });
        dramaProductAsyncTask.execute(ApiValue.API_DRAMA_PRODUCT, String.valueOf(getIntent().getIntExtra("dramaId", 0)));


    }


    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }


}
