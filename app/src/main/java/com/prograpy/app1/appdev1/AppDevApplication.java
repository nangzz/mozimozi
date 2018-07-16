package com.prograpy.app1.appdev1;

import android.app.Application;

import com.prograpy.app1.appdev1.utils.PreferenceData;

public class AppDevApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceData.init(this);
    }
}
