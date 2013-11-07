package com.example.pocket_monsters;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
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


// File usage
// stored in internal storage so the user cannot modify/cheat
// Monsterdeck.txt
// tempMonsterdeck.txt //to rewrite to file upon battle exit/ monster swap
// Capturedmonster.txt
// tempCapturedmonster.txt //to rewrite to file upon battle exit to save exp/health/newly captured monsters/ monster swap to change active boolean

public class Monsterdeck extends Activity {
	FileOutputStream outputStream;
	String filename = "Monsterdeck.txt", name, bio, image;
	public int length;
	int id, level, exp, maxhp, curhp, str, agi, arm;
	Monster[] monsterd;
	Boolean active;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences pref = getApplication().getSharedPreferences("pref",0); 

		//Monsterdeck md;
			//try {
			//	md = new Monsterdeck();
		Monster[] monsterd = new Monster[6]; 
      monsterd[0] = new Monster("pegasus", "bio",  "monsters1",5, 5, 5, 50,  50, 5, 5, 5);
      monsterd[1] = new Monster("rat", "bio",  "monsters10.png",6, 5, 5, 50,  50, 5, 5, 5);
      monsterd[2] = new Monster("dog", "bio",  "monsters5",7, 5, 5, 50,  50, 5, 5, 5);
      monsterd[3] = new Monster("drags", "bio",  "monsters1",8, 5, 5, 50,  50, 5, 5, 5);
      monsterd[4] = new Monster("legs", "bio",  "monsters1",9, 5, 5, 50,  50, 5, 5, 5);
      monsterd[5] = new Monster("knight", "bio",  "monsters1",17, 5, 5, 50,  50, 5, 5, 5);

        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
    	setContentView(R.layout.monsterdeck);
    	
