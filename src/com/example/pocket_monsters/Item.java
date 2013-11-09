package com.example.pocket_monsters;

import java.io.Serializable;

import android.database.Cursor;

public class Item implements Serializable{
	
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	String image;
	String description;
	String effect;
	
	public static Cursor cursor;
	
	public Item(int id, String name, String image, String description, String effect){
		this.id = id;
		this.name = name;
		this.image = image;
		this.description = description;
		this.effect = effect;
	}
	
	public static void setCounter(LocalDatabaseOpenHelper localData){
		cursor = localData.select(false, new String[]{"item_id","name","image","description","effect"},
				"items", null, null, null, null, null, null);
	}
	
	public static Item[] fetchItems(LocalDatabaseOpenHelper localData){
		setCounter(localData);
        Item[] inventory = new Item[cursor.getCount()];
        int i = 0;
        while( cursor.moveToNext() ){
        	int item_index = cursor.getColumnIndexOrThrow("item_id");
			String item_id = cursor.getString(item_index);
			
			int name_index = cursor.getColumnIndexOrThrow("name");
    		String name = cursor.getString(name_index);
    		
    		int image_index = cursor.getColumnIndexOrThrow("image");
    		String image = cursor.getString(image_index);
    		
    		int description_index = cursor.getColumnIndexOrThrow("description");
    		String description = cursor.getString(description_index);
			
			int effect_index = cursor.getColumnIndexOrThrow("effect");
			String effect = cursor.getString(effect_index);
			
			inventory[i] = new Item(Integer.parseInt(item_id), name, image, description, effect); 
			i++;
		}
        return inventory;
	}
}
