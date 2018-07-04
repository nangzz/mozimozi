package com.prograpy.app1.appdev1.drama.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.drama.item.DramaItemListActivity;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.DramaListResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.task.DramaListAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.DramaVO;

import java.util.ArrayList;

public class DramaMainActivity extends AppCompatActivity {

    private NetworkProgressDialog networkProgressDialog;

    private DramaListAdapter dramaListAdapter;
    private RecyclerView recyclerView;

    private TopbarView topbarView;

    private ArrayList<DramaVO> dramaList = new ArrayList<DramaVO>();


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent i = new Intent(DramaMainActivity.this, DramaItemListActivity.class);
            i.putExtra("title", (String)v.getTag());

            for(DramaVO dramaVO : dramaList){
                if(((String)v.getTag()).equals(dramaVO.getD_name())){
                    i.putExtra("dramaId", dramaVO.getD_id());
                    break;
                }
            }

            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        setContentView(R.layout.activity_darama_list_main);


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

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        dramaListAdapter = new DramaListAdapter(getApplicationContext(), listener);
        recyclerView.setAdapter(dramaListAdapter);


        networkProgressDialog.show();

        DramaListAsyncTask dramaListAsyncTask = new DramaListAsyncTask(new DramaListAsyncTask.DramaListResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(DramaListResult result) {

                networkProgressDialog.dismiss();

                if(result != null){
                    Log.d("TAG", result.isSuccess() + "\n" + result.getDramaVOArrayList() );

                    if(result.isSuccess()){

                        if(result.getDramaVOArrayList() != null && result.getDramaVOArrayList().size() > 0){
                            dramaList = result.getDramaVOArrayList();
                            dramaListAdapter.setDramaItemData(result.getDramaVOArrayList());
                            dramaListAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(DramaMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(DramaMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(DramaMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailAppAsysncask() {

                networkProgressDialog.dismiss();

                Toast.makeText(DramaMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                networkProgressDialog.dismiss();

                Toast.makeText(DramaMainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });
        dramaListAsyncTask.execute(ApiValue.API_DRAMA, getIntent().getStringExtra("type"));


    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
