package com.example.pocket_monsters;

public class Monster {
	int id;
	String name;
	String image;
	String description;
	String attacks;
	
	
	public Monster(int id, String name, String image, String description, String attack){
		this.id = id;
		this.name = name;
		this.image = image;
		this.description = description;
		this.attacks = attack;
	}
}	