    	final ListView indexList = (ListView) findViewById(R.id.monster_deck);
        final MonsterdeckListAdapter adapter = new MonsterdeckListAdapter(this, monsterd);
        indexList.setAdapter(adapter);
			/*} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        
	}
	
	public class MonsterdeckListAdapter extends BaseAdapter{
    	Monster[] index;
    	String monster_image;
    	Context context;
    	private LayoutInflater inflater = null;
    	
    	public MonsterdeckListAdapter(Context context, Monster[] index) {
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


    	// locate
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
					
					Intent myIntent = new Intent(Monsterdeck.this, MapActivity.class);
					Monsterdeck.this.startActivity(myIntent);
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
	public void Monsterdec() throws IOException {
		Monster[] monsterd = new Monster[6]; 
		//createfile();
	// Monster deck with 6 maximum monsters to be read in from file
	
	try {
    FileInputStream in = openFileInput(filename);	
    InputStreamReader inputStreamReader = new InputStreamReader(in);
    Scanner s = new Scanner(inputStreamReader);
    //BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    
	length = s.nextInt();
	s.nextLine(); //remove white space
	s.nextLine();
	for(int z =0; z<length; z++) { // populates monsterdeck[]
		name = s.nextLine();
		id = s.nextInt(); s.nextLine();
		level = s.nextInt(); s.nextLine();
		exp = s.nextInt(); s.nextLine();
		maxhp = s.nextInt(); s.nextLine();
		curhp = s.nextInt(); s.nextLine();
		str = s.nextInt(); s.nextLine();
		agi = s.nextInt(); s.nextLine();
		arm = s.nextInt(); s.nextLine();
		bio = s.nextLine();
		image = s.nextLine(); // example "squirrel.jpg" holds pathfile of the image
		s.nextLine(); // remove whitespace inbetween monsters
		monsterd[z] = new Monster (name, bio,  image,id, level, exp, maxhp,  curhp, str, agi, arm); // construct class into class array
		
	}
	s.close();
	}
    catch (FileNotFoundException e) {
        Log.e("login activity", "File not found: " + e.toString());
    }

	// add listener for button to create new Viewcaptured
	

		
	}
	public void createfile(){
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append("5");
		sb.append("\n\n");
	       FileOutputStream fOut;
		try {
			fOut = openFileOutput("Monsterdeck.txt",Context.MODE_PRIVATE);
		
	       //OutputStreamWriter osw = new OutputStreamWriter(fOut); 
	      
	    	   sb.append("pegasus\n10\n1\n0\n50\n50\n5\n5\n5\nlol what\nmonsters1\n\n");
	    	   sb.append("knight\n10\n1\n0\n50\n50\n5\n5\n5\nlol what\nmonsters10\n\n");
	    	   sb.append("dragon\n10\n1\n0\n50\n50\n5\n5\n5\nlol what\nmonsters5\n\n");
	    	   sb.append("giraffe\n10\n1\n0\n50\n50\n5\n5\n5\nlol what\nmonsters1\n\n");
	    	   sb.append("kitteh\n10\n1\n0\n50\n50\n5\n5\n5\nlol what\nmonsters1\n\n");
	      
	       fOut.write(sb.toString().getBytes());
	       fOut.close();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void battleChoice(int first, int second) { //swap two positions in monster deck
		Monster monster = this.monsterd[first]; //temp to hold
		this.monsterd[first] = this.monsterd[second];
		this.monsterd[second] = monster;
	}
	
	
	public void updateMonsterd(Monster monster[]) throws IOException { // updates Monsterdeck.txt
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(this.length);
		sb.append("\n\n");
		   @SuppressWarnings("unused")
		File file = new File(getFilesDir(), "temp.txt");
	       FileOutputStream fOut = openFileOutput("temp.txt",Context.MODE_PRIVATE);
	       //OutputStreamWriter osw = new OutputStreamWriter(fOut); 
	       for(int z = 0; z< this.length;z++) {
	    	   sb.append(monster[z].name + "\n" + monster[z].id + "\n" + monster[z].level + "\n" + monster[z].exp + "\n" + monster[z].maxhp + "\n" + monster[z].curhp + "\n" + monster[z].str + "\n" + monster[z].agi + "\n" + monster[z].arm + "\n" + monster[z].bio + "\n" + monster[z].image + "\n\n" );
	       }
	       fOut.write(sb.toString().getBytes());
	       fOut.close();
	       File oldfile = getFileStreamPath("temp.txt");
	       File newfile = getFileStreamPath("Monsterdeck.txt");
	       deleteFile("Monsterdeck.txt");
	       oldfile.renameTo(newfile);
	}
	
	public void swapMonster(Monster monster, int position) throws IOException {

		if(this.length >= 6) {
			updateCapt(monster, this.monsterd[position]); // updates captured monster deck changes boolean
			this.monsterd[position] = monster;
			updateMonsterd(this.monsterd);
		}
		else { // add to end of monsterdeck if the monsterdeck is not full
			this.length++;
			this.monsterd[length] = monster;
			updateMonsterd(this.monsterd);
			updateCapt(monster, this.monsterd[position]);
		}
	}
	// finds mon0 in captured mon and sets to active 0
	// finds mon1 in capturedmon and sets to inactive 1
	public void updateCapt(Monster mon1, Monster mon0) throws FileNotFoundException, IOException{
			StringBuilder sb = new StringBuilder();
			sb.append("");
			sb.append(this.length);
			sb.append("\n\n");
		   @SuppressWarnings("unused")
		   File file = new File(getFilesDir(), "tempcap.txt");
	       FileOutputStream fOut = openFileOutput("tempcap.txt",Context.MODE_PRIVATE);
		   FileInputStream in = openFileInput("Capturedmonsters.txt");	
		   InputStreamReader inputStreamReader = new InputStreamReader(in);
		   Scanner s = new Scanner(inputStreamReader);
		   int leng = s.nextInt(); 
		   s.nextLine(); s.nextLine(); // remove whitespace
		   Monster[] captmonster = new Monster[leng]; 
		   for(int z=0; z< leng; z++) {
				name = s.nextLine();
				id = s.nextInt(); s.nextLine();
				level = s.nextInt(); s.nextLine();
				exp = s.nextInt(); s.nextLine();
				maxhp = s.nextInt(); s.nextLine();
				curhp = s.nextInt(); s.nextLine();
				str = s.nextInt(); s.nextLine();
				agi = s.nextInt(); s.nextLine();
				arm = s.nextInt(); s.nextLine();
				bio = s.nextLine();
				image = s.nextLine(); // example "squirrel.jpg" holds pathfile of the image
				active = s.nextBoolean();
				s.nextLine(); s.nextLine(); // remove whitespace inbetween monsters
				captmonster[z] = new Monster (name, bio,  image,id, level, exp, maxhp,  curhp, str, agi, arm); // construct class into class array
				if(mon1.id == captmonster[z].id && mon1.level == captmonster[z].level && captmonster[z].str == mon1.str && captmonster[z].agi == mon1.agi) {
					captmonster[z].active = true; }
				else if(mon0.id == captmonster[z].id && mon0.level == captmonster[z].level && captmonster[z].str == mon0.str && captmonster[z].agi == mon1.agi) {
					captmonster[z].active = false;
				}
			   
		   }
	       for(int h = 0; h< leng;h++) {
	    	   sb.append(captmonster[h].name + "\n" + captmonster[h].id + "\n" + captmonster[h].level + "\n" + captmonster[h].exp + "\n" + captmonster[h].maxhp + "\n" + captmonster[h].curhp + "\n" + captmonster[h].str + "\n" + captmonster[h].agi + "\n" + captmonster[h].arm + "\n" + captmonster[h].bio + "\n" + captmonster[h].image + "\n" + captmonster[h].active + "\n\n" );
	       }
	       fOut.write(sb.toString().getBytes());
	       fOut.close();
	       File oldfile = getFileStreamPath("tempcap.txt");
	       File newfile = getFileStreamPath("Capturedmonsters.txt");
	       deleteFile("Capturedmonsters.txt");
	       oldfile.renameTo(newfile);
		   
	}

}	
/* StringBuilder sb = new StringBuilder();
String line;
while ((line = bufferedReader.readLine()) != null) {
    sb.append(line);
}

*/

	/*
	private void writeToFile(String data) {
	    try {
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
	        outputStreamWriter.write(data);
	        outputStreamWriter.close();
	    }
	    catch (IOException e) {
	        Log.e("Exception", "File write failed: " + e.toString());
	    } 
	}


	private String readFromFile() {

	    String ret = "";

	    try {
	        InputStream inputStream = openFileInput("Monsterdeck.txt");

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            ret = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	    }

	    return ret;
	}
	
*/	
	

