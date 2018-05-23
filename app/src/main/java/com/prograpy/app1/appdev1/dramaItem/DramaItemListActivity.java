package com.prograpy.app1.appdev1.dramaItem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.dramaItem.adapter.DramaBestItemListAdapter;
import com.prograpy.app1.appdev1.dramaItem.adapter.DramaItemListAdapter;
import com.prograpy.app1.appdev1.view.TopbarView;


public class DramaItemListActivity extends AppCompatActivity{


    private TopbarView topbarView;

    private RecyclerView dramaBestItemListView;
    private DramaBestItemListAdapter bestItemListAdapter;

    private RecyclerView dramaItemListView;
    private DramaItemListAdapter dramaItemListAdapter;

    private Spinner oneDepthSpinner;
    private Spinner twoDepthSpinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0, 0);

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

        bestItemListAdapter = new DramaBestItemListAdapter();
        dramaItemListAdapter = new DramaItemListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dramaBestItemListView.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        dramaItemListView.setLayoutManager(gridLayoutManager);

        dramaBestItemListView.setAdapter(bestItemListAdapter);
        dramaItemListView.setAdapter(dramaItemListAdapter);
    }


}
