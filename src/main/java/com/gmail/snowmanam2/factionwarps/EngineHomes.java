package com.gmail.snowmanam2.factionwarps;

import java.text.MessageFormat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

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
		EssentialsWrapper.init();
		registerPermissions();
	}
	
	public static void registerPermissions() {
		MPerm.getCreative(23001, "userhome", "userhome", "teleport home", MUtil.set(Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY), false, true, true);
	}
	
	public static boolean canSetHome(Player p) {
		MPlayer mp = MPlayer.get(p);
		PS psChunk = PS.valueOf(p.getLocation());
		Faction f = BoardColl.get().getFactionAt(psChunk);
		
		MPerm userhome = MPerm.get("userhome");
		
		return userhome.has(mp, f, false);
	}
	
	public static void attemptSetHome(Player p, String home) {
		MPlayer mp = MPlayer.get(p);
		PS psChunk = PS.valueOf(p.getLocation());
		Faction f = BoardColl.get().getFactionAt(psChunk);
		MPerm userhome = MPerm.get("userhome");
		
		if (!userhome.has(mp, f, false)) {
			String msg = Txt.parse("%s<b> does not allow you to set home", f.describeTo(mp, true));
			p.sendMessage(msg);
		} else {
			EssentialsWrapper.setHome(p, home);
			p.sendMessage(Messages.get("homeSet"));
		}
		
	}
	
	public static void attemptTeleportHome(Player p, String home) {
		
		Location homeLoc = EssentialsWrapper.getHome(p, home);

		if (homeLoc == null) {
			p.sendMessage(Messages.get("invalidHome", home));
			return;
		}
		
		MPlayer mp = MPlayer.get(p);
		PS psChunk = PS.valueOf(homeLoc);
		Faction f = BoardColl.get().getFactionAt(psChunk);
		
		MPerm userhome = MPerm.get("userhome");
		
		if (!userhome.has(mp, f, false)) {
			String line2 = MessageFormat.format("teleport to your home at {0} {1}, {2}", 
					homeLoc.getWorld().getName(), homeLoc.getBlockX(), homeLoc.getBlockZ());
			String msg = Txt.parse("%s<b> does not allow you to", f.describeTo(mp, true));
			p.sendMessage(msg+"\n"+line2);
		} else {
			EssentialsWrapper.goHome(p, home);
		}
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
