package com.gmail.snowmanam2.factionwarps;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Entity;

public class Warp extends Entity<Warp> {
	private PS location;
	private String password;
	
	public Warp(PS location, String password) {
		this.location = location;
		this.password = password;
	}
	
	public boolean isValid(Faction faction) {
		return BoardColl.get().getFactionAt(location) == faction;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public PS getLocation() {
		return this.location;
	}
	
}
