package com.example.yinxiang;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.yinxiang.audio.AudioDBHelper;
import com.example.yinxiang.audio.AudioProvider;
import com.example.yinxiang.audio.YinXiangAudio;
import com.example.yinxiang.audio.YinXiangAudioNote;
import com.example.yinxiang.audio.YinXiangAudioResult;
import com.kc.toolbox.GsonRequest;
import com.kc.toolbox.MyJsonRequest;
import com.utils.FilePathHelper;
import com.utils.FileUtils;
import com.utils.GlobalUtils;
import com.utils.ParcelUtils;
import com.utils.PreferenceUtils;

import org.apache.http.client.utils.URIUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public boolean downloadFile(String urlStr, String path) {
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
                        getFromLocalDB(listener);
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
//                byte[] bytes = FileUtils.fileToByteArray(FilePathHelper.getAudioPath("audio_file"));
//                YinXiangAudioResult result = ParcelUtils.readItemFromTypes(bytes, YinXiangAudioResult.CREATOR);

                getFromLocalDB(listener);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getFromLocalDB(final Response.Listener<YinXiangAudioResult> listener) {
        if(YinXiangAudio.INSTANCE != null) {
            YinXiangAudio.INSTANCE.getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    return new CursorLoader( YinXiangAudio.INSTANCE,
                            AudioProvider.CONTENT_URI,
                            AudioDBHelper.ALL_COlUMN,
                            null,
                            null,
                            null);
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    YinXiangAudioResult result = new YinXiangAudioResult();
                    List<YinXiangAudioNote> notes = new ArrayList<YinXiangAudioNote>();
                    while(data.moveToNext()) {
                        String desc = data.getString(AudioDBHelper.DESC_INDEX);
                        String url = data.getString(AudioDBHelper.URL_INDEX);
                        String title = data.getString(AudioDBHelper.TITLE_INDEX);
                        String objectid = data.getString(AudioDBHelper.OBJECT_ID_INDEX);
                        String filePath = data.getString(AudioDBHelper.FILE_PATH_INDEX);
                        YinXiangAudioNote note = new YinXiangAudioNote(title, desc, new YinXiangFile(url), objectid, filePath);
                        note.setId(data.getInt(AudioDBHelper.ID_INDEX));
                        notes.add(note);
                    }
                    result.setResults(notes);
                    listener.onResponse(result);
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {

                }
            });
        }
    }

    private void downloadNewAudioData(final Response.Listener<YinXiangAudioResult> listener, final int version) {
        final GsonRequest request = new GsonRequest(Request.Method.GET,
                "https://cn.avoscloud.com:443/1/classes/YiinXiangAudio",
                YinXiangAudioResult.class,
                null,
                new Response.Listener<YinXiangAudioResult>() {
                    @Override
                    public void onResponse(final YinXiangAudioResult response) {
                        //保存到本地，并保存版本号
//                        Parcel parcel = ParcelUtils.writeToParcel(response);
//                        FileUtils.byteArrayToFile(parcel.marshall(), FilePathHelper.getAudioPath("audio_file"));
//                        PreferenceUtils.getInstance().saveYinXinagAudioVersion(version);
                        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
                            @Override
                            protected Boolean doInBackground(Void... params) {
                                final List<YinXiangAudioNote> notes = response.getResults();
                                for(YinXiangAudioNote note : notes) {
                                    Cursor c = mContext.getContentResolver().query(
                                            AudioProvider.CONTENT_URI,
                                            new String[]{AudioDBHelper._ID, AudioDBHelper.FILE_PATH},
                                            AudioDBHelper.OBJECT_ID + "=?",
                                            new String[]{note.getObjectId()},
                                            null);
                                    if(c != null && c.moveToFirst()) {
                                        String filePath = c.getString(1);
                                        note.setFilePath(filePath);
                                        note.setId(c.getInt(0));
                                    } else {
                                        ContentValues cv = new ContentValues();
                                        cv.put(AudioDBHelper.OBJECT_ID, note.getObjectId());
                                        cv.put(AudioDBHelper.TITLE, note.getTitle());
                                        cv.put(AudioDBHelper.URL, note.getUri().getUrl());
                                        cv.put(AudioDBHelper.DESC, note.getDescription());
                                        Uri result = mContext.getContentResolver().insert(AudioProvider.CONTENT_URI, cv);
                                        String longid = result.getPathSegments().get(1);
                                        note.setId(Integer.parseInt(longid));
                                        if (result == null) {
                                            return false;
                                        }
                                    }
                                }
                                return true;
                            }

                            @Override
                            protected void onPostExecute(Boolean aBoolean) {
                                if(aBoolean) {
                                    PreferenceUtils.getInstance().saveYinXinagAudioVersion(version);
                                }
                                listener.onResponse(response);  //通知界面
                            }
                        };
                        task.execute((Void[])null);
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
