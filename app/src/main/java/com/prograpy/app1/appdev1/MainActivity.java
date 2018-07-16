package com.prograpy.app1.appdev1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.drama.item.adapter.MainProductListAdapter;
import com.prograpy.app1.appdev1.drama.list.DramaMainActivity;
import com.prograpy.app1.appdev1.join.LoginActivity;
import com.prograpy.app1.appdev1.lib.mainlist.CarouselAdapter;
import com.prograpy.app1.appdev1.lib.mainlist.CarouselViewPager;
import com.prograpy.app1.appdev1.mypage.MypageMainActivity;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.DramaListResult;
import com.prograpy.app1.appdev1.network.response.SearchResult;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.productInfo.ProductInfoActivity;
import com.prograpy.app1.appdev1.task.HeartAsyncTask;
import com.prograpy.app1.appdev1.task.MainDListAsyncTask;
import com.prograpy.app1.appdev1.task.MainTopItemAsyncTask;
import com.prograpy.app1.appdev1.task.UserLoginAsyncTask;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<ProductVO> ProductList = new ArrayList<ProductVO>();
    private RecyclerView topItemList;
    private MainProductListAdapter topItemListAdapter;
    TextView searchText;
    private TopbarView mainTopbarView;
    private DrawerLayout mainDrawerView;
    private NavigationView mainSlideNaviView;

    private ActionBarDrawerToggle drawerToggle;

    private CarouselViewPager carousel;
    private CarouselAdapter carouselAdapter;

    private ArrayList<DramaVO> dramaDataList = new ArrayList<DramaVO>();
    private ArrayList<ProductVO> ProductDataList = new ArrayList<ProductVO>();
    private NetworkProgressDialog networkProgressDialog;
    private NestedScrollView scrollView;

    private TextView menuMyPage;
    private ImageView menuSbs;
    private ImageView menuKbs;
    private ImageView menuMbc;
    private ImageView menuTvn;
    private ImageView menuJtbc;
    private ImageView menuOcn;

    private ImageView btnJoin;

    private View.OnClickListener itemActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;


            ProductVO vo = (ProductVO) v.getTag();

            intent = new Intent(MainActivity.this, ProductInfoActivity.class);

            intent.putExtra("title", vo.getP_name());
//            intent.putExtra("dramaId", dramaId);

            startActivity(intent);
        }
    };

    private View.OnClickListener itemHeartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // 로그인에 성공하지 못한 사용자가 눌러버림
            if(!PreferenceData.getKeyLoginSuccess()){
                Toast.makeText(MainActivity.this, getResources().getString(R.string.not_login_click_heart), Toast.LENGTH_LONG).show();
                return;
            }

            ProductVO vo = (ProductVO) v.getTag();

            HeartAsyncTask heartAsyncTask = new HeartAsyncTask(MainActivity.this, new HeartAsyncTask.TaskResultHandler() {
                @Override
                public void onSuccessAppAsyncTask(ServerSuccessCheckResult result) {
                    networkProgressDialog.dismiss();

                    topItemListAdapter.notifyDataSetChanged();

                    if(!result.isSuccess()){
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

            heartAsyncTask.execute(DbController.isOverlapData(MainActivity.this, vo.getP_id()) ? ApiValue.API_HEART_REMOVE : ApiValue.API_HEART_CHECK,
                    PreferenceData.getKeyUserId(), String.valueOf(vo.getP_id()));

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(getIntent() != null){
            dramaDataList = getIntent().getParcelableArrayListExtra("dramaList");
        }

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

        topItemList = (RecyclerView) findViewById(R.id.item_list);

        topItemListAdapter = new MainProductListAdapter(getApplicationContext(), itemActivityListener, itemHeartListener);

        //searchItemListAdapter.setOnItemClickListener(itemPopupListener);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topItemList.setLayoutManager(layoutManager);
        topItemList.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        topItemList.setLayoutManager(gridLayoutManager);

        topItemList.setAdapter(topItemListAdapter);

        scrollView = (NestedScrollView) findViewById(R.id.scroll);

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

        carouselAdapter.setDramaList(dramaDataList);

        carousel.setAdapter(carouselAdapter);
        carousel.addOnPageChangeListener(carouselAdapter);
        carousel.setOffscreenPageLimit(dramaDataList.size());
        carousel.setClipToPadding(false);

        carousel.setScrollDurationFactor(1.5f);
        carousel.setPageWidth(0.55f);
        carousel.settPaddingBetweenItem(16);
        carousel.setAlpha(1.0f);


        if(PreferenceData.getKeyLoginSuccess()){
            btnJoin.setVisibility(View.INVISIBLE);
        }

        carousel.setCurrentItem(Integer.MAX_VALUE / 2);
        callTopItem();
    }


    @Override
    protected void onResume() {
        super.onResume();
        carouselAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {

        if (mainDrawerView.isDrawerOpen(GravityCompat.START)) {
            mainDrawerView.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void callTopItem() {
        MainTopItemAsyncTask mainTopItemAsyncTask = new MainTopItemAsyncTask(new MainTopItemAsyncTask.MainTopItemResultHandler() {

            @Override
            public void onSuccessAppAsyncTask(SearchResult result) {

                networkProgressDialog.dismiss();

                if(result != null){

                    if(result.success && result.productVOArrayList != null && result.productVOArrayList.size() > 0){
                        topItemListAdapter.setProductData(result.productVOArrayList);
                        topItemListAdapter.notifyDataSetChanged();

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

        mainTopItemAsyncTask.execute(ApiValue.API_MainTopItem);
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



    private void login(String userId, String userPw) {

        UserLoginAsyncTask task = new UserLoginAsyncTask(new UserLoginAsyncTask.UserLoginResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(ServerSuccessCheckResult result) {

                if (result.isSuccess()) {
                    PreferenceData.setKeyLoginSuccess(true);

                    btnJoin.setVisibility(View.INVISIBLE);
                } else {
                    PreferenceData.setKeyLoginSuccess(false);
                    PreferenceData.setKeyAutoLogin(false);
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.failed_auto_login), Toast.LENGTH_SHORT).show();

                    btnJoin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailAppAsysncask() {
                PreferenceData.setKeyLoginSuccess(false);
                PreferenceData.setKeyAutoLogin(false);

                btnJoin.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this, getResources().getString(R.string.failed_auto_login), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {
                PreferenceData.setKeyLoginSuccess(false);
                PreferenceData.setKeyAutoLogin(false);

                btnJoin.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this, getResources().getString(R.string.failed_auto_login), Toast.LENGTH_SHORT).show();
            }
        });


        task.execute(ApiValue.API_USER_LOGIN, userId, userPw);
    }
}
