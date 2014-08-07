package com.example.next_year2014;

import java.io.IOException;

/**
 * Created by kuangcheng on 2014/8/7.
 */
public class PackageInstaller {
    public void unInstallApp(String packageName){
        try {
            Runtime.getRuntime().exec("pm uninstall "+packageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void installApp(String appPath){
        try {
            Runtime.getRuntime().exec("pm install "+appPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reInstallApp(String appPath){
        try {
            Runtime.getRuntime().exec("pm install -r "+appPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
