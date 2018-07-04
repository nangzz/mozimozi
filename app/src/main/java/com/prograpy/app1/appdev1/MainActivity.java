package com.prograpy.app1.appdev1;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.drama.item.DramaItemListActivity;
import com.prograpy.app1.appdev1.drama.item.adapter.DramaItemListAdapter;
import com.prograpy.app1.appdev1.drama.item.adapter.MainProductListAdapter;
import com.prograpy.app1.appdev1.drama.list.DramaMainActivity;
import com.prograpy.app1.appdev1.join.LoginActivity;
import com.prograpy.app1.appdev1.join.ProvisionActivity;
import com.prograpy.app1.appdev1.lib.mainlist.CarouselAdapter;
import com.prograpy.app1.appdev1.lib.mainlist.CarouselViewPager;
import com.prograpy.app1.appdev1.mypage.MypageMainActivity;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.DramaListResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.popup.info.CustomPopup;
import com.prograpy.app1.appdev1.task.DramaListAsyncTask;
import com.prograpy.app1.appdev1.task.MainDListAsyncTask;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.DramaVO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TopbarView mainTopbarView;
    private DrawerLayout mainDrawerView;
    private NavigationView mainSlideNaviView;

    private ActionBarDrawerToggle drawerToggle;

    private CarouselViewPager carousel;
    private CarouselAdapter carouselAdapter;

    private ArrayList<DramaVO> dramaDataList = new ArrayList<DramaVO>();

    private NetworkProgressDialog networkProgressDialog;

    private TextView menuMyPage;
    private ImageView menuSbs;
    private ImageView menuKbs;
    private ImageView menuMbc;
    private ImageView menuTvn;
    private ImageView menuJtbc;
    private ImageView menuOcn;

    private ImageView btnJoin;

    private RecyclerView dramaItemListView;
    private MainProductListAdapter dramaItemListAdapter;

    private View.OnClickListener itemPopupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CustomPopup infoPopup = new CustomPopup(MainActivity.this);
            infoPopup.show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }
    //f8누르면 한줄 아래로
    //f9 함수 빠져나가기, 다음 브레이킹 포인트로 넘어가기

    private void initView() {

        mainTopbarView = (TopbarView) findViewById(R.id.title);
        mainTopbarView.setType(TopbarView.TOPBAR_TYPE.MAIN);
        mainTopbarView.setTopMenuClick(new TopbarView.ItemClick() {
            @Override
            public void onItemClick() {
                mainDrawerView.openDrawer(GravityCompat.START);
            }
        });

        networkProgressDialog = new NetworkProgressDialog(this);

        mainDrawerView = (DrawerLayout) findViewById(R.id.main_drawer);
        mainSlideNaviView = (NavigationView) findViewById(R.id.nav_view);

        drawerToggle = new ActionBarDrawerToggle(this, mainDrawerView, 0, 0);
        mainDrawerView.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        btnJoin = (ImageView) findViewById(R.id.img_login);
        btnJoin.setOnClickListener(this);

        menuMyPage = (TextView) findViewById(R.id.menu_mypage);
        menuMyPage.setOnClickListener(this);

        menuSbs = (ImageView) findViewById(R.id.menu_sbs);
        menuSbs.setOnClickListener(this);

        menuKbs = (ImageView) findViewById(R.id.menu_kbs);
        menuKbs.setOnClickListener(this);

        menuMbc = (ImageView) findViewById(R.id.menu_mbc);
        menuMbc.setOnClickListener(this);

        menuTvn = (ImageView) findViewById(R.id.menu_tvn);
        menuTvn.setOnClickListener(this);

        menuJtbc = (ImageView) findViewById(R.id.menu_jtbc);
        menuJtbc.setOnClickListener(this);

        menuOcn = (ImageView) findViewById(R.id.menu_ocn);
        menuOcn.setOnClickListener(this);

        carousel = (CarouselViewPager) findViewById(R.id.carousel);
        carouselAdapter = new CarouselAdapter(this, carousel, getSupportFragmentManager());

        carousel.setAdapter(carouselAdapter);
        carousel.addOnPageChangeListener(carouselAdapter);
        carousel.setOffscreenPageLimit(dramaDataList.size());
        carousel.setClipToPadding(false);

        carousel.setScrollDurationFactor(1.5f);
        carousel.setPageWidth(0.55f);
        carousel.settPaddingBetweenItem(16);
        carousel.setAlpha(1.0f);

        dramaItemListView = (RecyclerView) findViewById(R.id.item_list);
        dramaItemListView.setNestedScrollingEnabled(false);
        dramaItemListAdapter = new MainProductListAdapter();
        dramaItemListAdapter.setOnItemClickListener(itemPopupListener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        dramaItemListView.setLayoutManager(gridLayoutManager);

        dramaItemListView.setAdapter(dramaItemListAdapter);

        callMainDramaTask();
    }


    @Override
    public void onBackPressed() {

        if (mainDrawerView.isDrawerOpen(GravityCompat.START)) {
            mainDrawerView.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void callMainDramaTask() {

        MainDListAsyncTask MainDListAsyncTask = new MainDListAsyncTask(new MainDListAsyncTask.MainDListResultHandler() {

            @Override
            public void onSuccessAppAsyncTask(DramaListResult result) {

                networkProgressDialog.dismiss();

                if(result != null){

                    if(result.success && result.dramaVOArrayList != null && result.dramaVOArrayList.size() > 0){

                        carouselAdapter.setDramaList(result.dramaVOArrayList);
                        carouselAdapter.notifyDataSetChanged();

                        carousel.setOffscreenPageLimit(result.dramaVOArrayList.size());

                    }else{
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailAppAsysncask() {

                networkProgressDialog.dismiss();

                Toast.makeText(MainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                networkProgressDialog.dismiss();

                Toast.makeText(MainActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }
        });

        networkProgressDialog.show();

        MainDListAsyncTask.execute(ApiValue.API_RANDOM_DRAMA);
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        mainDrawerView.closeDrawer(Gravity.START);

        switch (v.getId()) {

            case R.id.menu_mypage:

                intent = new Intent(MainActivity.this, MypageMainActivity.class);
                intent.putExtra("type", ((TextView) v).getText().toString());
                startActivity(intent);

                break;


            case R.id.menu_sbs:

                intent = new Intent(MainActivity.this, DramaMainActivity.class);
                intent.putExtra("type", "sbs");
                startActivity(intent);

                break;

            case R.id.menu_kbs:
                intent = new Intent(MainActivity.this, DramaMainActivity.class);
                intent.putExtra("type", "kbs");
                startActivity(intent);

                break;

            case R.id.menu_mbc:
                intent = new Intent(MainActivity.this, DramaMainActivity.class);
                intent.putExtra("type", "mbc");
                startActivity(intent);

                break;

            case R.id.menu_tvn:
                intent = new Intent(MainActivity.this, DramaMainActivity.class);
                intent.putExtra("type", "tvn");
                startActivity(intent);

                break;

            case R.id.menu_jtbc:
                intent = new Intent(MainActivity.this, DramaMainActivity.class);
                intent.putExtra("type", "jtbc");
                startActivity(intent);

                break;

            case R.id.menu_ocn:
                intent = new Intent(MainActivity.this, DramaMainActivity.class);
                intent.putExtra("type", "ocn");
                startActivity(intent);

                break;

            case R.id.img_login:

                intent = new Intent(MainActivity.this, LoginActivity.class);
//                intent = new Intent(MainActivity.this, ProvisionActivity.class);
                startActivity(intent);
                break;
        }
    }

}
