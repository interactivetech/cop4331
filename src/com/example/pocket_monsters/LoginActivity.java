package com.example.pocket_monsters;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
      	setContentView(R.layout.activity_login);
        
      	final Button mainMenuItems = (Button) findViewById(R.id.login_btn);
      	mainMenuItems.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String username = ((EditText) findViewById(R.id.usernameInput)).getText().toString();
				String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString();
				
				DatabaseConnection db_connection = ((PocketMonsters) getApplication()).getConnection();
				
				if(db_connection.login(username,password)){
					Intent myIntent = new Intent(LoginActivity.this, MenuActivity.class);
					LoginActivity.this.startActivity(myIntent);
				}
			}
		});
      	 
    }

    public final static class Debug{
	    private Debug (){}

	    public static void out (Object msg){
	        Log.i ("myDebug", msg.toString ());
	    }
	}
}
