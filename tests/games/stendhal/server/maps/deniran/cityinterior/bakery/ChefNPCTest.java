package games.stendhal.server.maps.deniran.cityinterior.bakery;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class ChefNPCTest  extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "test_zone";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public ChefNPCTest() {
		setNpcNames("Patrick");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new ChefNPC(), ZONE_NAME);
	}
	
	/**
	 * Test for 'hi' and 'job'
	 */
	@Test
	public void testHiAndJob() {
				
		final SpeakerNPC npc = getNPC("Patrick");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		assertEquals(
				"Hello and welcome to Deniran Bakery.",
				getReply(npc));
		en.step(player, "job");
		assertEquals("I run Deniran Bakery.", getReply(npc));
	}


}
