package com.example.pocket_monsters;

import com.example.pocket_monsters.LoginActivity.Debug;

import android.database.Cursor;

public class Monster {
	int id;
	String name;
	String image;
	String description;
	String attacks;
	
	public static Cursor cursor;
	
	public Monster(int id, String name, String image, String description, String attack){
		this.id = id;
		this.name = name;
		this.image = image;
		this.description = description;
		this.attacks = attack;
	}
	
	public static void setCursor(LocalDatabaseOpenHelper localData){
		cursor = localData.select(false, new String[]{"monster_id","name","image","description","attack"},
				"monsters", null, null, null, null, null, null);
	}
	
	public static Monster[] fetchMonsters(LocalDatabaseOpenHelper localData){
		setCursor(localData);
		Monster[] index = new Monster[cursor.getCount()];
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
			
			int attack_index = cursor.getColumnIndexOrThrow("attack");
			String attack = cursor.getString(attack_index);
			
			index[i] = new Monster(Integer.parseInt(monster_id), name, description, image, attack); 
			i++;
		}
		return index;
	}
}	
