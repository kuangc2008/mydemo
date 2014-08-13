package com.example.next_year2014;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Created by kuangcheng on 2014/8/6.
 */
public class PackageUtils {


    public static final int install(Context context, String filePath) {
//        if(PackageUtils.isSystemApplication(context) || ShellUtils.checkRootPermission()) {
            if (installSilent(context, filePath) != INSTALL_SUCCEEDED) {
//        }
                return installNormal(context, filePath) ? INSTALL_SUCCEEDED : INSTALL_FAILED_INVALID_URI;
            }
        return 0;
    }

    public static boolean installNormal(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if(file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }

        i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    public static int installSilent(Context context, String filePath) {
        return installSilent(context, filePath, " -r " + getInstallLocationParams());
    }

    public static int installSilent(Context context, String filePath, String pmParams) {
        if(TextUtils.isEmpty(filePath)) {
            return INSTALL_FAILED_INVALID_URI;
        }
        File file = new File(filePath);
        if(file == null || file.length() <= 0 || !file.exists() || !file.isFile()) {
            return INSTALL_FAILED_INVALID_URI;
        }
        StringBuilder command = new StringBuilder().append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install ")
                .append(pmParams == null ? "" : pmParams)
                .append(" ")
                .append(filePath.replace(" ","\\ "));
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command.toString(), !isSystemApplication(context), true);
        if(commandResult.succssMsg != null && (commandResult.succssMsg.toLowerCase().contains("success"))) {
            return INSTALL_SUCCEEDED;
        }
        return INSTALL_FAILED_OTHER;
    }

    public static boolean isSystemApplication(Context context) {
        return isSystemApplication(context, context.getPackageName());
    }

    public static boolean isSystemApplication(Context context, String packgeName) {
        if(context == null) {
            return false;
        }
        PackageManager pm = context.getPackageManager();

        if(pm == null || TextUtils.isEmpty(packgeName)) {
            return false;
        }

        try {
            ApplicationInfo app = pm.getApplicationInfo(packgeName, 0);
            return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getInstallLocationParams() {
        int location = APP_INSTALL_INTERNAL; //getInstallLocation();
        switch (location) {
            case APP_INSTALL_INTERNAL:
                return "-f";
            case APP_INSTALL_EXTERNAL:
                return "-s";
        }
        return "";
    }


    public static final int APP_INSTALL_AUTO = 0;
    public static final int APP_INSTALL_INTERNAL = 1;
    public static final int APP_INSTALL_EXTERNAL = 2;
    public static int getInstallLocation() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm get-install-location", false, true);
        if(commandResult.result == 0 && commandResult.succssMsg != null && commandResult.succssMsg.length() > 0) {
            try {
                int location = Integer.parseInt(commandResult.succssMsg.substring(0, 1));
                switch (location) {
                    case APP_INSTALL_INTERNAL:
                        return APP_INSTALL_INTERNAL;
                    case APP_INSTALL_EXTERNAL:
                        return APP_INSTALL_EXTERNAL;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("kcc", "pm get-install-location error");
            }
        }
        return APP_INSTALL_AUTO;
    }

    public static final int INSTALL_SUCCEEDED = 1;
    public static final int INSTALL_FAILED_INVALID_URI = -3;
    public static final int INSTALL_FAILED_OTHER  = -1000000;
}
