package com.example.pocket_monsters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import android.app.Activity;

public class Viewcaptured extends Activity {
	FileOutputStream outputStream;
	String filename = "Monsterdeck.txt", name, bio, image;
	public int length;
	int id, level, exp, maxhp, curhp, str, agi, arm;
	Boolean active;
	Monster[] captured;
	
	public Viewcaptured() throws FileNotFoundException {
		   FileInputStream in = openFileInput("Capturedmonsters.txt");	
		   InputStreamReader inputStreamReader = new InputStreamReader(in);
		   Scanner s = new Scanner(inputStreamReader);
		   int leng = s.nextInt(); 
		   s.nextLine(); s.nextLine(); // remove whitespace
		   Monster[] captmonster = new Monster[leng]; 
		   for(int z=0; z< leng; z++) {
				name = s.nextLine();
				id = s.nextInt(); s.nextLine();
				level = s.nextInt(); s.nextLine();
				exp = s.nextInt(); s.nextLine();
				maxhp = s.nextInt(); s.nextLine();
				curhp = s.nextInt(); s.nextLine();
				str = s.nextInt(); s.nextLine();
				agi = s.nextInt(); s.nextLine();
				arm = s.nextInt(); s.nextLine();
				bio = s.nextLine();
				image = s.nextLine(); // example "squirrel.jpg" holds pathfile of the image
				active = s.nextBoolean();
				s.nextLine(); s.nextLine(); // remove whitespace inbetween monsters
				captmonster[z] = new Monster (name, bio,  image,id, level, exp, maxhp,  curhp, str, agi, arm); // construct class into class array			   
		   }
		   // populate UI with list of monsters
	}
}
