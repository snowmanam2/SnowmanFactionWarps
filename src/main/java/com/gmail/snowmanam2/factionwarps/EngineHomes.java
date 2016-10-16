package com.gmail.snowmanam2.factionwarps;

import java.text.MessageFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

public class EngineHomes implements Listener {
	public static EngineHomes i = new EngineHomes();
	public static EngineHomes get() {
		return i;
	}
	
	public static void init(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(EngineHomes.get(), plugin);
		registerPermissions();
	}
	
	public static void registerPermissions() {
		MPerm.getCreative(23001, "userhome", "userhome", "teleport home", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY), false, true, true);
	}
	
	public boolean attemptTeleportHome(User user, String home) {
		Location homeLoc = null;
		
		try {
			homeLoc = user.getHome(home);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (home == null) {
			return true;
		}
		
		MPlayer mp = MPlayer.get(user.getBase());
		PS psChunk = PS.valueOf(homeLoc);
		Faction f = BoardColl.get().getFactionAt(psChunk);
		
		MPerm userhome = MPerm.get("userhome");
		
		if (!userhome.has(mp, f, false)) {
			String line2 = MessageFormat.format("teleport to your home at {0} {1}, {2}", 
					homeLoc.getWorld().getName(), homeLoc.getBlockX(), homeLoc.getBlockZ());
			String msg = Txt.parse("%s<b> does not allow you to", f.describeTo(mp, true));
			user.sendMessage(msg+"\n"+line2);
		}
		
		return true;
	}
	
	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
		boolean cancel = false;
		
		String command = e.getMessage().toLowerCase();
		String[] args = command.split("\\s+");
		Player p = e.getPlayer();
		if (args[0].startsWith("/home ") || command.startsWith("/homes ")) {
			Essentials ess = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
			User user = ess.getUser(p);
			List<String> homes = user.getHomes();
			if (args.length < 2 && homes.size() == 1) {
				cancel = attemptTeleportHome(user, homes.get(0));
			} else if (args.length >= 2) {
				if (homes.contains(args[1])) {
					cancel = attemptTeleportHome(user, args[1]);
				}
			}
		} else if (command.startsWith("/sethome")) {
			MPlayer mp = MPlayer.get(p);
			PS psChunk = PS.valueOf(p.getLocation());
			Faction f = BoardColl.get().getFactionAt(psChunk);
			
			MPerm userhome = MPerm.get("userhome");
			
			if (!userhome.has(mp, f, false)) {
				String msg = Txt.parse("%s<b> does not allow you to %s<b>.", f.describeTo(mp, true), "set home");
				p.sendMessage(msg);
				cancel = true;
			}
			
		}
		
		e.setCancelled(cancel);
	}
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		Location location = p.getLocation();
		MPlayer mp = MPlayer.get(p);
		PS psChunk = PS.valueOf(location);
		Faction f = BoardColl.get().getFactionAt(psChunk);
		
		MPerm userhome = MPerm.get("userhome");
		
		if (!userhome.has(mp, f, false)) {
			p.teleport(location.getWorld().getSpawnLocation(), TeleportCause.PLUGIN);
		}
	}
}
