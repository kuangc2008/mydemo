package com.example.demo;

import android.app.Application;

import com.utils.GlobalUtils;

/**
 * Created by kuangcheng on 2014/9/30.
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalUtils.mContext = this;
    }
}
