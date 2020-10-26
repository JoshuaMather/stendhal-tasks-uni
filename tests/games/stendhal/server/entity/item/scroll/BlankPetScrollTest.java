package games.stendhal.server.entity.item.scroll;

import static org.junit.Assert.*;

import org.junit.After;
//import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.actions.admin.SummonAction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.Cat;
//import games.stendhal.server.entity.creature.Pet;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.CatTestHelper;
//import utilities.RPClass.CatTestHelper;
import utilities.RPClass.ItemTestHelper;

public class BlankPetScrollTest {

	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();

		ItemTestHelper.generateRPClasses();
		Log4J.init();
		SummonAction.register();
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}
	
	@After
	public void tearDown() throws Exception {
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}
	

	/**
	 * Tests for useScroll
	 */
	@Test
	public void testUseScroll() {
		final StendhalRPZone zone = new StendhalRPZone("zone");
		MockStendlRPWorld.get().addRPZone(zone);
		
		CatTestHelper.generateRPClasses();
		
		final Cat pet = new Cat();
		zone.add(pet);
		final Player player = PlayerTestHelper.createPlayer("player1");
		zone.add(player);
		
		player.setPet(pet);
		
		
		final Item blankPetScroll = SingletonRepository.getEntityManager().getItem(
				"blank pet scroll");
		player.equipOrPutOnGround(blankPetScroll);
		
		final Item blankPetScroll1 = SingletonRepository.getEntityManager().getItem(
				"blank pet scroll");
		player.equipOrPutOnGround(blankPetScroll1);
		
		assertTrue(((BlankPetScroll) blankPetScroll).useScroll(player));
		assertFalse(((BlankPetScroll) blankPetScroll1).useScroll(player));
		
		// check scroll is still in inventory
		assertTrue(player.isEquipped("blank pet scroll"));
	}

}
