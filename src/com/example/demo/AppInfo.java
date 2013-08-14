package com.example.demo;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

public class AppInfo {

    private ActivityInfo info;

    public String label;

    public ActivityInfo getActivityInfo() {
        return info;
    }

    public void setActivityInfo(ActivityInfo info, PackageManager pm) {
        this.info = info;
        label = (String) info.loadLabel(pm);
    }

    public String getListTitle(String prefix) {
        prefix = delteLastInternal(prefix);
        String title = null;
        if(isPrefixOfLabel(prefix)) {
            int end = label.indexOf('/', prefix.length()+1);
            title = (String) label.subSequence(prefix.length()+1, end);
        }
        return title;
    }

    public boolean isPrefixOfLabel(String prefix) {
        prefix = delteLastInternal(prefix);
        return label.startsWith(prefix);
    }
    
    public boolean isLastCharacter(String prefix) {
        prefix = delteLastInternal(prefix);
        return label.equals(prefix);
    }
    
    public static final String delteLastInternal(String prefix) {
        if(prefix.endsWith("/")) {
            prefix = (String) prefix.subSequence(0, prefix.length() - 1);
        }
        return prefix;
    }
}
