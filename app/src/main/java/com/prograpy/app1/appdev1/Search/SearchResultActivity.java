package com.prograpy.app1.appdev1.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.drama.item.adapter.MainProductListAdapter;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.SearchResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.SearchAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    String searchname;
    private TopbarView topbarView;
    private NetworkProgressDialog networkProgressDialog;
    private ArrayList<ProductVO> ProductList = new ArrayList<ProductVO>();
    private RecyclerView searchItemListView;
    private MainProductListAdapter searchItemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        Intent intent = getIntent();
        searchname = intent.getExtras().getString("searchname");
        Log.d("searchname",searchname);


        topbarView = (TopbarView) findViewById(R.id.title);
        topbarView.setType(TopbarView.TOPBAR_TYPE.BACK_TITLE);
        topbarView.setTopBarTitle("검색 결과");
        topbarView.setTopMenuBackClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                finish();
            }
        });
        networkProgressDialog = new NetworkProgressDialog(this);

        searchItemListView = (RecyclerView) findViewById(R.id.item_list);

        searchItemListAdapter = new MainProductListAdapter(getApplicationContext());

        //searchItemListAdapter.setOnItemClickListener(itemPopupListener);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchItemListView.setLayoutManager(layoutManager);
        searchItemListView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        searchItemListView.setLayoutManager(gridLayoutManager);

        searchItemListView.setAdapter(searchItemListAdapter);

        networkProgressDialog = new NetworkProgressDialog(this);


       networkProgressDialog.show();

        search();

    }

    public void search() {
        SearchAsyncTask searchAsyncTask = new SearchAsyncTask(new SearchAsyncTask.SearchHandler() {


            @Override
            public void onSuccessAppAsyncTask(SearchResult result) {

                if(result != null){

                    if(result.success && result.productVOArrayList != null && result.productVOArrayList.size() > 0){
                        networkProgressDialog.dismiss();
                        searchItemListAdapter.setProductData(result.productVOArrayList);
                        searchItemListAdapter.notifyDataSetChanged();

                    }else{
                        Toast.makeText(SearchResultActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SearchResultActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailAppAsysncask() {
                networkProgressDialog.dismiss();

                Toast.makeText(SearchResultActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelAppAsyncTask() {
                networkProgressDialog.dismiss();

                Toast.makeText(SearchResultActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });


        searchAsyncTask.execute(ApiValue.API_SEARCH,searchname);
    }


}



