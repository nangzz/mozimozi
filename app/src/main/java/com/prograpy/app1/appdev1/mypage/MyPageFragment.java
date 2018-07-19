package com.prograpy.app1.appdev1.mypage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prograpy.app1.appdev1.R;

import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.response.MyPageProductResult;
import com.prograpy.app1.appdev1.popup.info.CustomPopup;
import com.prograpy.app1.appdev1.task.MypageProductAsyncTask;
import com.prograpy.app1.appdev1.utils.PreferenceData;
import com.prograpy.app1.appdev1.vo.ProductVO;


public class MyPageFragment extends Fragment {

    private MyPageListAdapter myPageListAdapter;
    private RecyclerView recyclerView;

    private View.OnClickListener itemActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CustomPopup infoPopup = new CustomPopup(getContext());
            infoPopup.show();
        }
    };


    public static MyPageFragment createFragment(){

        Bundle bundle = new Bundle();

        MyPageFragment fragment = new MyPageFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.item_list_mypage);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        myPageListAdapter = new MyPageListAdapter(getContext(), itemActivityListener);
        recyclerView.setAdapter(myPageListAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MypageProductAsyncTask mypageProductAsyncTask = new MypageProductAsyncTask(new MypageProductAsyncTask.TaskResultHandler() {
            @Override
            public void onSuccessAppAsyncTask(MyPageProductResult result) {


                if (result.isSuccess()) {

                    if (result.getMypageProductList() != null && result.getMypageProductList().size() > 0) {
                        myPageListAdapter.setMyPageItemData(result.getMypageProductList());
                        myPageListAdapter.notifyDataSetChanged();


                        DbController.deleteAll(getContext());

                        for(ProductVO item : result.getMypageProductList()){
                            DbController.addProductId(getContext(), item.getP_id());
                        }
                    }

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

        mypageProductAsyncTask.execute(ApiValue.API_MYPAGE, PreferenceData.getKeyUserId());

    }

}