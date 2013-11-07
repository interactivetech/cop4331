package com.example.pocket_monsters;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.content.SharedPreferences;


// File usage
// stored in internal storage so the user cannot modify/cheat
// Monsterdeck.txt
// tempMonsterdeck.txt //to rewrite to file upon battle exit/ monster swap
// Capturedmonster.txt
// tempCapturedmonster.txt //to rewrite to file upon battle exit to save exp/health/newly captured monsters/ monster swap to change active boolean

@SuppressLint("NewApi")
public class Monsterdeck extends Activity {
	//String name, bio, image;
	//public int length;
	//int id, level, exp, maxhp, curhp, str, agi, arm;
	Monster[] monsterd;
	
	//calls the local database
	//static LocalDatabaseOpenHelper localData;
	//UI components
    private ListView monsterDeckListView;
    @SuppressWarnings("rawtypes")
	private ArrayAdapter arrayAdapter;
    
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	   
		setContentView(R.layout.monster_deck_list); //monster_deck_list
		
		/*localData = ((PocketMonsters) getApplication()).getDB();
	    Cursor cursor = localData.select(false, new String[]{"monster_id","name","image","bio","location","level","exp","maxhp","curhp","str","agi","arm"},
	    								"monsterdeck", null, null, null, null, null, null);
	*/
		//SharedPreferences pref = getApplication().getSharedPreferences("pref",0); 
		
		Monster[] monsterd = new Monster[6]; 
		monsterd[0] = new Monster("name", "bio", "image", "location",10,1,0,30,30,5,5,5);
		monsterd[1] = new Monster("derp", "bio", "image", "location",10,1,0,30,30,5,5,5);

      /* int i = 0;
        while( cursor.moveToNext() && i < 6 ){ // populates monster deck
        	int id_index = cursor.getColumnIndexOrThrow("monster_id");
    		String monster_id = cursor.getString(id_index);
    			
			int name_index = cursor.getColumnIndexOrThrow("name");
			String name = cursor.getString(name_index);
			
			int image_index = cursor.getColumnIndexOrThrow("image");
			String image = cursor.getString(image_index);
			
    		int bio_index = cursor.getColumnIndexOrThrow("bio");
    		String bio = cursor.getString(bio_index);
			
			int location_index = cursor.getColumnIndexOrThrow("location");
			String location = cursor.getString(location_index);
			
			int level_index = cursor.getColumnIndexOrThrow("level"); // "level","exp","maxhp","curhp","str","agi","arm"
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
			
			//(String name, String bio, String image, String location, int id, int level, int exp, int maxhp, int curhp, int str, int agi, int arm)
			monsterd[i] = new Monster(name, bio, image, location, Integer.parseInt(monster_id), Integer.parseInt(level), Integer.parseInt(exp), Integer.parseInt(maxhp), Integer.parseInt(curhp), Integer.parseInt(str), Integer.parseInt(agi), Integer.parseInt(arm)); 
			i++;
		}*/
        monsterDeckListView = (ListView) findViewById(R.id.monsterdeck_list);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, monsterd);
        monsterDeckListView.setAdapter(arrayAdapter);
 	

	}
	

//	public void battleChoice(int first, int second) { //swap two positions in monster deck
	//	Monster monster = this.monsterd[first]; //temp to hold
		//this.monsterd[first] = this.monsterd[second];
		//this.monsterd[second] = monster;
//	}
	
	
}
	

