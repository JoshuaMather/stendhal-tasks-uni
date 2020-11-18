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
	
	/**
	 * Creates a wand that can be equipped in the left or right hand, and generates a heavy
	 * status to be applied.
	 * 
	 * The parameters derive from SingletonRepository's EntityManager generating an object.
	 * 
	 * @param name Wand name
	 * @param clazz Class name defined in XML
	 * @param subclass Specific subclass defined in XML
	 * @param attributes Attributes of the wand defined in XML
	 */
	public SluggishWand(String name, String clazz, String subclass, Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		ArrayList<String> slots = new ArrayList<>();
		slots.add("lhand");
		slots.add("rhand");
		initiateActiveSlotsList(slots);
	
		heavyStatus = StatusAttackerFactory.get("HeavyStatus,100");
	}
	
	/**
	 * When the wand is equipped, adds status attacker with the heavy status to the player
	 * 
	 * @return True if the status attacker is applied, false otherwise
	 */
	@Override
	protected boolean onActivate() {
		
		boolean active = this.isActivated();
		if (active) {
			return active;
		}
		
		// Gets the player who owns the slot where the wand is equipped
		SlotOwner sltOwner = this.getContainerBaseOwner();
		if (sltOwner instanceof RPEntity) {
			RPEntity owner = (RPEntity) sltOwner;
			// Adds a status attacker with the slowdown status if the equipper is a player
			owner.addStatusAttacker(heavyStatus);
			return true;
		}

		return false;
	}
	
	/**
	 * When the wand is unequipped, removes the status attacker with the heavy status from the player
	 * 
	 * @return False if the status attacker is removed, true otherwise
	 */
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
