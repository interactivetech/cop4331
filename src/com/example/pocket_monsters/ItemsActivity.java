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
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ItemsActivity extends Activity{
	public static LocalDatabaseOpenHelper localData;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		localData = ((PocketMonsters) getApplication()).getDB();
        Item[] inventory = ((PocketMonsters) getApplication()).getInventory();

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	setContentView(R.layout.activity_items);
        
        final ListView itemsList = (ListView) findViewById(R.id.itemList);
        final ItemListAdapter adapter = new ItemListAdapter(this, inventory);
        itemsList.setAdapter(adapter);
        
	}
	
	public class ItemListAdapter extends BaseAdapter{
    	Item[] items;
    	Context context;
    	private LayoutInflater inflater = null;
    	
    	public ItemListAdapter(Context context, Item[] items) {
    		this.items = items;
    		this.context = context;
    		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	}
    	
    	@Override
    	public long getItemId(int position){
    		return position;
    	}
    	
    	@Override
    	public Object getItem(int position){
    		return items[position];
    	}
    	
    	@Override
    	public int getCount() {
    		return items.length;
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent ){
    		View view = convertView;
    		if (view == null){
    			view = inflater.inflate(R.layout.items_list_item, null);
    		}
    		TextView text = (TextView) view.findViewById(R.id.item_name);
    		text.setText(items[position].name);
    		
    		text.setOnTouchListener(new OnTouchListener() {
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    		        switch(event.getAction()) {
	    		       	case MotionEvent.ACTION_DOWN:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.white_overlay));
	    		            break;
	    		        case MotionEvent.ACTION_UP:
	    		        	((View) v.getParent()).setBackgroundColor(getResources().getColor(R.color.black_overlay));
	    		        	
	    		        	String item_name = (String) ((TextView) v).getText();
	    		        	Cursor cursor = localData.select(false, new String[]{"item_id","image","description","effect"},
    								"items", "name=?", new String[]{item_name}, 
    								null, null, null, null);
	    		        	
	    		        	String[] attribute_array = new String[5];
	    		        	while( cursor.moveToNext() ){
	    		        		int item_id_index = cursor.getColumnIndexOrThrow("item_id");
	    		        		String item_id = cursor.getString(item_id_index);
	    		        		attribute_array[0] = item_id;
	    		        		
	    		        		attribute_array[1] = item_name;
	    		        		
	    		        		int image_index = cursor.getColumnIndexOrThrow("image");
	    		        		String image = cursor.getString(image_index);
	    		        		attribute_array[2] = image;
	    		        				
	    		        		int description_index = cursor.getColumnIndexOrThrow("description");
	    		        		String description = cursor.getString(description_index);
	    		        		attribute_array[3] = description;
	    		        		
	    		        		int attack_index = cursor.getColumnIndexOrThrow("effect");
	    		        		String attack = cursor.getString(attack_index);
	    		        		attribute_array[3] = attack;
	    		        	}
	    		        	
	    		        	Intent myIntent = new Intent(v.getContext(), InfoActivity.class);
	    		        	myIntent.putExtra("type","items");
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
