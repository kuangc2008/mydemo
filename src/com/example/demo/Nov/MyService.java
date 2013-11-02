package com.example.demo.Nov;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("kc", " startId-->" + startId + "   intent-->" + intent);
		return super.onStartCommand(intent, flags, startId);
	}

}
