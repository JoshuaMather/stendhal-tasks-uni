package games.stendhal.server.maps.deniran.river;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.item.FastFerryTicket;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class PortManagerNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "test_zone";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public PortManagerNPCTest() {
		setNpcNames("Fiete");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new PortManagerNPC(), ZONE_NAME);
	}
	
	/**
	 * Test for 'hi' and 'job'
	 */
	@Test
	public void testHiAndJob() {
				
		final SpeakerNPC npc = getNPC("Fiete");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		assertEquals(
				"Moin! How can I #help you?",
				getReply(npc));
		en.step(player, "job");
		assertEquals(
				"Me port manager. Busy job. Very important!", 
				getReply(npc));
	}
	
	@Test
	public void testNotAllowedOnFerryToAdosWithoutTicket() {
		final SpeakerNPC npc = getNPC("Fiete");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		en.step(player, "ados");
		assertEquals(
				"You must have ticket to travel to Ados.",
				getReply(npc));
	}
	
	@Test
	public void testAllowedOnFerryToDeniranWithTicket() {
		final SpeakerNPC npc = getNPC("Fiete");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		
		//FastFerryTicket fastFerryTicket = new FastFerryTicket("fast ferry ticket", "", "", new HashMap<String, String>());
		//final Item fastFerryTicket = SingletonRepository.getEntityManager().getItem("fast ferry ticket");
		FastFerryTicket fastFerryTicket = new FastFerryTicket(new HashMap<String, String>());	
		player.equip("bag", fastFerryTicket);
		
		en.step(player, "ados");
		assertEquals(
				"Thank you! Enjoy your trip!", getReply(npc));
	}
	
	@Test
	public void testSuccessfulTravelToAdos() {
		final SpeakerNPC npc = getNPC("Fiete");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		
		//FastFerryTicket fastFerryTicket = new FastFerryTicket("fast ferry ticket", "", "", new HashMap<String, String>());
		FastFerryTicket fastFerryTicket = new FastFerryTicket(new HashMap<String, String>());
		player.equip("bag", fastFerryTicket);
		
		en.step(player, "ados");

		int destX = 99;
		int destY = 48;
		String destZone = "0_ados_coast_s_w2";
		
		assertEquals(player.getX(), destX);
		assertEquals(player.getY(), destY);
		assertEquals(player.getZone(), destZone);
	}


}
