package com.last201409;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kuangcheng on 2014/9/29.
 */
public class DeviceIdActivity extends Activity {
    private static final String DEFAULT_IMEI = "360_DEFAULT_IMEI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = new Button(this);
        button.setText("获取设备id");
        setContentView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imei = getImei(DeviceIdActivity.this);
                Log.w("kcc", "imei-->" + imei);

                String andoridid = getAndroidId(DeviceIdActivity.this);
                Log.w("kcc", "andoridid-->" +  andoridid);

                String serialNumber = getSerialNumber();
                Log.w("kcc", "serialNumber-->" +  serialNumber);

                String deviceId = md5(imei + andoridid + serialNumber);
                Log.w("kcc", "deviceId-->" +  deviceId);
            }
        });
    }

    private static String getImei(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if(tm != null) {
            try {
                String deviceId = tm.getDeviceId();
                if(!TextUtils.isEmpty(deviceId)) {
                    return deviceId;
                }
            } catch (Exception e) {}
        }

        return DEFAULT_IMEI;
    }

    private static String getAndroidId(Context context) {
        String androidId = Settings.System.getString(context.getContentResolver(), "android_id");
        return androidId;
    }

    private static String getSerialNumber() {
        String serialNumber = "";
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            if(clazz != null) {
                Method method_get = clazz.getMethod("get", String.class, String.class);
                if(method_get != null) {
                    serialNumber = (String) method_get.invoke(clazz, "ro.serialno", "");
                }
            }
        }  catch (Exception e) {
        }
        return serialNumber;
    }

    private static String md5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for(int offset = 0; offset < b.length; offset ++) {
                i = b[offset];
                Log.w("kcc", "md5--->ofset--" + offset + "  value-->" + i);
                if(i<0) {
                    i += 256;
                }
                if(i<16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
