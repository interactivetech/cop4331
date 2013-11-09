package com.example.pocket_monsters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends Activity {
	public static LocalDatabaseOpenHelper localData;

	/*
	protected void onResume() {
		super.onResume();
		
	}
	*/
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		localData = ((PocketMonsters) getApplication()).getDB();
        Cursor cursor = localData.select(false, new String[]{"monster_id","name","image","description","attack"},
				"monsters", null, null, null, null, null, null);
        
        int monster_count = cursor.getCount();
        Monster[] index = new Monster[monster_count];
        int i = 0;
        while( cursor.moveToNext() ){
        	int id_index = cursor.getColumnIndexOrThrow("monster_id");
    		String monster_id = cursor.getString(id_index);
    			
			int name_index = cursor.getColumnIndexOrThrow("name");
			String name = cursor.getString(name_index);
			
			int image_index = cursor.getColumnIndexOrThrow("image");
			String image = cursor.getString(image_index);
			
    		int description_index = cursor.getColumnIndexOrThrow("description");
    		String description = cursor.getString(description_index);
			
			
			int location_index = cursor.getColumnIndexOrThrow("location");
			String location = cursor.getString(location_index);
			
			int level_index = cursor.getColumnIndexOrThrow("level");
			String level = cursor.getString(level_index);
			
			int exp_index = cursor.getColumnIndexOrThrow("exp");
			String exp = cursor.getString(exp_index);
			
			int maxhp_index = cursor.getColumnIndexOrThrow("maxhp");
			String maxhp = cursor.getString(maxhp_index);
			
			int curhp_index = cursor.getColumnIndexOrThrow("curhp");
			String curhp = cursor.getString(curhp_index);
			
			int str_index = cursor.getColumnIndexOrThrow("str");
			String str = cursor.getString(str_index);
			
			int agi_index = cursor.getColumnIndexOrThrow("agi");
			String agi = cursor.getString(agi_index);
			
			int arm_index = cursor.getColumnIndexOrThrow("arm");
			String arm = cursor.getString(arm_index);
			
			int active_index = cursor.getColumnIndexOrThrow("active");
			String active = cursor.getString(active_index);
			
			index[i] = new Monster(name, description, image, location, Integer.parseInt(monster_id),Integer.parseInt(level), Integer.parseInt(exp), Integer.parseInt(maxhp), Integer.parseInt(curhp), Integer.parseInt(str), Integer.parseInt(agi), Integer.parseInt(arm), Integer.parseInt(active)); 
			i++; //(String name, String bio, String image, String location, int id, int level, int exp, int maxhp, int curhp, int str, int agi, int arm)
		}

        cursor = localData.select(false, new String[]{"encounter_id","location_id","monster_id","monster_lvl"},
				"encounters", null, null, null, null, null, "3");
        int encounter_count = cursor.getCount();
        Encounter[] encounters = new Encounter[encounter_count];
        i = 0;
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
    	
      	setContentView(R.layout.activity_menu);
      	
      	final ListView indexList = (ListView) findViewById(R.id.index_items);
        final IndexQuickListAdapter index_adapter = new IndexQuickListAdapter(this, index);
        indexList.setAdapter(index_adapter);
      	
        final ListView notificationsList = (ListView) findViewById(R.id.encounter_items);
        final NotificationQuickListAdapter notification_adapter = new NotificationQuickListAdapter(this, encounters);
        notificationsList.setAdapter(notification_adapter);		
	
        final ImageView mainMenuMap = (ImageView) findViewById(R.id.map_image);
      	mainMenuMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(MenuActivity.this, MapActivity.class);
		    	MenuActivity.this.startActivity(myIntent);
			}
		});

      	final TextView mainMenuNotifications = (TextView) findViewById(R.id.encounters_title);
      	mainMenuNotifications.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(MenuActivity.this, EncountersActivity.class);
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
	
	public class IndexQuickListAdapter extends BaseAdapter{
    	Monster[] index;
    	Context context;
    	private LayoutInflater inflater = null;
    	
    	public IndexQuickListAdapter(Context context, Monster[] index) {
    		this.index = index;
    		this.context = context;
    		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	}
    	
    	@Override
    	public long getItemId(int position){
    		return position;
    	}
    	
    	@Override
    	public Object getItem(int position){
    		return index[position];
    	}
    	
    	@Override
    	public int getCount() {
    		return index.length;
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent ){
    		View view = convertView;
    		if (view == null){
    			view = inflater.inflate(R.layout.index_quick_item, null);
    		}
    		TextView text = (TextView) view.findViewById(R.id.index_quick_name);
    		text.setText(index[position].name);
    		
    		text.setOnTouchListener(new OnTouchListener() {
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    		        switch(event.getAction()) {
	    		       	case MotionEvent.ACTION_DOWN:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.white_overlay));
	    		            break;
	    		        case MotionEvent.ACTION_UP:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.black_overlay));
    	        			Intent myIntent = new Intent(v.getContext(), MapActivity.class);
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
	
	public class NotificationQuickListAdapter extends BaseAdapter{
    	Encounter[] encounters;
    	Context context;
    	private LayoutInflater inflater = null;
    	
    	public NotificationQuickListAdapter(Context context, Encounter[] encounters) {
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
    			view = inflater.inflate(R.layout.encounter_quick_item, null);
    		}
    		TextView text = (TextView) view.findViewById(R.id.encounter_quick_name);
    		
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
