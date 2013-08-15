package com.example.demo;

import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.test.AndroidTestCase;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import junit.framework.TestCase;

public class Test extends AndroidTestCase {

    public void test1() {
        AppInfo info = new AppInfo(null,null);
        info.label = "a/b/c";
        
        String title = info.getTitle("a/");
        System.out.println(title);
        
        title = info.getTitle("a");
        System.out.println(title);
        
        title = info.getTitle("a/b/");
        System.out.println(title);
        
        
        title = info.getTitle("a/b");
        System.out.println(title);
        
        title = info.getTitle("a/b/c");
        System.out.println(title);
    }
    
    public void test2() {
        PackageManager mPm = getContext().getPackageManager();
        List<ResolveInfo> activitys = mPm.queryIntentActivities(new Intent(AllActivityManager.ACTION), PackageManager.GET_ACTIVITIES);
        Log.v("kc", activitys.toString());
    }
    
    public void test3() {
        AllActivityManager instance = AllActivityManager.getInstance(getContext());
        List<AppInfo> activityList = instance.getActivityList("");
        Log.v("kc", activityList.toString());
        
        activityList = instance.getActivityList("view");
        Log.v("kc", activityList.toString());
        
        activityList = instance.getActivityList("view/view4");
        Log.v("kc", activityList.toString());
    }
}
