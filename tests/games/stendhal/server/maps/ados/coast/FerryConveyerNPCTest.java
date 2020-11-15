package games.stendhal.server.maps.ados.coast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.FastFerryTicket;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.deniran.river.PortManagerNPC;
import utilities.PlayerTestHelper;

public class FerryConveyerNPCTest {

	private static SpeakerNPC npc = null;
	private static Engine en = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		
		final StendhalRPZone adoscoast = new StendhalRPZone("0_ados_coast_s_w2",100,100);
		SingletonRepository.getRPWorld().addRPZone(adoscoast);
		final StendhalRPZone deniranriver = new StendhalRPZone("0_deniran_river_s",100,100);
		SingletonRepository.getRPWorld().addRPZone(deniranriver);
				
		new FerryConveyerNPC().configureZone(adoscoast, null);
		new PortManagerNPC().configureZone(deniranriver, null);
		
		npc = SingletonRepository.getNPCList().get("Eliza");
		en = npc.getEngine();
	}
	
	/**
	 * Test for 'hi' and 'job'
	 */
	@Test
	public void testHiAndJob() {
				
		Player player = PlayerTestHelper.createPlayer("player");
		
		en.step(player, "hi");
		assertEquals(
				"Welcome to the #ferry service to #Athor #island! How can I #help you?",
				getReply(npc));
		en.step(player, "job");
		assertEquals(
				"If passengers want to #board the #ferry to #Athor #island, I take them to the ship with this rowing boat.", 
				getReply(npc));
	}
	
	@Test
	public void testNotAllowedOnFerryToDeniranWithoutTicket() {
		Player player = PlayerTestHelper.createPlayer("player");
		en.step(player, "hi");
		en.step(player, "deniran");
		assertFalse(player.isEquipped("fast ferry ticket"));
		assertEquals(
				"You must have a ticket to travel to Deniran.",
				getReply(npc));
		
		
	}
	Player player = PlayerTestHelper.createPlayer("player");
	@Test
	public void testAllowedOnFerryToDeniranWithTicket() {
		Player player = PlayerTestHelper.createPlayer("player");
		
		en.step(player, "hi");
		SpeakerNPC npc1 = SingletonRepository.getNPCList().get("Fiete");
		System.out.println(npc1.getY());
		
		FastFerryTicket fastFerryTicket = new FastFerryTicket(new HashMap<String, String>());
		player.equip("bag", fastFerryTicket);
		
		en.step(player, "deniran");
		assertEquals(
				"Thank you! Enjoy your trip!", getReply(npc));
		int destX = 99;
		int destY = 48;
		StendhalRPZone destZone = npc1.getZone();
	
		assertEquals(player.getX(), destX);
		assertEquals(player.getY(), destY);
		assertEquals(player.getZone(), destZone);
	}
	
}
