package com.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by kuangcheng on 2014/10/18.
 */
public class FilePathHelper {
    public static String getDownloadTitlePath() {
        File file = new File(GlobalUtils.mContext.getFilesDir(), "title");
        return file.toString();
    }

    public static String getDownloadUrlPath() {
        File file = new File(GlobalUtils.mContext.getFilesDir(), "url");
        return file.toString();
    }


    public static String getAudioPath(String fileName) {
        File filePath = null;
        if(Environment.isExternalStorageEmulated()) {
            filePath = Environment.getExternalStorageDirectory();
            filePath = new File(filePath.getPath() + File.separator + "kc");
            if(!filePath.exists()) {
                filePath.mkdirs();
            }
            filePath = new File(filePath, fileName);
            if(filePath.isDirectory()) {
                filePath.delete();
            }
            int i =0;
            if(!filePath.exists()) {
                try {
                    filePath.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } else {
            filePath = new File(GlobalUtils.mContext.getFilesDir(), "audio");
        }
        return filePath.toString();
    }
}
