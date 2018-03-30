package com.prograpy.app1.appdev1.mypage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prograpy.app1.appdev1.R;

import java.util.ArrayList;
import java.util.List;

public class MypageMainActivity extends AppCompatActivity {

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_info);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MypageMainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        List<ItemData> itemData = new ArrayList<>();
        ItemData[] itemData_array = new ItemData[7];
        itemData_array[0] = new ItemData(R.drawable.my_golden_life_bag1, "52회 서은수 가방", "빈치스", "239,230원");
        itemData_array[1] = new ItemData(R.drawable.my_golden_life_bag2, "33회 서은수 가방", "만다리나덕", "297,310원");
        itemData_array[2] = new ItemData(R.drawable.my_golden_life_acc1, "52회 서은수 안경", "젠틀몬스터", "250,000원");
        itemData_array[3] = new ItemData(R.drawable.my_golden_life_acc2, "52회 신혜선 귀걸이", "러브캣 비쥬", "142,560원");
        itemData_array[4] = new ItemData(R.drawable.my_golden_life_shirt1, "52회 서은수 티셔츠", "코인코즈", "31,200원");
        itemData_array[5] = new ItemData(R.drawable.my_golden_life_shoes1, "52회 신혜선 운동화", "슈콤마보니", "305,760원");
        itemData_array[6] = new ItemData(R.drawable.my_golden_life_pants1, "37회 신혜선 청바지", "레이브", "89,180원");


        for(int i = 0; i < 7; i++)
            itemData.add(itemData_array[i]);

        adapter = new Adapter(getApplicationContext(), itemData, R.layout.mypage_main);
        recyclerView.setAdapter(adapter);

    }
}
