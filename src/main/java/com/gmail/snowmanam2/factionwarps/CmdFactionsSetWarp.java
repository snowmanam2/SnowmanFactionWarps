package com.gmail.snowmanam2.factionwarps;

import java.math.BigDecimal;

import org.bukkit.entity.Player;

import com.massivecraft.factions.cmd.FactionsCommand;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.arg.ARString;
import com.massivecraft.massivecore.ps.PS;

public class CmdFactionsSetWarp extends FactionsCommand {
	public CmdFactionsSetWarp() {
		this.addAliases("setwarp");
		this.addArg(ARString.get(), "name");
		this.addArg(ARString.get(), "password", "None");
		this.addArg(ARFaction.get(), "faction", "you");
		
	}
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		String name = this.readArg();
		String password = this.readArg("");
		Faction senderFaction = this.readArg(msenderFaction);
		Player p = msender.getPlayer();
		EconomyWrapper econ = new EconomyWrapper(p);
		
		PS location = PS.valueOf(p.getLocation());
		Faction boardFaction = BoardColl.get().getFactionAt(location);
		
		if (!boardFaction.equals(senderFaction)) {
			msender.msg(Messages.get("warps.invalidLocation", senderFaction.describeTo(msender)));
			return;
		}
		
		// MPerm
		if ( ! MPerm.get("setwarp").has(msender, senderFaction, true)) return;
		
		Warps warps = WarpsColl.get(senderFaction);
		
		int warpNumber = warps.getNumber();
		
		BigDecimal cost = Config.getNextWarpCost(warpNumber);
		BigDecimal availableMoney = econ.getMoney();
		if (availableMoney.compareTo(cost) >= 0) {
			Warp w = new Warp(PS.valueOf(p.getLocation()), password);
			warps.addWarp(name, w);
			msender.msg(Messages.get("warps.warpSet", name));
			
			if (cost.compareTo(new BigDecimal(0)) > 0) {
				econ.subtract(cost);
			}
		} else {
			msender.msg(Messages.get("insufficientFunds"));
		}
	}
}
