package com.prograpy.app1.appdev1;

import android.app.Application;

import com.prograpy.app1.appdev1.utils.PerferenceData;

public class AppDevApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PerferenceData.init(this);
    }
}
