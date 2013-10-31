package com.example.pocket_monsters;

public class Encounter {
	int id;
	String location;
	String monster;
	int monster_level;
	String monster_pic;
	
	public Encounter(int id, String location, String monster, int monster_level, String monster_pic){
		this.id = id;
		this.location = location;
		this.monster = monster;
		this.monster_level = monster_level;
		this.monster_pic = monster_pic;
	}
}
