package games.stendhal.server.maps.ados.coast;

import static org.junit.Assert.assertEquals;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.item.FastFerryTicket;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class FerryConveyerNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "test_zone";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public FerryConveyerNPCTest() {
		setNpcNames("Eliza");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new FerryConveyerNPC(), ZONE_NAME);
	}
	
	/**
	 * Test for 'hi' and 'job'
	 */
	@Test
	public void testHiAndJob() {
				
		final SpeakerNPC npc = getNPC("Eliza");
		final Engine en = npc.getEngine();
		
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
		final SpeakerNPC npc = getNPC("Eliza");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		en.step(player, "deniran");
		assertEquals(
				"You must have a ticket to travel to Deniran.",
				getReply(npc));
		
		
	}
	
	@Test
	public void testAllowedOnFerryToDeniranWithTicket() {
		final SpeakerNPC npc = getNPC("Eliza");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		
		//FastFerryTicket fastFerryTicket = new FastFerryTicket("fast ferry ticket", "", "", new HashMap<String, String>());
		FastFerryTicket fastFerryTicket = new FastFerryTicket(new HashMap<String, String>());
		player.equip("bag", fastFerryTicket);
		
		en.step(player, "deniran");
		assertEquals(
				"Thank you! Enjoy your trip!", getReply(npc));
	}
	
	@Test
	public void testSuccessfulTravelToDeniran() {
		final SpeakerNPC npc = getNPC("Eliza");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		
		//FastFerryTicket fastFerryTicket = new FastFerryTicket("fast ferry ticket", "", "", new HashMap<String, String>());
		FastFerryTicket fastFerryTicket = new FastFerryTicket(new HashMap<String, String>());
		player.equip("bag", fastFerryTicket);
			
		en.step(player, "deniran");

		int destX = 101;
		int destY = 48;
		String destZone = "0_deniran_river_s";
		
		assertEquals(player.getX(), destX);
		assertEquals(player.getY(), destY);
		assertEquals(player.getZone(), destZone);
	}
}
