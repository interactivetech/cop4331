package com.example.pocket_monsters;

import com.example.pocket_monsters.LoginActivity.Debug;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class InfoActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	setContentView(R.layout.activity_info);
    	
    	String img_path = (String) getIntent().getExtras().get("monster_img");
    	int image_id = InfoActivity.this.getApplicationContext().getResources().getIdentifier("drawable/"+img_path, null, getPackageName()); 
    	Debug.out(img_path+" = "+image_id);
    	
    	ImageView info_image = (ImageView) findViewById(R.id.info_image);
        info_image.setImageResource(image_id);
	}
}
