package com.example.pocket_monsters;

import java.io.Serializable;

public class Item implements Serializable{
	
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	String image;
	String description;
	String effect;
	
	public Item(int id, String name, String image, String description, String effect){
		this.id = id;
		this.name = name;
		this.image = image;
		this.description = description;
		this.effect = effect;
	}
}
