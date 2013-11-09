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
import android.widget.ListView;
import android.widget.TextView;

public class IndexActivity extends Activity{
	
	static LocalDatabaseOpenHelper localData;
	
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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	setContentView(R.layout.activity_index);
    	
    	final ListView indexList = (ListView) findViewById(R.id.indexList);
        final IndexListAdapter adapter = new IndexListAdapter(this, index);
        indexList.setAdapter(adapter);
        
	}
	
	public class IndexListAdapter extends BaseAdapter{
    	Monster[] index;
    	Context context;
    	private LayoutInflater inflater = null;
    	
    	public IndexListAdapter(Context context, Monster[] index) {
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
    			view = inflater.inflate(R.layout.index_list_item, null);
    		}
    		TextView text = (TextView) view.findViewById(R.id.monster_name);
    		text.setText(index[position].name);
    		
    		Button location = (Button) view.findViewById(R.id.monster_location);
    		location.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent myIntent = new Intent(IndexActivity.this, MapActivity.class);
			    	IndexActivity.this.startActivity(myIntent);
				}
    		});
    		
    		Button order = (Button) view.findViewById(R.id.monster_order);
    		order.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent myIntent = new Intent(IndexActivity.this, MapActivity.class);
			    	IndexActivity.this.startActivity(myIntent);
				}
    		});
    		
    		Button activate = (Button) view.findViewById(R.id.monster_activate);
    		activate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) { // find current active monster switch to 0 
					
					Intent myIntent = new Intent(IndexActivity.this, MapActivity.class);
			    	IndexActivity.this.startActivity(myIntent);
				}
    		});
    		
    		
    		text.setOnTouchListener(new OnTouchListener() {
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    		        switch(event.getAction()) {
	    		       	case MotionEvent.ACTION_DOWN:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.white_overlay));
    	        			break;
	    		        case MotionEvent.ACTION_UP:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.black_overlay));
	    		        	
	    		        	String monster_name = (String) ((TextView) v).getText();
	    		        	Cursor cursor = localData.select(false, new String[]{"monster_id","image","description","attack"},
    								"monsters", "name=?", new String[]{monster_name}, 
    								null, null, null, null);
	    		        	
	    		        	String[] attribute_array = new String[5];
	    		        	while( cursor.moveToNext() ){
	    		        		int monster_id_index = cursor.getColumnIndexOrThrow("monster_id");
	    		        		String monster_id = cursor.getString(monster_id_index);
	    		        		attribute_array[0] = monster_id;
	    		        		
	    		        		attribute_array[1] = monster_name;
	    		        		
	    		        		int image_index = cursor.getColumnIndexOrThrow("image");
	    		        		String image = cursor.getString(image_index);
	    		        		attribute_array[2] = image;
	    		        		
	    		        		int description_index = cursor.getColumnIndexOrThrow("description");
	    		        		String description = cursor.getString(description_index);
	    		        		attribute_array[3] = description;
	    		        		
	    		        		int attack_index = cursor.getColumnIndexOrThrow("attack");
	    		        		String attack = cursor.getString(attack_index);
	    		        		attribute_array[3] = attack;
	    		        	}
	    		        	
	    		        	Intent myIntent = new Intent(v.getContext(), InfoActivity.class);
	    		        	myIntent.putExtra("type","monsters");
	    		        	myIntent.putExtra("attributes", attribute_array);
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
