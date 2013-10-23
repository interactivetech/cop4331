package com.example.pocket_monsters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NotificationsActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences pref = getApplication().getSharedPreferences("pref",0); 
        String encounter_dump = pref.getString("encounter", "not found");
        String[] encounter_array = encounter_dump.split(";");
        
        Encounter[] encounters = new Encounter[encounter_array.length];
        int i = 0;
        for( String each_encounter : encounter_array ){
        	String[] attributes = each_encounter.split(",");
        	encounters[i] = new Encounter(Integer.parseInt(attributes[0]),
        			Integer.parseInt(attributes[1]),Integer.parseInt(attributes[2]),
        			Integer.parseInt(attributes[3]),attributes[4]);
        	i++;
        }
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	setContentView(R.layout.activity_notifications);
    	
    	final ListView EncountersList = (ListView) findViewById(R.id.notificationList);
        final NotificationListAdapter adapter = new NotificationListAdapter(this, encounters);
        EncountersList.setAdapter(adapter);
	}
	
	public class NotificationListAdapter extends BaseAdapter{
    	Encounter[] encounters;
    	Context context;
    	private LayoutInflater inflater = null;
    	
    	public NotificationListAdapter(Context context, Encounter[] encounters) {
    		this.encounters = encounters;
    		this.context = context;
    		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	}
    	
    	@Override
    	public long getItemId(int position){
    		return position;
    	}
    	
    	@Override
    	public Object getItem(int position){
    		return encounters[position];
    	}
    	
    	@Override
    	public int getCount() {
    		return encounters.length;
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent ){
    		View view = convertView;
    		if (view == null){
    			view = inflater.inflate(R.layout.notifications_list_item, null);
    		}
    		TextView text = (TextView) view.findViewById(R.id.item_name);
    		text.setText(""+encounters[position].monster_id);
    		
    		text.setOnTouchListener(new OnTouchListener() {
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    		        switch(event.getAction()) {
	    		       	case MotionEvent.ACTION_DOWN:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.white_overlay));
	    	        		if( ((TextView) v).getText() == "Monster Index" ){
	    	        			Intent myIntent = new Intent(v.getContext(), MenuActivity.class);
	    		        		startActivity(myIntent);
	    	        		}
	    		            break;
	    		        case MotionEvent.ACTION_UP:
	    		        case MotionEvent.ACTION_MOVE:
	    		        case MotionEvent.ACTION_CANCEL:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.black_overlay));
    		        }
    		        return true;
    		    }
    		});
    		
    		return view;
    	}	
    }
}
