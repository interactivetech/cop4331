package com.example.pocket_monsters;

public class Monster {
	int id;
	String name;
	String location;
	String image;
	String attacks;
	
	
	public Monster(int id, String name, String location, String image, String attack){
		this.id = id;
		this.name = name;
		this.location = location;
		this.image = image;
		this.attacks = attack;
	}
}	
