package com.gmail.snowmanam2.factionwarps;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.CmdFactions;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.massivecore.util.MUtil;

public class EngineWarps implements Listener {
	public static EngineWarps i = new EngineWarps();
	public static EngineWarps get() {
		return i;
	}
	
	public static void init(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(EngineWarps.get(), plugin);
		registerCommands();
		registerPermissions();
	}
	
	public static void registerPermissions() {
		MPerm.getCreative(23002, "setwarp", "setwarp", "manage warps", MUtil.set(Rel.LEADER, Rel.OFFICER), false, true, true);
		MPerm.getCreative(23003, "warp", "warp", "teleport to warp", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER), false, true, true);
		MPerm.getCreative(23004, "warppassword", "warppassword", "show warp password", MUtil.set(Rel.LEADER), false, false, false);
	}
	
	public static void registerCommands() {
		Factions factionsPlugin = (Factions)Bukkit.getServer().getPluginManager().getPlugin("Factions");
		CmdFactions cmd = factionsPlugin.getOuterCmdFactions();
		cmd.addSubCommand(new CmdFactionsWarp());
		cmd.addSubCommand(new CmdFactionsSetWarp());
		cmd.addSubCommand(new CmdFactionsDelWarp());
		cmd.addSubCommand(new CmdFactionsListWarps());
	}
	
	@EventHandler
	public void onFactionDisband(EventFactionsDisband event) {
		WarpsColl.get().detachId(event.getFactionId());
	}
}
