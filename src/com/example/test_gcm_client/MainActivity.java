package com.example.test_gcm_client;

import java.io.IOException;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;


public class MainActivity extends Activity{

	private static final String LOG_TAG = "GCMsample";
	String senderId = "572545044114";
	String apiKey = "AIzaSyARNoK7rYtcXc89_8MSzQ4iBuhPx48ZyW8";
	Button btnSendGcmMessage;
	
    /**
     * 화면 설정
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 디바이스 GCM 사용 가능한지 확인
        GCMRegistrar.checkDevice(this);
        // 매니페스트 설정이 올바른지 확인
        GCMRegistrar.checkManifest(this);
        // 등록
        registerToken();
        
        btnSendGcmMessage = (Button)findViewById(R.id.button1);
        btnSendGcmMessage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Sender sender = new Sender(apiKey);
						Message msg = new Message.Builder().addData("title", "welcome").addData("msg", "hello world").build();
						try {
							//String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
							String regId = "APA91bGDmaqTAjyAlh1W0xtvlcSNC72FJyM3FuJzDHpARzUP2KGYjnnWyq_Of9-z3yS7rYZ3qSW8fnNvDJogdUWn8OboaPocZSoy-Mk67PywfoV_OFl4f9wCI46Db_nX1d8st-bJi_Ah-KVf1X7hb108I5ooB7YOVQ";
							Result result = sender.send(msg, regId, 5);
						} catch(IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
        });
    }
   
    
    /**
     * GCM에 디바이스 토큰 등록
     */
    private void registerToken() {
        // registration ID（디바이스 토큰) 취득하고 등록되지 않은 경우 GCM에 등록
        final String regId = GCMRegistrar.getRegistrationId(this);
        if ("".equals(regId)) {
        	GCMRegistrar.register(this, GCMIntentService.SENDER_ID);
        }
        else
        	Log.d(LOG_TAG, "[GCMIntentService] start");
    }
    
    /**
     * GCM에 디바이스토큰 삭제
     */
    private void unregisterToken() {
        if (GCMRegistrar.isRegistered(this)) {
        	GCMRegistrar.unregister(this);
        }
    }
    
}
