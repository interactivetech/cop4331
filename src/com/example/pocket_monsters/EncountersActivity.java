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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EncountersActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences pref = getApplication().getSharedPreferences("pref",0); 
		String monster_dump = pref.getString("monster", "not found");
        String[] monster_array = monster_dump.split(";");
        
        Monster[] index = new Monster[monster_array.length];
        int i = 0;
        for( String each_item : monster_array ){
        	String[] attributes = each_item.split(",");
        	index[i] = new Monster(Integer.parseInt(attributes[0]),
        			attributes[1],attributes[2],attributes[3],attributes[4]);
        	i++;
        }
        
		String encounter_dump = pref.getString("encounter", "not found");
        String[] encounter_array = encounter_dump.split(";");
        
        Encounter[] encounters = new Encounter[encounter_array.length];
        i = 0;
        for( String each_encounter : encounter_array ){
        	String[] attributes = each_encounter.split(",");
        	encounters[i] = new Encounter(Integer.parseInt(attributes[0]),
        			index[i].location,index[i].name,Integer.parseInt(attributes[3]),attributes[4]);
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
    			view = inflater.inflate(R.layout.encounters_list_item, null);
    		}
    		TextView text = (TextView) view.findViewById(R.id.encounter_name);
    		text.setText(encounters[position].monster+" lvl:"+encounters[position].monster_level+" at "+encounters[position].location);
    		
    		text.setOnTouchListener(new OnTouchListener() {
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    		        switch(event.getAction()) {
	    		       	case MotionEvent.ACTION_DOWN:
	    		       		((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.white_overlay));
	    		            break;
	    		        case MotionEvent.ACTION_UP:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.black_overlay));
	    		        	Intent myIntent = new Intent(v.getContext(), GameActivity.class);
    		        		startActivity(myIntent);
    		        		break;
	    		        case MotionEvent.ACTION_MOVE:
	    		        case MotionEvent.ACTION_CANCEL:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.black_overlay));
	    		        	break;
    		        }
    		        return true;
    		    }
    		});
    		
    		return view;
    	}	
    }
}
