package com.example.pocket_monsters;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

public class PocketMonsters extends Application{
	
	public SharedPreferences preferences; 
	
	private static PocketMonsters singleton;
	
	public static PocketMonsters getInstance(){
		return singleton;
	}
	
	@SuppressLint("CommitPrefEdits")
	public final void onCreate(){
		super.onCreate();
		preferences = getSharedPreferences("pref",0);
		SharedPreferences.Editor editor = preferences.edit();
		
		String items = "";
		int item_num = 8;
		for( int i=1; i<=item_num; i++){
			items += i + ",item"+i +",description"+i + ",effect"+i+";";
		}
		editor.putString("item", items);
		
		String monsters = "1,monster1,@drawable/monsters1.png,attack;"
						+ "2,monster2,@drawable/monsters5.png,attack;"
						+ "3,monster3,@drawable/monsters10.png,attack;"
						+ "4,monster4,@drawable/monsters1.png,attack;"
						+ "5,monster5,@drawable/monsters5.png,attack;"
						+ "6,monster6,@drawable/monsters10.png,attack;";
		editor.putString("monster", monsters);
		
		String[] monster_array = monsters.split(";");
		Monster[] monster_index = new Monster[monster_array.length];
		int monster_num = 0;
		for( String each_monster : monster_array ){
			String[] attributes = each_monster.split(",");
			monster_index[monster_num] = new Monster(
					Integer.parseInt(attributes[0]),
					attributes[1],attributes[2],attributes[3]);
			monster_num++;
		}
		
		String encounters = "";
		int encounter_num = 5;
		for( int i=1; i<=encounter_num; i++){
			int level = (int) (5 + (Math.random() * (20 - 5)));
			encounters += i + ","+i + ","+i%6 +","+level + ","+monster_index[i%6].image+";";
		}
		editor.putString("encounter", encounters);
		
		editor.commit();
		singleton = this;
	}
}