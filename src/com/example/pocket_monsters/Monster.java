/*package com.example.pocket_monsters;

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
	

}	
*/



package com.example.pocket_monsters;
import java.util.Random;
import com.example.pocket_monsters.LoginActivity.Debug;
import android.database.Cursor;
//must call tempSet() when encounter starts or ends to set/reset temporary stats equal to normal stats
@SuppressWarnings("unused")
public class Monster {
public String name, bio, image, location;
public int id, level, exp, maxhp, curhp, str, agi, arm, tempstr, tempagi, temparm;
public int active; // 1 = in monsterdeck   0 = inactive
public static Cursor cursor;
//id = monster's number
//level = current level
//exp = current experience points once it reaches 500 + 500 * level reset and level up pokemon and increase stats
//maxhp = maximum health of monster
//curhp = current health of monster (damage done to it in battle)
//str = strength stat determines damage of attack ability
//agi = determines who attacks first/ avoidance (dodge chance)/ hit chance
//arm = armor of pokemon to mitigate damage each point of armor reduces damage by 1% up to 60%
//name = name of monster
//bio =  string that tells background of monster
//image = image of the monster not quite sure if string is the right type
//tempstr, tempagi, temparm = str,agi,arm respectively with added status effects and items these are to be used in formulas
// location = location of the monster

//class constructor
	public Monster (String name, String bio, String image, String location, int id, int level, int exp, int maxhp, int curhp, int str, int agi, int arm, int active) {
		this.name = name;
		this.id = id;
		this.bio = bio;
		this.image = image;
		this.level = level;
		this.exp = exp;
		this.maxhp=maxhp;
		this.curhp=curhp;
		this.str = str;
		this.agi = agi;
		this.arm = arm;
		this.tempstr = str;
		this.tempagi = agi;
		this.temparm = arm;
		this.location = location;
		this.active = active;
	
	}
	
	public static void setCursor(LocalDatabaseOpenHelper localData){
		cursor = localData.select(false, new String[]{"monster_id","name","image","description","location","level","exp","maxhp","curhp","str","agi","arm","active"},
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
			
			int location_index = cursor.getColumnIndexOrThrow("location");
			String location = cursor.getString(location_index);
			
			int level_index = cursor.getColumnIndexOrThrow("level");
			String level = cursor.getString(level_index);
			
			int exp_index = cursor.getColumnIndexOrThrow("exp");
			String exp = cursor.getString(exp_index);
			
			int maxhp_index = cursor.getColumnIndexOrThrow("maxhp");
			String maxhp = cursor.getString(maxhp_index);
			
			int curhp_index = cursor.getColumnIndexOrThrow("curhp");
			String curhp = cursor.getString(curhp_index);
			
			int str_index = cursor.getColumnIndexOrThrow("str");
			String str = cursor.getString(str_index);
			
			int agi_index = cursor.getColumnIndexOrThrow("agi");
			String agi = cursor.getString(agi_index);
			
			int arm_index = cursor.getColumnIndexOrThrow("arm");
			String arm = cursor.getString(arm_index);
			
			int active_index = cursor.getColumnIndexOrThrow("active");
			String active = cursor.getString(active_index);
			
			index[i] = new Monster(name, description, image, location, Integer.parseInt(monster_id),Integer.parseInt(level),Integer.parseInt(exp), Integer.parseInt(maxhp),Integer.parseInt(curhp),Integer.parseInt(str),Integer.parseInt(agi),Integer.parseInt(arm),Integer.parseInt(active) ); 
			i++; // (String name, String bio, String image, String location, int id, int level, int exp, int maxhp, int curhp, int str, int agi, int arm, int active)
		}
		return index;
	}
	
	
	public void tempSet() { // reset temporary stats to be called at the beginning or end of battle
		this.tempstr = this.str;
		this.tempagi = this.agi;
		this.temparm = this.arm;
	}
	
	public void gainExp (int amount) { // monster gains experience from item or defeating another monster
		this.exp = this.exp + amount;
		if(this.exp >= 500 + 500 * this.level) {
			this.exp = this.exp - (500 + 500 * this.level);
			this.increaseLevel();
	}
	}
	public void increaseLevel() { // simply increasing all stats by 1 for now
		this.level+=1;
		this.str+=1;
		this.agi+=1;
		this.arm+=1;
		this.tempstr+=1; // increment here incase of level up in battle
		this.tempagi+=1;
		this.temparm+=1;
		this.maxhp+=5;
		this.curhp = this.maxhp; // heals monster to full upon level up
		
	}
	// returns 0 if the monster dies
	// 1 if the monster is alive
	// 2 if the monster avoids the attack
	// to be used with output
	public int takeDamage(int amount) { // return true if alive, false if dead
		Random randomGenerator = new Random();
		int avoid = randomGenerator.nextInt(100);
		if(avoid <= this.tempagi*2) {
			return 2; //monster avoided the attack
		}
		this.curhp -= amount - amount * this.temparm/50;
		if(this.curhp <= 0) { //monster faints
			return 0; //dead
		}
		return 1; // alive
		
	}
	
	// 1 = increase experience
	// 2 = heal damage
	// 3 = temporarily increase agility for use in combat only
	// 4 = temporarily increase strength for use in combat only
	// 5 = temporarily increase armor for use in combat only
	// 6 = permanently increase agi
	// 7 = permanently increase str
	// 8 = permanently increase arm
	
	public void useItem(int stat, int amount) {
		if(stat == 1) { // increase exp
			this.gainExp(amount);
		}
		if(stat == 2) { // heal
			this.curhp += amount;
			if(this.curhp > this.maxhp)
				this.curhp = this.maxhp;
		}
		if(stat == 3) { // temp increase agi
			this.tempagi += amount;
		}
		if(stat == 4) { //temp increase strength
			this.tempstr += amount;
		}
		if(stat == 5) { // temp increase armor
			this.temparm += amount;
		}
		if(stat == 6) { // permanently increase agi
			this.agi += amount;
		}
		if(stat == 7) { // permanently increase str
			this.str += amount;
		}
		if(stat == 8) { // permanently increase armor
			this.arm += amount;
		}
	}
	
	public int attack() { // returns amount of damage this monster will deal
		return this.tempstr;
	}
}
