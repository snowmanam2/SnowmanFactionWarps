package com.gmail.snowmanam2.factionwarps;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.MStore;

public class WarpsColl extends Coll<Warps> {
	private static WarpsColl i = new WarpsColl();
	public static WarpsColl get() { return i; }
	private WarpsColl()
	{
		super("warps", Warps.class, MStore.getDb(), FactionWarps.getPlugin(FactionWarps.class));
	}
	
	public static Warps get(Faction f) {
		if (WarpsColl.get().containsId(f.getId())) {
			return WarpsColl.get().get(f.getId());
		} else {
			return WarpsColl.get().create(f.getId());
		}
	}
}
