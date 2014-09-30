package com.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.Preference;

public class PreferenceUtils {
    private static final PreferenceUtils preUtls = new PreferenceUtils();
    private SharedPreferences sp = null;

    private PreferenceUtils() {
        sp = GlobalUtils.mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public static final PreferenceUtils getInstance() {
        return preUtls;
    }


    public void saveYinXiangTime(long time) {
        sp.edit().putLong("time", time).commit();
    }

    public long getYinXiangTime() {
        return sp.getLong("time", 0);
    }



}
