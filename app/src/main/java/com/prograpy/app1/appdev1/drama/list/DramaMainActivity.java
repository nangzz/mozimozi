package com.prograpy.app1.appdev1.drama.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.drama.item.DramaItemListActivity;
import com.prograpy.app1.appdev1.view.TopbarView;

import java.util.ArrayList;
import java.util.List;

public class DramaMainActivity extends AppCompatActivity {

    private DramaListAdapter dramaListAdapter;
    private RecyclerView recyclerView;

    private TopbarView topbarView;


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(DramaMainActivity.this, DramaItemListActivity.class);
            i.putExtra("title", (String)v.getTag());
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


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<DramaItemData> dramaItemData = new ArrayList<>();
        DramaItemData[] dramaItemData_array = new DramaItemData[7];
        dramaItemData_array[0] = new DramaItemData(R.drawable.poster_my_golden, "황금빛내인생", "신혜선, 박시후, 이태환, 서은수, 천호진", "#서은수니트 #이태성코트");
        dramaItemData_array[1] = new DramaItemData(R.drawable.poster_love_returns, "미워도 사랑해", "표예진, 이성열, 이동하, 한혜린, 송옥숙", "#표예진가방 #표예진귀걸이");
        dramaItemData_array[2] = new DramaItemData(R.drawable.poster_live_together, "같이 살래요", "유동근, 장미희, 한지혜, 이상우, 박선영", "#이상우맨투맨 #여회현티셔츠");
        dramaItemData_array[3] = new DramaItemData(R.drawable.poster_misty, "미스티", "김남주, 지진희, 전혜진, 임태경", "#김남주가방");
        dramaItemData_array[4] = new DramaItemData(R.drawable.poster_mother, "마더", "이보영, 허율, 이혜영", "#이보영가디건");
        dramaItemData_array[5] = new DramaItemData(R.drawable.poster_return, "리턴", "박진희, 이진욱, 신성록, 봉태규, 박기웅", "#박진희가방 #박진희구두");
        dramaItemData_array[6] = new DramaItemData(R.drawable.poster_to_you, "시를 잊은 그대에게", "이유비, 이준혁, 장동윤", "#이유비후드");


        for(int i = 0; i < 7; i++)
            dramaItemData.add(dramaItemData_array[i]);

        dramaListAdapter = new DramaListAdapter(getApplicationContext(), dramaItemData, R.layout.activity_darama_list_main, listener);
        recyclerView.setAdapter(dramaListAdapter);

    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
