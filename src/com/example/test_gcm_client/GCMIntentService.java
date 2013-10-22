package com.example.test_gcm_client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	static final String SENDER_ID = "572545044114";
	private static final String LOG_TAG = "GCM-Test";
	//private static final String PUSH_DATA_TITLE = "test-title";
	//private static final String PUSH_DATA_MESSAGE = "message";

	public GCMIntentService() {
		super(SENDER_ID);//project ID
		if (BuildConfig.DEBUG)
			Log.d(LOG_TAG, "[GCMIntentService] start");
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// �޼����� �������� �� ���� ����
		if (BuildConfig.DEBUG)
			Log.d(LOG_TAG, "GCMReceiver Message");
		try {
		
			String title = intent.getStringExtra("title");
			String message = intent.getStringExtra("msg");
			Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(1000);
			setNotification(context, title, message);
			if (BuildConfig.DEBUG)
				Log.d(LOG_TAG, title + ":" + message);
		} catch (Exception e) {
			Log.e(LOG_TAG, "[onMessage] Exception : " + e.getMessage());
		}
	}


	@Override
	protected void onRegistered(Context arg0, String registrationId) {
		// TODO Auto-generated method stub
		Log.v(LOG_TAG, "onRegistered-registrationId = " + registrationId);
		  // ����̽� ��� ���̵� ��� ó�� ���� ����
		  // 3rd Party ���ø����̼� ������ �����
		
	}

	@Override
	protected void onUnregistered(Context arg0, String registrationId) {
		// TODO Auto-generated method stub
		// ����̽� ��ū ���� ������ ���� ����
		if (BuildConfig.DEBUG)
			Log.d(LOG_TAG, "onUnregistered-registrationId = " + registrationId);
	  // ����̽� ��� ���̵� ���� ó�� ���� ����
	  // 3rd Party ���ø����̼� ������ �����
		
	}
	
	private void setNotification(Context context, String title, String message) {
		NotificationManager notificationManager = null;
		Notification notification = null;
		try {
			notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			notification = new Notification(R.drawable.ic_launcher, message, System.currentTimeMillis());
			Intent intent = new Intent(context, MainActivity.class);
			PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
			notification.setLatestEventInfo(context, title, message, pi);
			notificationManager.notify(0, notification);
		} catch (Exception e) {
			Log.e(LOG_TAG, "[setNotification] Exception : " + e.getMessage());
		}
	}
}
