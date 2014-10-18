package com.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kuangcheng on 2014/9/30.
 */
public class TimeUtils {

    public static int getDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        return day;
    }

    public static String getTimeStr(long time) {
        int seconds = (int) (time/1000);
        String timeStr = "%02d:%02d";
        return String.format(timeStr, seconds/60, seconds%60);
    }
}
