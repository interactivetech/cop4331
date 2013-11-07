package com.example.pocket_monsters;

public class Encounter {
	int id;
	int location_id;
	int monster_id;
	int monster_level;
	
	public Encounter(int id, int location_id, int monster_id, int monster_level){
		this.id = id;
		this.location_id = location_id;
		this.monster_id = monster_id;
		this.monster_level = monster_level;
	}
}
