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

    public void saveRefreshTime(int times) {
        sp.edit().putInt("refresh", times).commit();
    }

    public int getRefreshTime() {
        return sp.getInt("refresh", 0);
    }


    public void saveYinXinagVersion(int version) {
        sp.edit().putInt("yinxiang_version", version).commit();
    }

    public int getYinXiangVersion() {
        return sp.getInt("yinxiang_version", 0);
    }


    public void saveYinXinagVersionSuccess(boolean success) {
        sp.edit().putBoolean("yinxiang_update_success", success).commit();
    }

    public boolean getYinXinagVersionSuccess() {
        return sp.getBoolean("yinxiang_update_success", false);
    }
}
