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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.drama.item.adapter.DramaItemListAdapter;
import com.prograpy.app1.appdev1.home.HomeDramaAdapter;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.CategoryProductResult;
import com.prograpy.app1.appdev1.network.response.DramaItemListResult;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.ActorProductAsyncTask;
import com.prograpy.app1.appdev1.task.HeartAsyncTask;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.ProductVO;


public class ProductInfoActivity extends AppCompatActivity{

    private NetworkProgressDialog networkProgressDialog;

    private TopbarView topbarView;

    private RecyclerView dramaItemListView;
    private DramaItemListAdapter dramaItemListAdapter;

    private int dramaId =0;
    private String itemImage = "";

    private TextView infoItemTitle;
   private ImageView itemImageView;

    private View.OnClickListener itemActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            ProductVO vo = (ProductVO) v.getTag();

            intent = new Intent(ProductInfoActivity.this, ProductInfoActivity.class);
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
                Toast.makeText(ProductInfoActivity.this, getResources().getString(R.string.not_login_click_heart), Toast.LENGTH_SHORT).show();
                return;
            }

            ProductVO vo = (ProductVO) v.getTag();

            HeartAsyncTask heartAsyncTask = new HeartAsyncTask(ProductInfoActivity.this, new HeartAsyncTask.TaskResultHandler() {
                @Override
                public void onSuccessAppAsyncTask(ServerSuccessCheckResult result) {

                    networkProgressDialog.dismiss();
                    dramaItemListAdapter.notifyDataSetChanged();

                    if(!result.isSuccess()){
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


            networkProgressDialog.show();

            heartAsyncTask.execute(DbController.isOverlapData(ProductInfoActivity.this, vo.getP_id()) ? ApiValue.API_HEART_REMOVE : ApiValue.API_HEART_CHECK,
                    PreferenceData.getKeyUserId(), String.valueOf(vo.getP_id()));

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        setContentView(R.layout.activity_product_info);

        dramaId = getIntent().getIntExtra("dramaId", 0 );

        infoItemTitle = (TextView)findViewById(R.id.info_item_title);
        infoItemTitle.setText(getIntent().getStringExtra("title"));
        itemImageView = (ImageView)findViewById(R.id.info_img_content);
        itemImage  = getIntent().getStringExtra("img");

        Glide.with(this).load(itemImage).into( itemImageView);

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

        dramaItemListAdapter = new DramaItemListAdapter(getApplicationContext(), itemActivityListener, itemHeartListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        dramaItemListView.setLayoutManager(gridLayoutManager);

        dramaItemListView.setAdapter(dramaItemListAdapter);

        networkProgressDialog = new NetworkProgressDialog(this);


        networkProgressDialog.show();

        ActorProductAsyncTask actorProductAsyncTask = new ActorProductAsyncTask(new ActorProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(DramaItemListResult result) {

                networkProgressDialog.dismiss();

                if(result != null){
                    Log.d("TAG", result.success + "\n" + result.getProducts());

                    if(result.success){

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
        actorProductAsyncTask.execute(ApiValue.API_ACTOR_PRODUCT, String.valueOf(dramaId),"김선아");


    }


    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }


}
