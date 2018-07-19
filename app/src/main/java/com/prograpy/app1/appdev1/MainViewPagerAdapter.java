package com.prograpy.app1.appdev1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.prograpy.app1.appdev1.Search.SearchFragment;
import com.prograpy.app1.appdev1.favorite.FavoriteFragment;
import com.prograpy.app1.appdev1.home.HomeFragment;
import com.prograpy.app1.appdev1.mypage.MyPageFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {


    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return HomeFragment.createFragment();

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
}
