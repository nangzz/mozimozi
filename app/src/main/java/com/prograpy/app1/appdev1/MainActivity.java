package com.prograpy.app1.appdev1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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
import com.prograpy.app1.appdev1.mypage.MyPageFragment;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.SearchResult;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.productInfo.ProductInfoActivity;
import com.prograpy.app1.appdev1.task.HeartAsyncTask;
import com.prograpy.app1.appdev1.task.MainTopItemAsyncTask;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.view.TopbarView;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

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
        mainViewPagerAdapter = new MainViewPagerAdapter(this.getSupportFragmentManager());

        mainPager = (ViewPager) findViewById(R.id.main_viewpager);
        mainPager.setOffscreenPageLimit(3);
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
