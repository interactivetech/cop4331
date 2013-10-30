package com.example.pocket_monsters;

import com.example.pocket_monsters.LoginActivity.Debug;

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

public class ItemsActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	
		SharedPreferences pref = getApplication().getSharedPreferences("pref",0); 
        String item_dump = pref.getString("item", "not found");
        String[] item_array = item_dump.split(";");
        
        Item[] inventory = new Item[item_array.length];
        int i = 0;
        for( String each_item : item_array ){
        	String[] attributes = each_item.split(",");
        	inventory[i] = new Item(Integer.parseInt(attributes[0]),
        			attributes[1],attributes[2],attributes[3]);
        	i++;
        }
        
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
