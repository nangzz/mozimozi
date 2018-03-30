package com.prograpy.app1.appdev1;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;

import com.prograpy.app1.appdev1.lib.CarouselAdapter;
import com.prograpy.app1.appdev1.lib.CarouselViewPager;
import com.prograpy.app1.appdev1.lib.Entity;
import com.prograpy.app1.appdev1.view.TopbarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TopbarView mainTopbarView;
    private DrawerLayout mainDrawerView;
    private NavigationView mainSlideNaviView;

    private ActionBarDrawerToggle drawerToggle;

    private CarouselViewPager carousel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }


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

        mainSlideNaviView.setNavigationItemSelectedListener(this);

        carousel = (CarouselViewPager) findViewById(R.id.carousel);
        ArrayList<Entity> entities = buildData();
        CarouselAdapter carouselAdapter = new CarouselAdapter(this, carousel, getSupportFragmentManager(), entities);

        carousel.setAdapter(carouselAdapter);
        carousel.addOnPageChangeListener(carouselAdapter);
        carousel.setOffscreenPageLimit(entities.size());
        carousel.setClipToPadding(false);

        carousel.setScrollDurationFactor(1.5f);
        carousel.setPageWidth(0.55f);
        carousel.settPaddingBetweenItem(16);
        carousel.setAlpha(1.0f);

    }


    @Override
    public void onBackPressed() {

        if (mainDrawerView.isDrawerOpen(GravityCompat.START)) {
            mainDrawerView.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



        mainDrawerView.closeDrawer(GravityCompat.START);

        return true;
    }


    private ArrayList<Entity> buildData() {
        ArrayList<Entity> entities = new ArrayList<>();

        entities.add(new Entity(R.drawable.poster_my_golden, "황금빛내인생", getString(R.string.myGoldenLife)));
        entities.add(new Entity(R.drawable.poster_love_returns, "미워도 사랑해", getString(R.string.loveReturns)));
        entities.add(new Entity(R.drawable.poster_live_together, "같이 살래요", getString(R.string.liveTogether)));
        entities.add(new Entity(R.drawable.poster_misty, "미스티", getString(R.string.misty)));
        entities.add(new Entity(R.drawable.poster_mother, "마더", getString(R.string.mother)));
        entities.add(new Entity(R.drawable.poster_return, "리턴", getString(R.string.rEturn)));
        entities.add(new Entity(R.drawable.poster_to_you, "시를 잊은 그대에게", getString(R.string.toYou)));

        return entities;
    }
}
