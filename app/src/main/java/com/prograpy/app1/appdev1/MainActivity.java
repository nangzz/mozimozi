package com.prograpy.app1.appdev1;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.prograpy.app1.appdev1.view.TopbarView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TopbarView mainTopbarView;
    private DrawerLayout mainDrawerView;
    private NavigationView mainSlideNaviView;

    private ActionBarDrawerToggle drawerToggle;


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
}
