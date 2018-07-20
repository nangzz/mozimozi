package com.prograpy.app1.appdev1.favorite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.mypage.MyPageListAdapter;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.MyPageProductResult;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;
import com.prograpy.app1.appdev1.popup.NetworkProgressDialog;
import com.prograpy.app1.appdev1.productInfo.ProductInfoActivity;
import com.prograpy.app1.appdev1.task.HeartAsyncTask;
import com.prograpy.app1.appdev1.task.MypageProductAsyncTask;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.vo.ProductVO;

public class FavoriteFragment extends Fragment{

    private MyPageListAdapter myPageListAdapter;
    private NetworkProgressDialog networkProgressDialog;
    private RecyclerView recyclerView;

    private TextView zzimCount;

    private View.OnClickListener itemUrlListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ProductVO vo = (ProductVO) v.getTag();

            Uri uri = Uri.parse(vo.getP_url());
            Intent it  = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
        }
    };

    private View.OnClickListener itemDelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            networkProgressDialog.show();

            ProductVO vo = (ProductVO) v.getTag();

            HeartAsyncTask heartAsyncTask = new HeartAsyncTask(getContext(), new HeartAsyncTask.TaskResultHandler() {
                @Override
                public void onSuccessAppAsyncTask(ServerSuccessCheckResult result) {

                    if(!result.isSuccess()){
                        networkProgressDialog.dismiss();
                        Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }else{
                        callMyProduct();
                    }

                }

                @Override
                public void onFailAppAsysncask() {
                    networkProgressDialog.dismiss();
                    Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelAppAsyncTask() {
                    networkProgressDialog.dismiss();
                    Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                }
            });

            heartAsyncTask.execute(ApiValue.API_HEART_REMOVE, PreferenceData.getKeyUserId(), String.valueOf(vo.getP_id()));
        }
    };

    public static FavoriteFragment createFragment(){

        Bundle bundle = new Bundle();

        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);


        networkProgressDialog = new NetworkProgressDialog(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.item_list_mypage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        zzimCount = (TextView) view.findViewById(R.id.zzim_count);

        myPageListAdapter = new MyPageListAdapter(getContext(), itemUrlListener, itemDelListener);
        recyclerView.setAdapter(myPageListAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        callMyProduct();
    }

    public void callMyProduct(){
        MypageProductAsyncTask mypageProductAsyncTask = new MypageProductAsyncTask(new MypageProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(MyPageProductResult result) {
                networkProgressDialog.dismiss();

                if (result.isSuccess()) {

                    if (result.getMypageProductList() != null && result.getMypageProductList().size() > 0) {
                        myPageListAdapter.setMyPageItemData(result.getMypageProductList());
                        myPageListAdapter.notifyDataSetChanged();

                        zzimCount.setText(String.valueOf(result.getMypageProductList().size()));

                        DbController.deleteAll(getContext());

                        for(ProductVO item : result.getMypageProductList()){
                            DbController.addProductId(getContext(), item.getP_id());
                        }
                    }

                }
            }

            @Override
            public void onFailAppAsysncask() {
                networkProgressDialog.dismiss();
                Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppAsyncTask() {
                networkProgressDialog.dismiss();
                Toast.makeText(getContext(), getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }
        });

        mypageProductAsyncTask.execute(ApiValue.API_MYPAGE, PreferenceData.getKeyUserId());

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
           callMyProduct();
        }
    }
}
