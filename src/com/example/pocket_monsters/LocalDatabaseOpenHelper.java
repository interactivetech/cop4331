package com.example.pocket_monsters;

import com.example.pocket_monsters.LoginActivity.Debug;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseOpenHelper extends SQLiteOpenHelper {

	SQLiteDatabase db;
	
	private static final int DATABASE_VERSION = 2;
	
	private static final String ITEM_TABLE_NAME = "items";
	private static final String[][] ITEM_ATTRIBUTES = {
		new String[]{"item_id","INTEGER PRIMARY KEY"},
		new String[]{"name","TEXT"},
		new String[]{"image","TEXT"},
		new String[]{"description","TEXT"},
		new String[]{"effect","TEXT"}
	};
	private static final String MONSTER_TABLE_NAME = "monsters";
	private static final String[][] MONSTER_ATTRIBUTES = {
		new String[]{"monster_id","INTEGER PRIMARY KEY"},
		new String[]{"name","TEXT"},
		new String[]{"image","TEXT"},
		new String[]{"description","TEXT"},
		new String[]{"attack","TEXT"}
	};
	private static final String ENCOUNTER_TABLE_NAME = "encounters";
	private static final String[][] ENCOUNTER_ATTRIBUTES = {
		new String[]{"encounter_id","INTEGER PRIMARY KEY"},
		new String[]{"location_id","INTEGER"},
		new String[]{"monster_id","INTEGER"},
		new String[]{"monster_lvl","INTEGER"}
	};
	private static final String LOCATION_TABLE_NAME = "locations";
	private static final String[][] LOCATION_ATTRIBUTES = {
		new String[]{"location_id","INTEGER PRIMARY KEY"},
		new String[]{"name","TEXT"},
		new String[]{"coordinates","TEXT"}
	};
	
	public LocalDatabaseOpenHelper(Context context){//, String name, CursorFactory factory, int version) {
		super(context, "local.db", null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createDatabase(db, ITEM_TABLE_NAME);
		createDatabase(db, ENCOUNTER_TABLE_NAME);
	    createDatabase(db, MONSTER_TABLE_NAME);
	    createDatabase(db, LOCATION_TABLE_NAME);
	}

	public void createDatabase(SQLiteDatabase db, String table_name){
	    //Debug.out("create");	    
	    String[][] attribute_array = null;
	    if( table_name.compareTo("items") == 0){
	    	attribute_array = ITEM_ATTRIBUTES;
	    }
	    else if( table_name.compareTo("monsters") == 0){
	    	attribute_array = MONSTER_ATTRIBUTES;
	    }
	    else if( table_name.compareTo("encounters") == 0){
	    	attribute_array = ENCOUNTER_ATTRIBUTES;
	    }

	    else if( table_name.compareTo("locations") == 0){
	    	attribute_array = LOCATION_ATTRIBUTES;
	    }
	     
	    String create_sql = "CREATE TABLE " + table_name + " (";
	    for ( String[] each : attribute_array){
	    	create_sql += each[0] + " " + each[1] + ", ";
	    }
	    create_sql = create_sql.substring(0,create_sql.length()-2);
	    create_sql += ");";
		db.execSQL(create_sql);
	}
	
	public void insert(String table, String[] attributes, String[] values){
		//Debug.out("insert");
		SQLiteDatabase write_db = this.getWritableDatabase();
		
		ContentValues newValues = new ContentValues();
		int array_length = attributes.length;
		for( int i = 0; i < array_length; i++){
			newValues.put(attributes[i], values[i]);
		}
		write_db.insert(table, null, newValues);
	}
	
	public Cursor select(Boolean distinct, String[] columns, String table, 
							String where, String[] whereArgs, String groupBy, 
							String having, String orderBy, String limit){
		SQLiteDatabase read_db = this.getReadableDatabase();
		
		Cursor cursor = read_db.query(distinct, table, columns, where, whereArgs, groupBy, having, orderBy, limit);
	    return cursor;
	}
	
	public int update(String table, String[] columns, String[] values, String whereClause, String[] whereArgs){
		//Debug.out("update");
		SQLiteDatabase write_db = this.getWritableDatabase();
		
		ContentValues updatedValues = new ContentValues();
		int array_length = columns.length;
		for( int i = 0; i < array_length; i++){
			updatedValues.put(columns[i], values[i]);
		}
		
		return write_db.update(table, updatedValues, whereClause, whereArgs);
	}
	
	public int delete(String table, String whereClause, String[] whereArgs){
		SQLiteDatabase read_db = this.getReadableDatabase();
		
		int result = read_db.delete(table, whereClause, whereArgs);
		return result;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    //Debug.out("upgrade");
		db.execSQL("DROP TABLE IF EXISTS '" + ITEM_TABLE_NAME + "';");
	    db.execSQL("DROP TABLE IF EXISTS '" + ENCOUNTER_TABLE_NAME + "';");
	    db.execSQL("DROP TABLE IF EXISTS '" + MONSTER_TABLE_NAME + "';");
	    db.execSQL("DROP TABLE IF EXISTS '" + LOCATION_TABLE_NAME + "';");
	    createDatabase(db, ITEM_TABLE_NAME);
	    createDatabase(db, ENCOUNTER_TABLE_NAME);
	    createDatabase(db, MONSTER_TABLE_NAME);
	    createDatabase(db, LOCATION_TABLE_NAME);
	}

}
