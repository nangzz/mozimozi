package com.prograpy.app1.appdev1.mypage;


import android.os.Bundle;
import android.support.v4.app.Fragment;


public class MyPageFragment extends Fragment {

    public static MyPageFragment createFragment(){

        Bundle bundle = new Bundle();

        MyPageFragment fragment = new MyPageFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

}
