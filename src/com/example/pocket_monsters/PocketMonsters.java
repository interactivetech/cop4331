package com.example.pocket_monsters;

import com.example.pocket_monsters.LoginActivity.Debug;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

public class PocketMonsters extends Application{
	
	public SharedPreferences preferences; 
	public static LocalDatabaseOpenHelper db_helper;
	public static DatabaseConnection db_connection;
	
	public static Monster[] index;
	public static Encounter[] encounters;
	public static Item[] inventory;
	
	private static PocketMonsters singleton;
	
	public static PocketMonsters getInstance(){
		return singleton;
	}
	

	public DatabaseConnection getConnection() {
		return db_connection;
	}
	
	public LocalDatabaseOpenHelper getDB(){
		return db_helper;
	}
	
	
	public static Monster[] getIndex() {
		return index;
	}
	
	public static Encounter[] getEncounters() {
		return encounters;
	}
	
	public static Item[] getInventory() {
		return inventory;
	}
	
	@SuppressLint("CommitPrefEdits")
	public final void onCreate(){
		super.onCreate();
		
		db_helper = new LocalDatabaseOpenHelper(this);
		
		int location_num = 4;
		for( int i = 1; i <= location_num; i++){
			db_helper.insert("locations", new String[]{"location_id","name","coordinates"}, 
							new String[]{""+i,"location"+i,"coordinates"+i});
		}
		
		int monster_num = 9;
		for( int i = 1; i <= monster_num; i++){
			db_helper.insert("monsters", new String[]{"monster_id","name","image","attack"}, 
							new String[]{""+i,"monster"+i,"monsters"+((i%3)*5),"attack"+i});
		}
		
		int encounter_num = 5;
		for( int i = 1; i <= encounter_num; i++){
			int monster_id = (int) (Math.random()*(monster_num))+1;
			int location_id = (int) (Math.random()*(location_num))+1;
			int monster_lvl = (int) Math.random()*(20-5);
			db_helper.insert("encounters", new String[]{"encounter_id","monster_id","location_id","monster_lvl"}, 
												new String[]{""+i,""+monster_id,""+location_id,""+monster_lvl});
		}
		
		int item_num = 8;
		for( int i = 1; i <= item_num; i++){
			db_helper.insert("items", new String[]{"item_id","name","description","image","effect"}, 
							new String[]{""+i,"item"+i,"description"+i,"map","effect"+i});
		}
		
		singleton = this;
		
		index = Monster.fetchMonsters(db_helper);
		encounters = Encounter.fetchEncounters(db_helper);
		inventory = Item.fetchItems(db_helper);
		
		db_connection = new DatabaseConnection();
		
		Intent intent = new Intent(this, EncounterService.class);
		startService(intent);
		
	}
}