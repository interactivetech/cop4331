package com.example.pocket_monsters;

import java.util.Arrays;

import com.example.pocket_monsters.IndexActivity.IndexListAdapter;
import com.example.pocket_monsters.LoginActivity.Debug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences pref = getApplication().getSharedPreferences("pref",0); 
        String monster_dump = pref.getString("monster", "not found");
        String[] monster_array = monster_dump.split(";");
        
        Monster[] index = new Monster[monster_array.length];
        int i = 0;
        for( String each_item : monster_array ){ // creates monsters into index
        	String[] attributes = each_item.split(",");
        	//index[i] = new Monster(Integer.parseInt(attributes[0]),attributes[1],attributes[2],attributes[3],attributes[4]);
        	index[i] = new Monster(attributes[1], " bio here", attributes[3], Integer.parseInt(attributes[0]), 1, 0, 30, 30, 5, 5, 5);

        	i++;
        }
        
        String encounter_dump = pref.getString("encounter", "not found");
        String[] encounter_array = encounter_dump.split(";");
        
        Encounter[] encounters = new Encounter[encounter_array.length];
        i = 0;
        for( String each_encounter : encounter_array ){
        	String[] attributes = each_encounter.split(",");
        	encounters[i] = new Encounter(Integer.parseInt(attributes[0]),
        			index[i].location,index[i].name,
        			Integer.parseInt(attributes[3]),attributes[4]);
        	i++;
        }
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
      	setContentView(R.layout.activity_menu);
      	
        // makes the list of monsters under index
      	final ListView indexList = (ListView) findViewById(R.id.index_items); 
        final IndexQuickListAdapter index_adapter = new IndexQuickListAdapter(this, index);
        indexList.setAdapter(index_adapter);
      	
        // creates list under notifications
        final ListView notificationsList = (ListView) findViewById(R.id.notification_items);
        Encounter[] e = {encounters[0],encounters[1],encounters[2]};
        final NotificationQuickListAdapter notification_adapter = new NotificationQuickListAdapter(this, e);
        notificationsList.setAdapter(notification_adapter);
        
        // creates map
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
      	// Links the item button to the item activity
      	final Button mainMenuItems = (Button) findViewById(R.id.items_link);
      	mainMenuItems.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(MenuActivity.this, ItemsActivity.class);
		    	MenuActivity.this.startActivity(myIntent);
			}
		});
     // Links the monster deck button to the monsterdeck activity
      	final Button mainMenuMonsterdeck = (Button) findViewById(R.id.monster_deck);
      	mainMenuMonsterdeck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(MenuActivity.this, Monsterdeck.class);
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
