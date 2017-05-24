package com.gmail.snowmanam2.factionwarps;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.arg.ARString;
import com.massivecraft.massivecore.ps.PS;

public class CmdFactionsWarp extends FactionsCommand {
	public CmdFactionsWarp() {
		this.addAliases("warp");
		this.addArg(ARString.get(), "name");
		this.addArg(ARString.get(), "password", "");
		this.addArg(ARFaction.get(), "faction", "you");
		
	}
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		String name = this.readArg();
		String password = this.readArg("");
		Faction faction = this.readArg(msenderFaction);
				
		// MPerm
		if ( ! MPerm.get("warp").has(msender, faction, true)) return;
		
		Warps warps = WarpsColl.get(faction);
		Warp w = warps.getWarp(name);
		
		if (w != null) {
			if (w.isValid(faction)) {
				if (w.getPassword() == password) {
					EssentialsWrapper.teleportPlayer(msender.getPlayer(), w.getLocation().asBukkitLocation());
				} else {
					msender.msg(Messages.get("warps.incorrectPassword"));
				}
			} else {
				PS loc = w.getLocation();
				msender.msg(Messages.get("warps.removeInvalid", name, loc.getWorld(), loc.getBlockX(), loc.getBlockZ(), faction.describeTo(msender)));
				warps.removeWarp(name);
			}
		} else {
			msender.msg(Messages.get("warps.noExist"));
		}
	}
}
