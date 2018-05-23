package com.prograpy.app1.appdev1;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app1.appdev1.drama.list.DramaMainActivity;
import com.prograpy.app1.appdev1.join.ProvisionActivity;
import com.prograpy.app1.appdev1.lib.mainlist.CarouselAdapter;
import com.prograpy.app1.appdev1.lib.mainlist.CarouselViewPager;
import com.prograpy.app1.appdev1.lib.mainlist.MainDramaData;
import com.prograpy.app1.appdev1.mypage.MypageMainActivity;
import com.prograpy.app1.appdev1.network.response.MainDramaResult;
import com.prograpy.app1.appdev1.task.MainDramaAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TopbarView mainTopbarView;
    private DrawerLayout mainDrawerView;
    private NavigationView mainSlideNaviView;

    private ActionBarDrawerToggle drawerToggle;

    private CarouselViewPager carousel;


    private TextView menuMyPage;
    private ImageView menuSbs;
    private ImageView menuKbs;

    private TextView btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }
    //f8누르면 한줄 아래로
    //f9 함수 빠져나가기, 다음 브레이킹 포인트로 넘어가기

    private void initView(){

        mainTopbarView = (TopbarView) findViewById(R.id.title);
        mainTopbarView.setType(TopbarView.TOPBAR_TYPE.MAIN);
        mainTopbarView.setTopMenuClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                mainDrawerView.openDrawer(GravityCompat.START);
            }
        });

        mainDrawerView = (DrawerLayout) findViewById(R.id.main_drawer);
        mainSlideNaviView = (NavigationView) findViewById(R.id.nav_view);

        drawerToggle = new ActionBarDrawerToggle(this, mainDrawerView, 0, 0);
        mainDrawerView.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        btnJoin = (TextView) findViewById(R.id.btn_join);
        btnJoin.setOnClickListener(this);

        carousel = (CarouselViewPager) findViewById(R.id.carousel);
        ArrayList<MainDramaData> entities = buildData();
        CarouselAdapter carouselAdapter = new CarouselAdapter(this, carousel, getSupportFragmentManager(), entities);

        carousel.setAdapter(carouselAdapter);
        carousel.addOnPageChangeListener(carouselAdapter);
        carousel.setOffscreenPageLimit(entities.size());
        carousel.setClipToPadding(false);

        carousel.setScrollDurationFactor(1.5f);
        carousel.setPageWidth(0.55f);
        carousel.settPaddingBetweenItem(16);
        carousel.setAlpha(1.0f);

        menuMyPage = (TextView) findViewById(R.id.menu_mypage);
        menuMyPage.setOnClickListener(this);

        menuSbs = (ImageView) findViewById(R.id.menu_sbs);
        menuSbs.setOnClickListener(this);

        menuKbs = (ImageView) findViewById(R.id.menu_kbs);
        menuKbs.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {

        if (mainDrawerView.isDrawerOpen(GravityCompat.START)) {
            mainDrawerView.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private ArrayList<MainDramaData> buildData() {
        ArrayList<MainDramaData> entities = new ArrayList<>();

        entities.add(new MainDramaData(R.drawable.poster_my_golden, "황금빛내인생", getString(R.string.myGoldenLife)));
        entities.add(new MainDramaData(R.drawable.poster_love_returns, "미워도 사랑해", getString(R.string.loveReturns)));
        entities.add(new MainDramaData(R.drawable.poster_live_together, "같이 살래요", getString(R.string.liveTogether)));
        entities.add(new MainDramaData(R.drawable.poster_misty, "미스티", getString(R.string.misty)));
        entities.add(new MainDramaData(R.drawable.poster_mother, "마더", getString(R.string.mother)));
        entities.add(new MainDramaData(R.drawable.poster_return, "리턴", getString(R.string.rEturn)));
        entities.add(new MainDramaData(R.drawable.poster_to_you, "시를 잊은 그대에게", getString(R.string.toYou)));

        return entities;
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()){

            case R.id.menu_mypage:

                intent = new Intent(MainActivity.this, MypageMainActivity.class);
                intent.putExtra("type", ((TextView)v).getText().toString());
                startActivity(intent);

                break;


            case R.id.menu_sbs:

                intent = new Intent(MainActivity.this, DramaMainActivity.class);
                intent.putExtra("type", "sbs");
                startActivity(intent);

                break;

            case R.id.menu_kbs:
//                intent = new Intent(MainActivity.this, NewIntentCategorySample.class);
//                intent.putExtra("type", "kbs");
//                startActivity(intent);

                MainDramaAsyncTask mainDramaAsyncTask = new MainDramaAsyncTask(new MainDramaAsyncTask.MainDramaResultHandler() {
                    @Override
                    public void onSuccessAppAsyncTask(MainDramaResult result) {
                        Log.d("TAG", result.success + "\n" + result.dramaVOArrayList );
                    }

                    @Override
                    public void onFailAppAsysncask() {

                    }

                    @Override
                    public void onCancelAppAsyncTask() {

                    }
                });
                mainDramaAsyncTask.execute("/channel", "sbs");
                break;

            case R.id.btn_join:

                intent = new Intent(MainActivity.this, ProvisionActivity.class);
                intent.putExtra("type", ((TextView)v).getText().toString());
                startActivity(intent);
                break;
        }



        mainDrawerView.closeDrawer(Gravity.START);

    }

}
