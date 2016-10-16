package com.gmail.snowmanam2.factionwarps;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.util.MUtil;

public class EngineWarps implements Listener {
	public static EngineWarps i = new EngineWarps();
	public static EngineWarps get() {
		return i;
	}
	
	public static void init(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(EngineWarps.get(), plugin);
		registerPermissions();
	}
	
	public static void registerPermissions() {
		MPerm.getCreative(23002, "setwarp", "setwarp", "set warp", MUtil.set(Rel.LEADER, Rel.OFFICER), false, true, true);
		MPerm.getCreative(23003, "warp", "warp", "teleport to warp", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER), false, true, true);
	}
}
