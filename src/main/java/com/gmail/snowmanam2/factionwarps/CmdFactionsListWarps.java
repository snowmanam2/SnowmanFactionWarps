package com.gmail.snowmanam2.factionwarps;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.ArgSetting;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.util.Txt;

public class CmdFactionsListWarps extends FactionsCommand {
	public CmdFactionsListWarps() {
		this.addAliases("listwarps");
		this.addArg(ArgSetting.getPage());
		this.addArg(ARFaction.get(), "faction", "you");
		
		this.setDesc(Messages.get("commands.listwarps"));
		
	}
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		int page = this.readArg();
		Faction faction = this.readArg(msenderFaction);
		
		// MPerm
		if ( ! MPerm.get("warp").has(msender, faction, true)) return;
		
		boolean showPassword = MPerm.get("warppassword").has(msender, faction, false);
		
		Warps warps = WarpsColl.get(faction);
		
		message(Txt.getPage(warps.getListing(showPassword), page, "Warps for "+faction.describeTo(msender), this));

	}
}
