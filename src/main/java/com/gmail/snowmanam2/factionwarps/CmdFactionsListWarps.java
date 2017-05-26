package com.gmail.snowmanam2.factionwarps;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;

public class CmdFactionsListWarps extends FactionsCommand {
	public CmdFactionsListWarps() {
		this.addAliases("listwarps");
		this.addArg(ARFaction.get(), "faction", "you");
		
		this.setDesc(Messages.get("commands.listwarps"));
		
	}
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		Faction faction = this.readArg(msenderFaction);
		
		// MPerm
		if ( ! MPerm.get("warp").has(msender, faction, true)) return;
		
		Warps warps = WarpsColl.get(faction);
		
		if ( MPerm.get("setwarp").has(msender, faction, false)) {
			
			// Tell sender the cost of the next warp, but only if they actually *can* set a warp
			int warpNumber = warps.getNumber();
			msender.msg(Messages.get("warps.listNextCost", EconomyWrapper.formatMoney(Config.getNextWarpCost(warpNumber))));
		}
		
		msender.msg(Messages.get("warps.listHeader", faction.describeTo(msender)));
		
		boolean showPassword = MPerm.get("warppassword").has(msender, faction, false);
		msender.msg(warps.getListing(showPassword));
	}
}
