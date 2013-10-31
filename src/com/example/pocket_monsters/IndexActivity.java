package com.example.pocket_monsters;

import com.example.pocket_monsters.ItemsActivity.ItemListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
    	String monster_image;
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
    		
    		this.monster_image = index[position].image;
    		
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
	    		        	Intent myIntent = new Intent(v.getContext(), InfoActivity.class);
	    		        	myIntent.putExtra("monster_img",monster_image);
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
