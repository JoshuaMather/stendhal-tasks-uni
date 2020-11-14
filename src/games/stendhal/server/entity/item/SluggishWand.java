package games.stendhal.server.entity.item;

import java.util.ArrayList;
import java.util.Map;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.status.StatusAttacker;
import games.stendhal.server.entity.status.StatusAttackerFactory;
import marauroa.common.game.SlotOwner;

public class SluggishWand extends SlotActivatedItem{
	private StatusAttacker heavyStatus;
	private RPEntity equippedTo;
	

	public SluggishWand(String name, String clazz, String subclass, Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		ArrayList<String> slots = new ArrayList<>();
		slots.add("lhand");
		slots.add("rhand");
		initiateActiveSlotsList(slots);
	
		heavyStatus = StatusAttackerFactory.get("HeavyStatus,100");
	}
	
	@Override
	protected boolean onActivate() {
		
		boolean active = this.isActivated();
		if (active) {
			return active;
		}
		
		SlotOwner sltOwner = this.getContainerBaseOwner();
		if (sltOwner instanceof RPEntity) {
			RPEntity owner = (RPEntity) sltOwner;
			owner.addStatusAttacker(heavyStatus);
			return true;
		}

		return false;
	}
	
	@Override
	protected boolean onDeactivate() {
		
		boolean active = this.isActivated();
		if (!active) {
			return active;
		}
		
		SlotOwner sltOwner = this.getContainerBaseOwner();
		if (sltOwner instanceof RPEntity) {
			RPEntity owner = (RPEntity) sltOwner;
			owner.removeStatusAttacker(heavyStatus);	
			return false;
		}

		return true;
	}
}
