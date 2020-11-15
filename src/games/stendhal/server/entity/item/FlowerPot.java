package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.mapstuff.spawner.VegetableGrower;


public class FlowerPot extends StackableItem{

	public FlowerPot(String name, String clazz, String subclass, Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		
	}
	
	@Override
	public boolean onUsed(final RPEntity user) {
		if (!this.isContained()) {
			// the pot is on the ground, but not next to the player
			if (!this.nextTo(user)) {
				user.sendPrivateText("The " + this.getName() + " is too far away");
				return false;
			}

			// the infostring of the seed stores what it should grow
			final String infostring = this.getInfoString();
			VegetableGrower vegetableGrower;
			// choose the default flower grower if there is none set
			if (infostring == null) {
				vegetableGrower = new VegetableGrower("carrot",3);
			} else {
				vegetableGrower = new VegetableGrower(this.getInfoString(),3);
			}
			user.getZone().add(vegetableGrower);
			// add the VegetableGrower where the pot was on the ground
			vegetableGrower.setPosition(this.getX(), this.getY());
			// The first stage of growth happens almost immediately
			TurnNotifier.get().notifyInTurns(3, vegetableGrower);
			// remove the seed now that it is planted
			this.removeOne();
			return true;
		}
		// the pot was 'contained' in a slot and so it cannot be planted
		user.sendPrivateText("You have to put the " + this.getName() + " on the ground to grow something in it, silly!");
		return false;
	}
	
	


}
	
