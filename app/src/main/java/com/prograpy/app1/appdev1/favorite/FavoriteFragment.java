package com.prograpy.app1.appdev1.favorite;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FavoriteFragment extends Fragment{


    public static FavoriteFragment createFragment(){

        Bundle bundle = new Bundle();

        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

}
