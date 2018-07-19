package com.prograpy.app1.appdev1.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.drama.item.adapter.MainProductListAdapter;
import com.prograpy.app1.appdev1.lib.mainlist.CarouselAdapter;
import com.prograpy.app1.appdev1.lib.mainlist.CarouselViewPager;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.SearchResult;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.productInfo.ProductInfoActivity;
import com.prograpy.app1.appdev1.task.HeartAsyncTask;
import com.prograpy.app1.appdev1.task.MainTopItemAsyncTask;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

public class HomeFragment extends Fragment{


    private ArrayList<ProductVO> ProductList = new ArrayList<ProductVO>();
    private RecyclerView topItemList;
    private MainProductListAdapter topItemListAdapter;

    private CarouselViewPager carousel;
    private CarouselAdapter carouselAdapter;

    private ArrayList<DramaVO> dramaDataList = new ArrayList<DramaVO>();

    private NestedScrollView scrollView;


    private View.OnClickListener itemHeartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // 로그인에 성공하지 못한 사용자가 눌러버림
            if(!PreferenceData.getKeyLoginSuccess()){
                Toast.makeText(getContext(), getResources().getString(R.string.not_login_click_heart), Toast.LENGTH_SHORT).show();
                return;
            }

            ProductVO vo = (ProductVO) v.getTag();

            HeartAsyncTask heartAsyncTask = new HeartAsyncTask(getContext(), new HeartAsyncTask.TaskResultHandler() {
                @Override
                public void onSuccessAppAsyncTask(ServerSuccessCheckResult result) {

                    topItemListAdapter.notifyDataSetChanged();

                    if(!result.isSuccess()){
                        Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailAppAsysncask() {
                    Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelAppAsyncTask() {
                    Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }
            });

            heartAsyncTask.execute(DbController.isOverlapData(getContext(), vo.getP_id()) ? ApiValue.API_HEART_REMOVE : ApiValue.API_HEART_CHECK,
                    PreferenceData.getKeyUserId(), String.valueOf(vo.getP_id()));

        }
    };
    
    private View.OnClickListener itemActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;


            ProductVO vo = (ProductVO) v.getTag();

            intent = new Intent(getContext(), ProductInfoActivity.class);

            intent.putExtra("title", vo.getP_name());
//            intent.putExtra("dramaId", dramaId);

            startActivity(intent);
        }
    };



    public static HomeFragment createFragment(){

        Bundle bundle = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_lib_main, container, false);

        initView(view);

        return view;
    }


    private void initView(View view) {

        topItemList = (RecyclerView) view.findViewById(R.id.item_list);

        topItemListAdapter = new MainProductListAdapter(getContext(), itemActivityListener, itemHeartListener);

        //searchItemListAdapter.setOnItemClickListener(itemPopupListener);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topItemList.setLayoutManager(layoutManager);
        topItemList.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        topItemList.setLayoutManager(gridLayoutManager);

        topItemList.setAdapter(topItemListAdapter);

        scrollView = (NestedScrollView) view.findViewById(R.id.scroll);

        carousel = (CarouselViewPager) view.findViewById(R.id.carousel);
        carouselAdapter = new CarouselAdapter(getContext(), carousel, getFragmentManager());

        carouselAdapter.setDramaList(dramaDataList);

        carousel.setAdapter(carouselAdapter);
        carousel.addOnPageChangeListener(carouselAdapter);
        carousel.setOffscreenPageLimit(dramaDataList.size());
        carousel.setClipToPadding(false);

        carousel.setScrollDurationFactor(1.5f);
        carousel.setPageWidth(0.55f);
        carousel.settPaddingBetweenItem(16);
        carousel.setAlpha(1.0f);


        carousel.setCurrentItem(Integer.MAX_VALUE / 2);
        callTopItem();
    }



    public void callTopItem() {
        MainTopItemAsyncTask mainTopItemAsyncTask = new MainTopItemAsyncTask(new MainTopItemAsyncTask.MainTopItemResultHandler() {

            @Override
            public void onSuccessAppAsyncTask(SearchResult result) {

                if(result != null){

                    if(result.success && result.productVOArrayList != null && result.productVOArrayList.size() > 0){
                        topItemListAdapter.setProductData(result.productVOArrayList);
                        topItemListAdapter.notifyDataSetChanged();

                    }else{
                        Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailAppAsysncask() {

                Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {
                Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }
        });

        mainTopItemAsyncTask.execute(ApiValue.API_MainTopItem);
    }

}
