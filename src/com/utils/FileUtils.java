package com.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.demo.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kuangcheng on 2014/9/30.
 */
public class FileUtils {

    public static final String getStringFromRawFile(Context context, InputStream is) {
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024 *8];
        try {
            int len;
            while( (len = bis.read(buffer, 0 , 1024*8)) > 0) {
                baos.write(buffer, 0, len);
            }
            return baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  "";
    }


    public static boolean byteArrayToFile(byte[] bytes, String filePath) {
        if (bytes == null || bytes.length <= 0 || TextUtils.isEmpty(filePath)) {
            return false;
        }

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        boolean success = true;
        try {
            in = new BufferedInputStream(new ByteArrayInputStream(bytes));
            out = new BufferedOutputStream(new FileOutputStream(filePath, false));
            byte[] temp = new byte[4096];
            int size = 0;
            while ((size = in.read(temp, 0, 4096)) != -1) {
                out.write(temp, 0, size);
            }
        } catch (Exception e) {
            success = false;
        } finally {
            close(in);
            close(out);
        }
        return success;
    }

    public static byte[] fileToByteArray(String filePath) {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            in = new BufferedInputStream(new FileInputStream(filePath));
            byte[] temp = new byte[4096];
            int size = 0;
            while ((size = in.read(temp, 0, 4096)) != -1) {
                out.write(temp, 0, size);
            }
            return out.toByteArray();
        } catch (Exception e) {
        } finally {
            close(in);
            close(out);
        }
        return null;
    }



    public static void close(InputStream in) {
        try{
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(OutputStream out) {
        try{
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
