package com.prograpy.app1.appdev1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.prograpy.app1.appdev1.favorite.FavoriteFragment;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.DramaVO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TopbarView mainTopbarView;

    private ViewPager mainPager;
    private MainViewPagerAdapter mainViewPagerAdapter;

    private ImageView tabHome, tabZzim, tabSearch, tabMy;
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


        tabHome = (ImageView) findViewById(R.id.tab_home);
        tabZzim = (ImageView) findViewById(R.id.tab_zzim);
        tabSearch = (ImageView) findViewById(R.id.tab_search);
        tabMy = (ImageView) findViewById(R.id.tab_my);

        tabHome.setOnClickListener(this);
        tabZzim.setOnClickListener(this);
        tabSearch.setOnClickListener(this);
        tabMy.setOnClickListener(this);

        tabHome.setSelected(true);

        mainViewPagerAdapter = new MainViewPagerAdapter(this.getSupportFragmentManager(), dramaDataList);

        mainPager = (ViewPager) findViewById(R.id.main_viewpager);
        mainPager.setOffscreenPageLimit(4);
        mainPager.setAdapter(mainViewPagerAdapter);
        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                switch (position){


                    case 0:
                        tabHome.setSelected(true);
                        tabSearch.setSelected(false);
                        tabMy.setSelected(false);
                        tabZzim.setSelected(false);
                        break;

                    case 1:
                        tabHome.setSelected(false);
                        tabSearch.setSelected(false);
                        tabMy.setSelected(false);
                        tabZzim.setSelected(true);
                        break;

                    case 2:
                        tabHome.setSelected(false);
                        tabSearch.setSelected(true);
                        tabMy.setSelected(false);
                        tabZzim.setSelected(false);
                        break;

                    case 3:
                        tabHome.setSelected(false);
                        tabSearch.setSelected(false);
                        tabMy.setSelected(true);
                        tabZzim.setSelected(false);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
    //f8누르면 한줄 아래로
    //f9 함수 빠져나가기, 다음 브레이킹 포인트로 넘어가기





    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onClick(View v) {
        int position = 0;

        switch (v.getId()){

            case R.id.tab_home:
                tabHome.setSelected(true);
                tabSearch.setSelected(false);
                tabMy.setSelected(false);
                tabZzim.setSelected(false);
                position = 0;
                break;

            case R.id.tab_zzim:
                tabHome.setSelected(false);
                tabSearch.setSelected(false);
                tabMy.setSelected(false);
                tabZzim.setSelected(true);
                position = 1;
                break;

            case R.id.tab_search:
                tabHome.setSelected(false);
                tabSearch.setSelected(true);
                tabMy.setSelected(false);
                tabZzim.setSelected(false);
                position = 2;
                break;

            case R.id.tab_my:
                tabHome.setSelected(false);
                tabSearch.setSelected(false);
                tabMy.setSelected(true);
                tabZzim.setSelected(false);
                position = 3;
                break;
        }

        mainPager.setCurrentItem(position, true);
    }

}
