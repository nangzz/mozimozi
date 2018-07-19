package com.prograpy.app1.appdev1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.prograpy.app1.appdev1.Search.SearchFragment;
import com.prograpy.app1.appdev1.favorite.FavoriteFragment;
import com.prograpy.app1.appdev1.home.HomeFragment;
import com.prograpy.app1.appdev1.mypage.MyPageFragment;
import com.prograpy.app1.appdev1.vo.DramaVO;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<DramaVO> dramaRandomDataList = new ArrayList<DramaVO>();

    public MainViewPagerAdapter(FragmentManager fm, ArrayList<DramaVO> dramaRandomDataList) {
        super(fm);
        this.dramaRandomDataList = dramaRandomDataList;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return HomeFragment.createFragment(dramaRandomDataList);

            case 1:
                return FavoriteFragment.createFragment();

            case 2:
                return SearchFragment.createFragment();

            case 3:
                return MyPageFragment.createFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }



}
