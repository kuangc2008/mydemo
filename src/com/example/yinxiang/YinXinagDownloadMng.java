package com.example.yinxiang;

import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.yinxiang.audio.YinXiangAudioResult;
import com.kc.toolbox.GsonRequest;
import com.kc.toolbox.MyJsonRequest;
import com.utils.FilePathHelper;
import com.utils.FileUtils;
import com.utils.GlobalUtils;
import com.utils.ParcelUtils;
import com.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kuangcheng on 2014/9/30.
 */
public class YinXinagDownloadMng {
    private RequestQueue mQueue = null;
    private Context mContext = null;
    private static YinXinagDownloadMng mng = null;

    private YinXinagDownloadMng(Context context) {
        mContext = GlobalUtils.mContext;
        mQueue = Volley.newRequestQueue(mContext);
    }

    public static YinXinagDownloadMng getInstance() {
        if(mng == null) {
            mng = new YinXinagDownloadMng(GlobalUtils.mContext);
        }
        return mng;
    }

    public void downloadNote() {
        MyJsonRequest request = new MyJsonRequest(Request.Method.GET,
                "https://cn.avoscloud.com:443/1/classes/UpdateConfig/542a8be3e4b0fecfe438ff8d",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                         downloadNewData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("kcc", "onErrorResponse", error);
                    }
                }
        );
        request.setHeader("X-AVOSCloud-Application-Id", "418dgirlkcy5pqph1tiuts5pxhdhjz4s3rft986mvevo73hi");
        request.setHeader("X-AVOSCloud-Application-Key", "r7cj4qp0t84ak240hdr9j62uqw6cv5qcl7og753c5lcp3pyp");
        mQueue.add(request);
    }

    private void downloadNewData(JSONObject response) {
        try {
            final int version = response.getInt("yinxiang_version");
            int oldVersion = PreferenceUtils.getInstance().getYinXiangVersion();
            oldVersion = 2;
            if(version > oldVersion) {
                JSONObject title = response.getJSONObject("title");
                final String titleUrl = title.getString("url");
                JSONObject url = response.getJSONObject("url");
                final String urlUrl = url.getString("url");
                Log.w("kcc", "titleurl-->" + titleUrl + "  urlurl-->" + urlUrl);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PreferenceUtils.getInstance().saveYinXinagVersionSuccess(false);
                        boolean isSuccessUrl = downloadFile(urlUrl, FilePathHelper.getDownloadUrlPath());
                        boolean isSuccessTitle = downloadFile(titleUrl, FilePathHelper.getDownloadTitlePath());
                        if (isSuccessTitle && isSuccessUrl) {
                            PreferenceUtils.getInstance().saveYinXinagVersion(version);
                            PreferenceUtils.getInstance().saveYinXinagVersionSuccess(true);
                        }
                    }
                }).start();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean downloadFile(String urlStr, String path) {
        boolean sucess = true;
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("X-AVOSCloud-Application-Id", "418dgirlkcy5pqph1tiuts5pxhdhjz4s3rft986mvevo73hi");
            urlConnection.addRequestProperty("X-AVOSCloud-Application-Key", "r7cj4qp0t84ak240hdr9j62uqw6cv5qcl7og753c5lcp3pyp");
            urlConnection.addRequestProperty("Content-Type", "application/octet-stream");
            urlConnection.connect();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            outputStream = new BufferedOutputStream(new FileOutputStream(path));

            byte[] buffer = new byte[1024 * 8];
            int len = -1;
            while( (len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            sucess = false;
        } finally {
            FileUtils.close(inputStream);
            FileUtils.close(outputStream);
        }
        return sucess;
    }


    public void downloadAudio(final Response.Listener<YinXiangAudioResult> listener) {
        MyJsonRequest request = new MyJsonRequest(Request.Method.GET,
                "https://cn.avoscloud.com:443/1/classes/UpdateConfig/544201f2e4b09f67a87506be",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        downloadNewAudioDataByVersion(response, listener);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("kcc", "onErrorResponse", error);
                    }
                });
        request.setHeader("X-AVOSCloud-Application-Id", "418dgirlkcy5pqph1tiuts5pxhdhjz4s3rft986mvevo73hi");
        request.setHeader("X-AVOSCloud-Application-Key", "r7cj4qp0t84ak240hdr9j62uqw6cv5qcl7og753c5lcp3pyp");
        mQueue.add(request);
    }

    private void downloadNewAudioDataByVersion(JSONObject response, final Response.Listener<YinXiangAudioResult> listener) {
        try {
            final int version = response.getInt("yinxiang_version");
            int oldVersion = PreferenceUtils.getInstance().getYinXiangAudioVersion();
            if(version > oldVersion) {
                //下载新数据
                downloadNewAudioData(listener, version);
            } else {
                //从本地取出数据，并notifiy
                byte[] bytes = FileUtils.fileToByteArray(FilePathHelper.getAudioPath());
                YinXiangAudioResult result = ParcelUtils.readItemFromTypes(bytes, YinXiangAudioResult.CREATOR);
                listener.onResponse(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void downloadNewAudioData(final Response.Listener<YinXiangAudioResult> listener, final int version) {
        GsonRequest request = new GsonRequest(Request.Method.GET,
                "https://cn.avoscloud.com:443/1/classes/YiinXiangAudio",
                YinXiangAudioResult.class,
                null,
                new Response.Listener<YinXiangAudioResult>() {
                    @Override
                    public void onResponse(YinXiangAudioResult response) {
                        listener.onResponse(response);  //通知界面
                        //保存到本地，并保存版本号
                        Parcel parcel = ParcelUtils.writeToParcel(response);
                        FileUtils.byteArrayToFile(parcel.marshall(), FilePathHelper.getAudioPath());
                        PreferenceUtils.getInstance().saveYinXinagAudioVersion(version);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("kcc", "onErrorResponse", error);
                    }
                });
        request.setHeader("X-AVOSCloud-Application-Id", "418dgirlkcy5pqph1tiuts5pxhdhjz4s3rft986mvevo73hi");
        request.setHeader("X-AVOSCloud-Application-Key", "r7cj4qp0t84ak240hdr9j62uqw6cv5qcl7og753c5lcp3pyp");
        mQueue.add(request);
    }
}
