package com.example.pocket_monsters;

import com.example.pocket_monsters.LoginActivity.Debug;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class EncounterService extends Service{

	@Override
	public void onCreate(){
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		//startBackgroundTask(intent, startId);
		return Service.START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}

}
