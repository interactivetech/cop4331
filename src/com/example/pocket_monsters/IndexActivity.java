package com.example.pocket_monsters;

import com.example.pocket_monsters.ItemsActivity.ItemListAdapter;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class IndexActivity extends Activity{
	
	static LocalDatabaseOpenHelper localData;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		localData = ((PocketMonsters) getApplication()).getDB();
	    Cursor cursor = localData.select(false, new String[]{"monster_id","name","image","description","location","level","exp","maxhp","curhp","str","agi","arm","active"},
	    								"monsters", null, null, null, null, null, null);
	        
        int monster_count = cursor.getCount();
        Monster[] index = new Monster[monster_count];
        index = Monster.fetchMonsters(localData);
        

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