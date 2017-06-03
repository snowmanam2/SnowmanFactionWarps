package com.gmail.snowmanam2.factionwarps;

import java.util.ArrayList;
import java.util.List;

import com.massivecraft.massivecore.collections.MassiveMapDef;
import com.massivecraft.massivecore.store.Entity;

public class Warps extends Entity<Warps> {
	private MassiveMapDef<String, Warp> warps = new MassiveMapDef<String, Warp>();
	
	public void addWarp(String key, Warp newWarp) {
		warps.put(key, newWarp);
	}
	
	public Warp getWarp(String key) {
		return warps.get(key);
	}
	
	public void removeWarp(String key) {
		warps.remove(key);
	}
	
	public int getNumber() {
		return warps.size();
	}
	
	public List<String> getListing(boolean showPassword) {
		List<String> warpList = new ArrayList<String>();
		
		for (String key: warps.keySet()) {
			Warp w = warps.get(key);
			
			String password = w.getPassword();
			
			String desc;
			if (password.equals("")) {
				desc = Messages.get("warps.listItem", key);
			} else {
				if (!showPassword) password = Messages.get("warps.hiddenPassword");
				
				desc = Messages.get("warps.listItemPassword", key, password);
			}
			warpList.add(desc);
		}
		
		return warpList;
	}
	
}
