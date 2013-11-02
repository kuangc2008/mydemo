package com.example.demo.Nov;

import com.example.demo.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.Button;

public class NotifyActivity extends Activity implements View.OnClickListener {
	NotificationManager nm = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notify);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
		findViewById(R.id.button5).setOnClickListener(this);
		nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	
	private void notifyUser() {
		NotificationCompat.Builder mBuilder = setMessage(android.R.drawable.ic_delete, 
				"love life!", "I will be good if I more creative!");
		
		Intent i = new Intent(this, com.example.demo.Nov.MergerCursorActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		
		nm.notify(0, mBuilder.build());
	}


	private NotificationCompat.Builder setMessage(int resId, String title, String content) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setSmallIcon(resId);
		mBuilder.setContentTitle(title);
		mBuilder.setContentText(content);
		return mBuilder;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			notifyUser();
			break;
		case R.id.button2:
			notifyUserWithNormalStack();
			break;
		case R.id.button3:
			notifyUserWithSpecialActivity();
			break;
		case R.id.button4:
			update();
			break;
		case R.id.button5:
			nm.cancelAll();
			mIsCancle = true;
			showNofityLargeIcion();
			break;
		case R.id.button6:
			showProgress();
			break;
		default:
			break;
		}
	}

	private void showProgress() {
		
	}


	private void showNofityLargeIcion() {
		Intent i = new Intent(this, com.example.demo.Nov.MoreMessageActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		Builder build = setMessage(android.R.drawable.ic_input_add, 
				"showNofityLargeIcion", "利用多巴胺");
		build.setContentIntent(pendingIntent);
		
		Intent nextIntent = new Intent(this, MyService.class);
		nextIntent.setAction("next");
		PendingIntent i1 = PendingIntent.getService(this, 0, nextIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
		
	
		Intent preIntent = new Intent(this, MyService.class);
		preIntent.setAction("pre");
		PendingIntent i2 = PendingIntent.getService(this, 0, preIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
		
		build.setStyle(new NotificationCompat.BigTextStyle().bigText("领导垃圾"));
		build.addAction(R.drawable.ic_launcher, "waha", i1);
		build.addAction(android.R.drawable.ic_dialog_alert,"hawa", i2);
		
		nm.notify(0, build.build());
		
	}

	int num = 0;
	boolean mIsCancle = false;
	private void update() {
		final NotificationCompat.Builder mBuilder = setMessage(android.R.drawable.ic_delete, 
				"love life!", "I will be good if I more creative!");
//		Intent i = new Intent(this, com.example.demo.Nov.MergerCursorActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//		mBuilder.setContentIntent(pendingIntent);
		mIsCancle = false;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!mIsCancle) {
					mBuilder.setContentText("good " +  (num++));
					mBuilder.setNumber(num);
					mBuilder.setAutoCancel(true);
					nm.notify(0, mBuilder.build());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}).start();
		
		
	}


	/**
	 * 默认情况下，新notification建立的activity是在最近的应用堆栈之上的。
	 */
	private void notifyUserWithSpecialActivity() {
		Intent i = new Intent(this, com.example.demo.Nov.MoreMessageActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Builder build = setMessage(android.R.drawable.ic_input_add, 
				"notifyUserWithSpecialActivity", "欲望的本源是什么？  真的是健康、希望与快乐吗");
		build.setContentIntent(pendingIntent);
		
		nm.notify(0, build.build());
	}


	private void notifyUserWithNormalStack() {
		Intent i = new Intent(this, com.example.demo.Nov.MergerCursorActivity.class);
		TaskStackBuilder taskBuilder = TaskStackBuilder.create(this);
		taskBuilder.addParentStack(MergerCursorActivity.class);
		taskBuilder.addNextIntent(i);
		PendingIntent pendingIntent = taskBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Builder build = setMessage(android.R.drawable.ic_input_add, "notifyUserWithNormalStack",
				"匡成，此乃一时成败，你可以的！");
		build.setContentIntent(pendingIntent);
		nm.notify(1, build.build());
	}
}
