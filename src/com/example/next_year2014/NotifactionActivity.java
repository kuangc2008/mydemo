package com.example.next_year2014;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.RemoteViews;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuangcheng on 2014/8/20.
 */
public class NotifactionActivity extends Activity {
    private NotificationManager mNotificationManager;
    Notification notif = null;
    private int displayChild = 0;
    private List<String> ss = new ArrayList<String>();
    {
        ss.add("111");
        ss.add("222");
        ss.add("333");
        ss.add("444"); ss.add("555");
        ss.add("666");
        ss.add("777");
        ss.add("888");
        ss.add("999");

    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.v("kcc", "showNext");
                    displayChild++;
                    if(displayChild >= 9) {
                        displayChild = 0;
                    }
//
//                    mBuilder.setContentIntent(makeMoodIntent(ss.get(displayChild)));
//                    mNotificationManager.notify(0, mBuilder.build());
//                    mhandler.sendEmptyMessageDelayed(0, 3000);

                    contentView.setTextViewText(R.id.text1, "gagag"); //必须要notify
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifaction_layout);
        findViewById(R.id.notication).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setMoodView(R.drawable.ic_launcher ,R.string.hello_world);
                setView();
            }
        });
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

//    private void setMoodView(int moodId, int textId) {
//        Notification notif = new Notification();
//        notif.contentIntent = makeMoodIntent(moodId);
//
//        CharSequence text = getText(textId);
//
//        notif.tickerText = text;
//        notif.icon = moodId;
//
//        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.status_bar_balloon);
//        contentView.setTextViewText(R.id.text, text);
//        contentView.setImageViewResource(R.id.icon, moodId);
//        notif.contentView = contentView;
//
//        // we use a string id because is a unique number.  we use it later to cancel the
//        // notification
//        mNotificationManager.notify(0, notif);
//        mhandler.sendEmptyMessageDelayed(0, 4000);
//    }

    NotificationCompat.Builder mBuilder = null;
    RemoteViews contentView = null;
    private void setView() {
        contentView = new RemoteViews(getPackageName(), R.layout.status_bar_balloon);
        contentView.setTextViewText(R.id.text, "hahha");
        contentView.setImageViewResource(R.id.icon, R.drawable.ic_launcher);
        contentView.setOnClickPendingIntent(R.id.text1, makeMoodIntent("111"));
        contentView.setOnClickPendingIntent(R.id.text2, makeMoodIntent("222"));
        contentView.setOnClickPendingIntent(R.id.text3, makeMoodIntent("333"));
        contentView.setOnClickPendingIntent(R.id.text4, makeMoodIntent("444"));
        contentView.setOnClickPendingIntent(R.id.text5, makeMoodIntent("555"));
        contentView.setOnClickPendingIntent(R.id.text6, makeMoodIntent("666"));

        mBuilder =
                new NotificationCompat.Builder(this).setContent(contentView);

//        mBuilder.setContentIntent(makeMoodIntent("111"));
        mBuilder.setAutoCancel(false);
        mBuilder.setTicker("111");
        mBuilder.setOngoing(true);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mNotificationManager.notify(0 , mBuilder.build ());

        mhandler.sendEmptyMessageDelayed(0, 3000);
        displayChild = 0;
//        contentView.showNext(R.id.flipper);
    }

    private PendingIntent makeMoodIntent(String text) {
        Intent inent = new Intent(Intent.ACTION_VIEW);
        inent.setData(Uri.parse("tel://" + text));
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                inent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }
}
