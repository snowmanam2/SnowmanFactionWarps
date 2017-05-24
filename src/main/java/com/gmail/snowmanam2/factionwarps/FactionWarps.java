package com.gmail.snowmanam2.factionwarps;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class FactionWarps extends JavaPlugin{
	@Override
	public void onEnable() {
		Messages.loadMessages(this);
		Config.loadConfig(this);
		EngineHomes.init(this);
		EngineWarps.init(this);
		WarpsColl.get().init();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = null;
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command must be run by a player");
			return false;
		} else {
			p = (Player) sender;
		}
		
		
		if(cmd.getName().equalsIgnoreCase("home")) {
			if (args.length == 0) {
				List<String> homes = EssentialsWrapper.getHomes(p);
				if (homes.size() == 1) {
					EngineHomes.attemptTeleportHome(p, homes.get(0));
				} else {
					p.sendMessage(Messages.get("homeList", StringUtils.join(EssentialsWrapper.getHomes(p), ", ")));
				}
			} else {
				EngineHomes.attemptTeleportHome(p, args[0]);
			}
		} else if (cmd.getName().equalsIgnoreCase("homes")) {
			p.sendMessage(Messages.get("homeList", StringUtils.join(EssentialsWrapper.getHomes(p), ", ")));
		} else if (cmd.getName().equalsIgnoreCase("sethome")) {
			if (args.length == 1) {
				EngineHomes.attemptSetHome(p, args[0]);
			} else if (args.length == 0) {
				EngineHomes.attemptSetHome(p, null);
			} else {
				p.sendMessage(Messages.get("setHomeUsage"));
			}
		}
		return true;
	}
}
