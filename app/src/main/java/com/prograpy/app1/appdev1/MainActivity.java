package com.prograpy.app1.appdev1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.DramaVO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TopbarView mainTopbarView;

    private ViewPager mainPager;
    private MainViewPagerAdapter mainViewPagerAdapter;

    private TabLayout mainBottomTab;

    private ArrayList<DramaVO> dramaDataList = new ArrayList<DramaVO>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent() != null){
            dramaDataList = getIntent().getParcelableArrayListExtra("dramaList");
        }


        mainTopbarView = (TopbarView) findViewById(R.id.title);
        mainTopbarView.setType(TopbarView.TOPBAR_TYPE.MAIN);
        mainTopbarView.setTopMenuClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {

            }
        });

        mainBottomTab = (TabLayout) findViewById(R.id.main_tab);
        mainViewPagerAdapter = new MainViewPagerAdapter(this.getSupportFragmentManager(), dramaDataList);

        mainPager = (ViewPager) findViewById(R.id.main_viewpager);
        mainPager.setOffscreenPageLimit(4);
        mainPager.setAdapter(mainViewPagerAdapter);

        mainBottomTab.setupWithViewPager(mainPager, false);

    }
    //f8누르면 한줄 아래로
    //f9 함수 빠져나가기, 다음 브레이킹 포인트로 넘어가기





    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onClick(View v) {

    }

}
