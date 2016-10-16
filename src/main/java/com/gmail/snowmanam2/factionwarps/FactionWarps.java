package com.gmail.snowmanam2.factionwarps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class FactionWarps extends JavaPlugin {
	@Override
	public void onEnable() {
		Messages.loadMessages(this);
		EngineHomes.init(this);
		EngineWarps.init(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("home")) {
			
		} else if (cmd.getName().equalsIgnoreCase("homes")) {
			
		}
		return false;
	}
}
