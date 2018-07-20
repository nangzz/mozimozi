package com.prograpy.app1.appdev1.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.drama.item.DramaItemListActivity;
import com.prograpy.app1.appdev1.drama.item.adapter.MainProductListAdapter;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.DramaListResult;
import com.prograpy.app1.appdev1.network.response.SearchResult;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.productInfo.ProductInfoActivity;
import com.prograpy.app1.appdev1.task.DramaListAsyncTask;
import com.prograpy.app1.appdev1.task.HeartAsyncTask;
import com.prograpy.app1.appdev1.task.MainTopItemAsyncTask;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private ArrayList<ProductVO> ProductList = new ArrayList<ProductVO>();
    private RecyclerView topItemList;
    private MainProductListAdapter topItemListAdapter;

    private RecyclerView dramaPosterList;
    private HomeDramaAdapter dramaPosterAdapter;

    private ArrayList<DramaVO> dramaDataList = new ArrayList<DramaVO>();
    private ArrayList<DramaVO> dramaRandomDataList = new ArrayList<DramaVO>();

    private NestedScrollView scrollView;

    private ImageView iconAll, iconKbs, iconSbs, iconMbc, iconTvn, iconOcn, iconJtbc;
    private TextView posterTitle;
    private DramaListAsyncTask dramaListAsyncTask;


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
            intent.putExtra("img", vo.getP_img());
            intent.putExtra("act", vo.getP_act());
            intent.putExtra("link", vo.getP_url());
            intent.putExtra("price", vo.getP_price());
            intent.putExtra("itemId", vo.getP_id());

            startActivity(intent);
        }
    };

    private View.OnClickListener itemPosterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DramaVO dramaVO = (DramaVO) v.getTag();

            Intent i = new Intent(getContext(), DramaItemListActivity.class);
            i.putExtra("title", dramaVO.getD_name());
            i.putExtra("dramaId", dramaVO.getD_id());
            i.putExtra("actors", "전체," + dramaVO.getD_act());

            startActivity(i);
        }
    };


    private View.OnClickListener channelRandomListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            iconAll.setSelected(true);
            iconKbs.setSelected(false);
            iconSbs.setSelected(false);
            iconMbc.setSelected(false);
            iconTvn.setSelected(false);
            iconOcn.setSelected(false);
            iconJtbc.setSelected(false);

            dramaPosterAdapter.setDramaData(dramaRandomDataList);
            dramaPosterAdapter.notifyDataSetChanged();

            dramaPosterList.smoothScrollToPosition(0);

            posterTitle.setText("전체 추천 드라마");
        }
    };



    private View.OnClickListener channelIconListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String channel = "";

            iconAll.setSelected(false);

            switch (v.getId()){
                case R.id.drama_kbs:

                    iconKbs.setSelected(true);
                    iconSbs.setSelected(false);
                    iconMbc.setSelected(false);
                    iconTvn.setSelected(false);
                    iconOcn.setSelected(false);
                    iconJtbc.setSelected(false);

                    channel = "kbs";
                    break;

                case R.id.drama_sbs:

                    iconKbs.setSelected(false);
                    iconSbs.setSelected(true);
                    iconMbc.setSelected(false);
                    iconTvn.setSelected(false);
                    iconOcn.setSelected(false);
                    iconJtbc.setSelected(false);

                    channel = "sbs";
                    break;

                case R.id.drama_mbc:

                    iconKbs.setSelected(false);
                    iconSbs.setSelected(false);
                    iconMbc.setSelected(true);
                    iconTvn.setSelected(false);
                    iconOcn.setSelected(false);
                    iconJtbc.setSelected(false);

                    channel = "mbc";
                    break;

                case R.id.drama_tvn:

                    iconKbs.setSelected(false);
                    iconSbs.setSelected(false);
                    iconMbc.setSelected(false);
                    iconTvn.setSelected(true);
                    iconOcn.setSelected(false);
                    iconJtbc.setSelected(false);

                    channel = "tvn";
                    break;

                case R.id.drama_ocn:

                    iconKbs.setSelected(false);
                    iconSbs.setSelected(false);
                    iconMbc.setSelected(false);
                    iconTvn.setSelected(false);
                    iconOcn.setSelected(true);
                    iconJtbc.setSelected(false);

                    channel = "ocn";
                    break;

                case R.id.drama_jtbc:

                    iconKbs.setSelected(false);
                    iconSbs.setSelected(false);
                    iconMbc.setSelected(false);
                    iconTvn.setSelected(false);
                    iconOcn.setSelected(false);
                    iconJtbc.setSelected(true);

                    channel = "jtbc";
                    break;
            }

            posterTitle.setText(channel.toUpperCase() + " 추천 드라마");
            callChannelDrama(channel);
        }
    };


    public static HomeFragment createFragment(ArrayList<DramaVO> dramaRandomDataList){


        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dramaRandList", dramaRandomDataList);

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_lib_main, container, false);

        if(getArguments() != null)
            dramaRandomDataList = getArguments().getParcelableArrayList("dramaRandList");

        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        topItemListAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {

        topItemList = (RecyclerView) view.findViewById(R.id.item_list);
        dramaPosterList = (RecyclerView) view.findViewById(R.id.main_drama);

        topItemListAdapter = new MainProductListAdapter(getContext(), itemActivityListener, itemHeartListener);
        dramaPosterAdapter = new HomeDramaAdapter(getContext(), itemPosterListener);

        //searchItemListAdapter.setOnItemClickListener(itemPopupListener);

        iconAll = (ImageView) view.findViewById(R.id.drama_all);
        iconKbs = (ImageView) view.findViewById(R.id.drama_kbs);
        iconSbs = (ImageView) view.findViewById(R.id.drama_sbs);
        iconMbc = (ImageView) view.findViewById(R.id.drama_mbc);
        iconTvn = (ImageView) view.findViewById(R.id.drama_tvn);
        iconOcn = (ImageView) view.findViewById(R.id.drama_ocn);
        iconJtbc = (ImageView) view.findViewById(R.id.drama_jtbc);

        iconAll.setOnClickListener(channelRandomListener);
        iconKbs.setOnClickListener(channelIconListener);
        iconSbs.setOnClickListener(channelIconListener);
        iconMbc.setOnClickListener(channelIconListener);
        iconTvn.setOnClickListener(channelIconListener);
        iconOcn.setOnClickListener(channelIconListener);
        iconJtbc.setOnClickListener(channelIconListener);

        iconAll.setSelected(true);


        posterTitle = (TextView) view.findViewById(R.id.poster_title);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        topItemList.setLayoutManager(gridLayoutManager);
        topItemList.setAdapter(topItemListAdapter);

        scrollView = (NestedScrollView) view.findViewById(R.id.scroll);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        dramaPosterList.setLayoutManager(layoutManager);
        dramaPosterList.setAdapter(dramaPosterAdapter);


        dramaPosterAdapter.setDramaData(dramaRandomDataList);
        dramaPosterAdapter.notifyDataSetChanged();

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




    private void callChannelDrama(final String channel){

        if(dramaListAsyncTask != null && !dramaListAsyncTask.isCancelled())
            dramaListAsyncTask.cancel(true);

        dramaListAsyncTask = new DramaListAsyncTask(new DramaListAsyncTask.DramaListResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(DramaListResult result) {

                if(result != null){
                    if(result.isSuccess()){

                        if(result.getDramaVOArrayList() != null && result.getDramaVOArrayList().size() > 0){

                            dramaDataList = result.getDramaVOArrayList();

                            dramaPosterAdapter.setDramaData(dramaDataList);
                            dramaPosterAdapter.notifyDataSetChanged();

                            dramaPosterList.smoothScrollToPosition(0);

                        }else{
                            Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }


                dramaListAsyncTask = null;

            }

            @Override
            public void onFailAppAsysncask() {
                dramaListAsyncTask = null;
                Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {

                dramaListAsyncTask = null;
            }
        });

        dramaListAsyncTask.execute(ApiValue.API_DRAMA, channel);
    }


}
