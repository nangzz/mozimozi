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


            DramaVO dramaVO = (DramaVO) v.getTag();

            Intent i = new Intent(DramaMainActivity.this, DramaItemListActivity.class);
            i.putExtra("title", dramaVO.getD_name());
            i.putExtra("dramaId", dramaVO.getD_id());
            i.putExtra("actors", "전체," + dramaVO.getD_act());

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


    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
