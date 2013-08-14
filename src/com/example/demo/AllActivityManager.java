package com.example.demo;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class AllActivityManager {
    private static final String ACTION = "com.example.demo.demoaction";
    
    private Context mContext = null;
    private PackageManager mPm = null;
    private AllActivityManager(Context context) {
        mContext = context;
        mPm = context.getPackageManager();
        
        List<ResolveInfo> activitys = mPm.queryIntentActivities(new Intent(ACTION), PackageManager.GET_ACTIVITIES);
    }
    
    private static AllActivityManager instance = null;
    
    public static AllActivityManager getInstance(Context context) {
        if(instance == null) {
            synchronized (instance) {
                if(instance == null) {
                    instance = new AllActivityManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * get activity lists according to prefix string.
     * @param prefixStr if the str is "", get 
     * @return
     */
    public List<AppInfo> getActivityList(String prefixStr) {
        return null;
    }

    
}
