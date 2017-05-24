package com.gmail.snowmanam2.factionwarps;

import java.math.BigDecimal;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	private static FileConfiguration config = null;
	
	private Config() {
		
	}
	
	public static void loadConfig(JavaPlugin plugin) {
		config = plugin.getConfig();
	}
	
	public static BigDecimal getNextWarpCost (int newWarpNumber) {
		if (!config.contains("warpCosts")) {
			return new BigDecimal(0);
		}
		List<Float> costs = config.getFloatList("warpCosts");
		
		if (costs.size() == 0) {
			return new BigDecimal(0);
		}
		
		if (newWarpNumber >= costs.size()) newWarpNumber = costs.size()-1;
		
		return new BigDecimal(costs.get(newWarpNumber));	
	}
}
