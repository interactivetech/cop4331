package com.example.pocket_monsters;

import com.example.pocket_monsters.MainActivity.Debug;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
      	setContentView(R.layout.activity_menu);
      	
      	final ImageView mainMenuMap = (ImageView) findViewById(R.id.map_image);
      	mainMenuMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(MenuActivity.this, MapActivity.class);
		    	MenuActivity.this.startActivity(myIntent);
			}
		});

      	final TextView mainMenuNotifications = (TextView) findViewById(R.id.notifications_title);
      	mainMenuNotifications.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(MenuActivity.this, NotificationsActivity.class);
		    	MenuActivity.this.startActivity(myIntent);
			}
		});
      	
      	final TextView mainMenuIndex = (TextView) findViewById(R.id.index_title);
      	mainMenuIndex.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(MenuActivity.this, IndexActivity.class);
		    	MenuActivity.this.startActivity(myIntent);
			}
		});
      	
      	final Button mainMenuItems = (Button) findViewById(R.id.items_link);
      	mainMenuItems.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(MenuActivity.this, ItemsActivity.class);
		    	MenuActivity.this.startActivity(myIntent);
			}
		});
	}
}
