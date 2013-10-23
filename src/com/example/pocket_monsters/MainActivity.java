package com.example.pocket_monsters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        
        Intent myIntent = new Intent(MainActivity.this, MenuActivity.class);
    	MainActivity.this.startActivity(myIntent); 
    }

    public final static class Debug{
	    private Debug (){}

	    public static void out (Object msg){
	        Log.i ("myDebug", msg.toString ());
	    }
	}
}
