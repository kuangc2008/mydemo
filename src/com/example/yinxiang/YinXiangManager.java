package com.example.yinxiang;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.provider.CalendarContract;
import android.text.TextUtils;

import com.example.demo.R;
import com.utils.FileUtils;
import com.utils.ParcelUtils;
import com.utils.PreferenceUtils;
import com.utils.TimeUtils;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kuangcheng on 2014/9/30.
 */
public class YinXiangManager {
    public static final int REFRESH_MAX_TIME = 1;

    public static List<YinXiangNote> getNoteList(Context context) {
        List notes = new ArrayList();

        String titlesStr = null;
        String urlsStr = null;
        titlesStr = getStringFromLocal(context,YinXinagDownloadMng.getDownloadTitlePath());
        urlsStr = getStringFromLocal(context,YinXinagDownloadMng.getDownloadUrlPath());
        if(TextUtils.isEmpty(titlesStr) || TextUtils.isEmpty(urlsStr)) {
            titlesStr = FileUtils.getStringFromRawFile(context, context.getResources().openRawResource(R.raw.names));
            urlsStr = FileUtils.getStringFromRawFile(context, context.getResources().openRawResource(R.raw.urls));
        }

        String[] titles = titlesStr.split("\n");
        String[] urls = urlsStr.split("\n");

        for(int i=0; i<titles.length; i++) {
            YinXiangNote note = new YinXiangNote();
            note.title = titles[i];
            note.url =urls[i];
            notes.add(note);
        }
        return notes;
    }

    private static String getStringFromLocal(Context context, String path) {
        String titlesStr = null;
        if(PreferenceUtils.getInstance().getYinXinagVersionSuccess()) {
            try {
                FileInputStream inputStream = new FileInputStream(path);
                titlesStr = FileUtils.getStringFromRawFile(context, inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return titlesStr;
    }


    public static List<YinXiangNote> getRandomNoteList(Context context, boolean isRefresh) {
        return getRandomNoteList(context, isRefresh, 7 , 20);
    }

    public static List<YinXiangNote> getRandomNoteList(Context context) {
        return getRandomNoteList(context, false);
    }

    public static List<YinXiangNote> getRandomNoteList(Context context, boolean isRefresh, int minSize, int maxSize) {
        long oldSaveTime = PreferenceUtils.getInstance().getYinXiangTime();
        long now = System.currentTimeMillis();
        List<YinXiangNote> showNotes = new ArrayList<YinXiangNote>();
        if (!isRefresh && TimeUtils.getDay(oldSaveTime)  ==  TimeUtils.getDay(now)) {
            File file = new File(context.getFilesDir(), "notes");
            byte[] bytes = FileUtils.fileToByteArray(file.toString());
            showNotes = ParcelUtils.readListFromTypes(bytes, YinXiangNote.CREATOR);
        } else {
            List<YinXiangNote> notes = getNoteList(context);
            Random random = new Random();
            int size = random.nextInt(maxSize - minSize) + minSize;
            for (int i = 0; i <= size; i++) {
                int revmoeNote = random.nextInt(notes.size());
                YinXiangNote note = notes.remove(revmoeNote);
                showNotes.add(note);
            }

            Parcel parcel = ParcelUtils.writeToParcel(showNotes);
            byte[] bytes = parcel.marshall();
            File file = new File(context.getFilesDir(), "notes");
            boolean sucess = FileUtils.byteArrayToFile(bytes, file.toString());
            if (sucess) {
                PreferenceUtils.getInstance().saveYinXiangTime(System.currentTimeMillis());
                if(!isRefresh) {
                    PreferenceUtils.getInstance().saveRefreshTime(0);
                } else {
                    PreferenceUtils.getInstance().saveRefreshTime(1);
                }
            }
        }
        return showNotes;
    }


    public static void downloadYunFile(Context context, String url) {

    }
}
