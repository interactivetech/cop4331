package com.example.pocket_monsters;

import android.database.Cursor;

public class Encounter {
	int id;
	int location_id;
	int monster_id;
	int monster_level;
	
	public static Cursor cursor;
	
	public Encounter(int id, int location_id, int monster_id, int monster_level){
		this.id = id;
		this.location_id = location_id;
		this.monster_id = monster_id;
		this.monster_level = monster_level;
	}
	
	public static void setCounter(LocalDatabaseOpenHelper localData){
		cursor = localData.select(false, new String[]{"encounter_id","location_id","monster_id","monster_lvl"},
				"encounters", null, null, null, null, null, null);
	}
	
	public static Encounter[] fetchEncounters(LocalDatabaseOpenHelper localData){
		setCounter(localData);
        Encounter[] encounters = new Encounter[cursor.getCount()];
        int i = 0;
        while( cursor.moveToNext() ){
        	int encounter_index = cursor.getColumnIndexOrThrow("encounter_id");
			String encounter_id = cursor.getString(encounter_index);
			
			int location_index = cursor.getColumnIndexOrThrow("location_id");
    		String location_id = cursor.getString(location_index);
    		
    		int monster_index = cursor.getColumnIndexOrThrow("monster_id");
    		String monster_id = cursor.getString(monster_index);
			
			int level_index = cursor.getColumnIndexOrThrow("monster_lvl");
			String monster_lvl = cursor.getString(level_index);
			
			encounters[i] = new Encounter(Integer.parseInt(encounter_id), Integer.parseInt(location_id),
										Integer.parseInt(monster_id), Integer.parseInt(monster_lvl)); 
			i++;
		}
        return encounters;
	}
}
