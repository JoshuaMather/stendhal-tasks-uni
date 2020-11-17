package games.stendhal.server.entity.mapstuff.spawner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.FlowerPot;
import games.stendhal.server.entity.mapstuff.area.AreaEntity;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPClass;
import utilities.PlayerTestHelper;
import utilities.RPClass.GrowingPassiveEntityRespawnPointTestHelper;

public class FlowerPotTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		if (!RPClass.hasRPClass("area")) {
			AreaEntity.generateRPClass();
		}
		GrowingPassiveEntityRespawnPointTestHelper.generateRPClasses();
	}
	
	/**
	 * Tests that the flowerpot exists when it is created.
	 */
	@Test
	public void testForInitialisingFlowerPot() {
		final FlowerPot flowerpot = (FlowerPot) SingletonRepository.getEntityManager().getItem("flowerpot");
		assertNotNull(flowerpot);
	}
	
	/**
	 * Tests that when the flowerpot is used, it places a VegetableGrower on the ground.
	 */
	@Test
	public void testForPlantingFlowerPotOnGround() {
		final FlowerPot flowerpot = (FlowerPot) SingletonRepository.getEntityManager().getItem("flowerpot");
		final Player player = PlayerTestHelper.createPlayer("bob");

		final StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);

		zone.add(flowerpot);
		flowerpot.setPosition(1, 0);

		assertTrue(flowerpot.onUsed(player));
		assertTrue(player.getZone().getEntityAt(1, 0) instanceof VegetableGrower);
	}
	
	/**
	 * Tests that the correct vegetable is placed into the flowerpot when it is used.
	 */
	@Test
	public void testForCorrectVegetableWithinFlowerPot() {
		final FlowerPot flowerpot = (FlowerPot) SingletonRepository.getEntityManager().getItem("flowerpot");
		final Player player = PlayerTestHelper.createPlayer("bob");
		final StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);
		zone.add(flowerpot);
		flowerpot.setPosition(1, 0);
		flowerpot.setInfoString("carrot");
		flowerpot.onUsed(player);
		VegetableGrower grower = (VegetableGrower) player.getZone().getEntityAt(1, 0);
		assertEquals(grower.describe(), "It looks like there's a carrot sprouting here.");
	}
	
	/**
	 * Tests when the vegetable grows to max ripeness that the flower pot displays the correct description.
	 */
	@Test
	public void testForCorrectVegetableWithinFlowerPotAtMaxRipeness() {
		final FlowerPot flowerpot = (FlowerPot) SingletonRepository.getEntityManager().getItem("flowerpot");
		final Player player = PlayerTestHelper.createPlayer("bob");
		final StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);
		zone.add(flowerpot);
		flowerpot.setInfoString("carrot");
		flowerpot.setPosition(1, 0);
		flowerpot.onUsed(player);
		VegetableGrower grower = (VegetableGrower) player.getZone().getEntityAt(1, 0);
		grower.setToFullGrowth();
		assertEquals(grower.describe(), "You see a carrot.");
	}
	
	/**
	 * Tests that when picked, the item goes into the player's inventory.
	 */
	@Test
	public void testForWhenPickedVegetableEntersPlayersInventoryWhenRipe() {
		final FlowerPot flowerpot = (FlowerPot) SingletonRepository.getEntityManager().getItem("flowerpot");
		final Player player = PlayerTestHelper.createPlayer("bob");
		final StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);
		zone.add(flowerpot);
		flowerpot.setInfoString("carrot");
		flowerpot.setPosition(1, 0);
		flowerpot.onUsed(player);
		VegetableGrower grower = (VegetableGrower) player.getZone().getEntityAt(1, 0);
		grower.setToFullGrowth();
		grower.onUsed(player);
		assertTrue(player.isEquipped("carrot"));
	}
	
	/**
	 * Tests that when an unripe flowerpot tries to be picked, the player doesn't get the item
	 */
	@Test
	public void testForWhenUnripeFlowerPotPickedVegetableDoesNotEnterPlayerInventory() {
		final FlowerPot flowerpot = (FlowerPot) SingletonRepository.getEntityManager().getItem("flowerpot");
		final Player player = PlayerTestHelper.createPlayer("bob");
		final StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);
		zone.add(flowerpot);
		flowerpot.setInfoString("carrot");
		flowerpot.setPosition(1, 0);
		flowerpot.onUsed(player);
		VegetableGrower grower = (VegetableGrower) player.getZone().getEntityAt(1, 0);
		grower.onUsed(player);
		assertFalse(player.isEquipped("carrot"));
	}
}	
