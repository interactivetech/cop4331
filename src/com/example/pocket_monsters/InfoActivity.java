package com.example.pocket_monsters;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	setContentView(R.layout.activity_info);
    	
    	String type = (String) getIntent().getExtras().get("type");
    	String[] attributes = (String[]) getIntent().getExtras().get("attributes");
    	
    	String id = attributes[0];
    	String name = attributes[1];
    	String image = attributes[2];
    	//need to generalize for items and monsters
    	String stats = attributes[3];
    	
	    int image_id = InfoActivity.this.getApplicationContext().getResources().getIdentifier("drawable/"+image, null, getPackageName()); 
    	ImageView info_image = (ImageView) findViewById(R.id.info_image);
        info_image.setImageResource(image_id);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(id+" "+name);
	}
}
