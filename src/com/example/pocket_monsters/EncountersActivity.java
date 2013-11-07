package com.example.pocket_monsters;

import com.example.pocket_monsters.LoginActivity.Debug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EncountersActivity extends Activity{
	public static LocalDatabaseOpenHelper localData;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        localData = ((PocketMonsters) getApplication()).getDB();
        Cursor cursor = localData.select(false, new String[]{"encounter_id","location_id","monster_id","monster_lvl"},
				"encounters", null, null, null, null, null, null);
        
        int encounter_count = cursor.getCount();
        Encounter[] encounters = new Encounter[encounter_count];
        int i = 0;
        while( cursor.moveToNext() ){
        	int encounter_index = cursor.getColumnIndexOrThrow("encounter_id");
			String encounter_id = cursor.getString(encounter_index);
			
			int location_index = cursor.getColumnIndexOrThrow("location_id");
    		String location_id = cursor.getString(location_index);
    		
    		int monster_index = cursor.getColumnIndexOrThrow("monster_id");
    		String monster_id = cursor.getString(monster_index);
			
			int level_index = cursor.getColumnIndexOrThrow("monster_lvl");
			String monster_lvl = cursor.getString(level_index);
			
			encounters[i] = new Encounter(Integer.parseInt(encounter_id), Integer.parseInt(location_id),
										Integer.parseInt(monster_id), Integer.parseInt(monster_lvl)); 
			i++;
		}
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	setContentView(R.layout.activity_encounters);
    	
    	final ListView EncountersList = (ListView) findViewById(R.id.encountersList);
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
    	public View getView(final int position, View convertView, ViewGroup parent ){
    		View view = convertView;
    		if (view == null){
    			view = inflater.inflate(R.layout.encounters_list_item, null);
    		}
    		TextView text = (TextView) view.findViewById(R.id.encounter_name);
    		
    		int monster_id = encounters[position].monster_id;
    		String monster_name = "";
    		Cursor cursor = localData.select(false, new String[]{"name"},
    				"monsters", "monster_id =?", new String[]{""+monster_id}, null, null, null, null);
    		while( cursor.moveToNext() ){
	        	int monster_index = cursor.getColumnIndexOrThrow("name");
				monster_name = cursor.getString(monster_index);
    		}
    		
    		int location_id = encounters[position].location_id;
    		String location_name = "";
    		cursor = localData.select(false, new String[]{"name"},
    				"locations", "location_id =?", new String[]{""+location_id}, null, null, null, null);
    		while( cursor.moveToNext() ){
	        	int location_index = cursor.getColumnIndexOrThrow("name");
				location_name = cursor.getString(location_index);
    		}
    		
    		text.setText(monster_name+" lvl:"+encounters[position].monster_level+" at "+location_name);
    		
    		text.setOnTouchListener(new OnTouchListener() {
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    		        switch(event.getAction()) {
	    		       	case MotionEvent.ACTION_DOWN:
	    		       		((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.white_overlay));
	    		            break;
	    		        case MotionEvent.ACTION_UP:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.black_overlay));
    		        		break;
	    		        case MotionEvent.ACTION_MOVE:
	    		        case MotionEvent.ACTION_CANCEL:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.black_overlay));
	    		        	break;
    		        }
    		        return true;
    		    }
    		});
    		
    		Button fight = (Button) view.findViewById(R.id.encounter_fight);
          	fight.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View view) {
    				Intent myIntent = new Intent(view.getContext(), GameActivity.class);
    				startActivity(myIntent);
    			}
    		});
          	
          	Button run = (Button) view.findViewById(R.id.encounter_run);
          	run.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View view) {
    				localData.delete("encounters", "encounter_id = ?", new String[]{""+encounters[position].id});
    				EncountersActivity.this.finish();
    				Intent myIntent = new Intent(view.getContext(), EncountersActivity.class);
    				startActivity(myIntent);
    			}
    		});
    		
    		return view;
    	}	
    }
}
