package com.example.yinxiang;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.provider.CalendarContract;

import com.example.demo.R;
import com.utils.FileUtils;
import com.utils.ParcelUtils;
import com.utils.PreferenceUtils;
import com.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kuangcheng on 2014/9/30.
 */
public class YinXiangManager {
    private static final String fileName = null;

    public static List<YinXiangNote> getNoteList(Context context) {
        List notes = new ArrayList();

        String titlesStr = FileUtils.getStringFromRawFile(context, R.raw.names);
        String urlsStr = FileUtils.getStringFromRawFile(context, R.raw.urls);

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

    public static List<YinXiangNote> getRandomNoteList(Context context) {
        return getRandomNoteList(context, 8 , 15);
    }

    public static List<YinXiangNote> getRandomNoteList(Context context, int minSize, int maxSize) {
        long oldSaveTime = PreferenceUtils.getInstance().getYinXiangTime();
        long now = System.currentTimeMillis();
        List<YinXiangNote> showNotes = new ArrayList<YinXiangNote>();
        if (TimeUtils.getDay(oldSaveTime)  ==  TimeUtils.getDay(now)) {
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
            }
        }
        return showNotes;
    }
}
