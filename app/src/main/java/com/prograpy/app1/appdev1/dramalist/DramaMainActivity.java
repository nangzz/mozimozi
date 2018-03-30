package com.prograpy.app1.appdev1.dramalist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.view.TopbarView;

import java.util.ArrayList;
import java.util.List;

public class DramaMainActivity extends AppCompatActivity {

    private Adapter adapter;
    private RecyclerView recyclerView;

    private TopbarView topbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.darama_list_main);


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

        List<ItemData> itemData = new ArrayList<>();
        ItemData[] itemData_array = new ItemData[7];
        itemData_array[0] = new ItemData(R.drawable.poster_my_golden, "황금빛내인생", "신혜선, 박시후, 이태환, 서은수, 천호진", "#서은수니트 #이태성코트");
        itemData_array[1] = new ItemData(R.drawable.poster_love_returns, "미워도 사랑해", "표예진, 이성열, 이동하, 한혜린, 송옥숙", "#표예진가방 #표예진귀걸이");
        itemData_array[2] = new ItemData(R.drawable.poster_live_together, "같이 살래요", "유동근, 장미희, 한지혜, 이상우, 박선영", "#이상우맨투맨 #여회현티셔츠");
        itemData_array[3] = new ItemData(R.drawable.poster_misty, "미스티", "김남주, 지진희, 전혜진, 임태경", "#김남주가방");
        itemData_array[4] = new ItemData(R.drawable.poster_mother, "마더", "이보영, 허율, 이혜영", "#이보영가디건");
        itemData_array[5] = new ItemData(R.drawable.poster_return, "리턴", "박진희, 이진욱, 신성록, 봉태규, 박기웅", "#박진희가방 #박진희구두");
        itemData_array[6] = new ItemData(R.drawable.poster_to_you, "시를 잊은 그대에게", "이유비, 이준혁, 장동윤", "#이유비후드");


        for(int i = 0; i < 7; i++)
            itemData.add(itemData_array[i]);

        adapter = new Adapter(getApplicationContext(), itemData, R.layout.darama_list_main);
        recyclerView.setAdapter(adapter);

    }
}
