package com.gmail.snowmanam2.factionwarps;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.arg.ARString;

public class CmdFactionsDelWarp extends FactionsCommand {
	public CmdFactionsDelWarp() {
		this.addAliases("delwarp");
		this.addArg(ARString.get(), "name");
		this.addArg(ARFaction.get(), "faction", "you");
		
		this.setDesc(Messages.get("commands.delwarp"));
		
	}
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		String name = this.readArg();
		Faction faction = this.readArg(msenderFaction);
		
		// MPerm
		if ( ! MPerm.get("setwarp").has(msender, faction, true)) return;
		
		Warps warps = WarpsColl.get(faction);
		
		if (warps.getWarp(name) == null) {
			msender.msg(Messages.get("warps.noExist"));
			return;
		}
		
		warps.removeWarp(name);
		msender.msg(Messages.get("warps.warpDeleted"));
	}
}
