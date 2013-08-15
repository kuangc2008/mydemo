package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class AllActivityManager {
    public static final String ACTION = "com.example.demo.demoaction";
    
    private Context mContext = null;
    private PackageManager mPm = null;
//    private TreeMap<String, List<AppInfo>> allActivityMap = null;
    List<AppInfo> appInfos = new ArrayList<AppInfo>();

    private AllActivityManager(Context context) {
        mContext = context;
        mPm = context.getPackageManager();

//        allActivityMap = new TreeMap<String, List<AppInfo>>();
        List<ResolveInfo> activitys = mPm.queryIntentActivities(new Intent(ACTION), PackageManager.GET_ACTIVITIES);
        for(ResolveInfo activity : activitys) {
            AppInfo info = new AppInfo(activity, mPm);
//            String key = info.getTitle("");
//            
//            List<AppInfo> lists = allActivityMap.get(key);
//            if(lists == null) {
//                lists = new ArrayList<AppInfo>();
//            }
            appInfos.add(info);
        }
    }
    private static AllActivityManager instance = null;
    private static final Object obj = new Object();
    
    public static AllActivityManager getInstance(Context context) {
        if(instance == null) {
            synchronized (obj) {
                if(instance == null) {
                    instance = new AllActivityManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * get activity lists according to prefix string.
     */
    public List<AppInfo> getActivityList(String prefix) {
        List<AppInfo> resultinfos = new ArrayList<AppInfo>();
        List<String> haveLabels = new ArrayList<String>();
        for(AppInfo info : appInfos) {
            if(info.isPrefix(prefix) && !haveLabels.contains(info.getTitle(prefix))) {
                resultinfos.add(info);
                haveLabels.add(info.getTitle(prefix));
            }
        }
        return resultinfos;
    }

}
