package games.stendhal.server.maps.quests.houses;

import static games.stendhal.server.entity.npc.ConversationStates.QUEST_OFFERED;
import static games.stendhal.server.entity.npc.ConversationStates.QUESTION_3;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.mapstuff.chest.StoredChest;
import games.stendhal.server.entity.mapstuff.portal.HousePortal;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class ResellHouseActionTest {
	private HousePortal housePortal;
	private StoredChest chest;
	private HouseTax tax;
	private AdosHouseSeller seller;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PlayerTestHelper.generateNPCRPClasses();
		Chest.generateRPClass();
		Portal.generateRPClass();
		HousePortal.generateRPClass();
		MockStendlRPWorld.get();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		HouseUtilities.clearCache();
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}
	
	@Before
	public void setUp() {
		SingletonRepository.getNPCList().add(new SpeakerNPC("Mr Taxman"));
		tax = new HouseTax();
		seller = new AdosHouseSeller("bob", "nirvana", tax);
	}
	
	@After
	public void clearStored() {
		if (housePortal != null) {
			StendhalRPZone zone = housePortal.getZone();
			if (zone != null) {
				zone.remove(housePortal);
				housePortal = null;
			}
		}

		if (chest != null) {
			StendhalRPZone zone = chest.getZone();
			if (zone != null) {
				zone.remove(chest);
				chest = null;
			}
		}
	}

	@Test
	public void testEmptyingChestOnResell() {
		String zoneName = "0_ados_city_n";
		StendhalRPZone ados = new StendhalRPZone(zoneName);
		MockStendlRPWorld.get().addRPZone(ados);
		housePortal = new HousePortal("schnick bla 51");
		housePortal.setIdentifier("keep rpzone happy");
		housePortal.setDestination(zoneName, "schnick bla 51");
		ados.add(housePortal);
		chest = new StoredChest();
		ados.add(chest);
		HouseUtilities.clearCache();
		
		Engine en = seller.getEngine();
		// Set NPC to state where he allows player to buy house
		en.setCurrentState(QUEST_OFFERED);
		Player george = PlayerTestHelper.createPlayer("george");
		
		// Give money to afford house
		StackableItem money = (StackableItem) SingletonRepository.getEntityManager().getItem("money");
		money.setQuantity(120000);
		george.equipToInventoryOnly(money);
		
		// Buy house
		en.step(george, "51");
		
		// Check house contains welcome gifts
		assertEquals(3, chest.size());
		
		// Sell house back
		en.setCurrentState(QUESTION_3);
		en.step(george, "yes");
		
		int result = chest.size();
		
		// Check house chest is empty
		assertEquals(0, result);
	}
}
