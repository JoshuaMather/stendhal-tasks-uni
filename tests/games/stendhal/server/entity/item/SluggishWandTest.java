package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.StatusType;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;

public class SluggishWandTest {
	private StendhalRPZone zone;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		new DatabaseFactory().initializeDatabase();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		PlayerTestHelper.removeAllPlayers();
	}
	
	@Before
	public void setUp() throws Exception {
		zone = new StendhalRPZone("zone", 20, 20);
		zone.protectionMap.init(1, 1);
		MockStendlRPWorld.get().addRPZone(zone);
	}

	@After
	public void tearDown() throws Exception {
		MockStendlRPWorld.get().removeZone(zone);

	}
	
	@Test
	public void shouldApplyHeavyStatusToCreatureOnAttack() {
		
		// Set up the fixture
		final Player player = PlayerTestHelper.createPlayer("hyde");
		final Item wand = SingletonRepository.getEntityManager().getItem("sluggish_wand");
		Creature victim = SingletonRepository.getEntityManager().getCreature("mouse");
		
		// Equip sluggish wand to player
		player.equip("lhand", wand);
		
		// Add target to zone
		zone.add(victim);
		
		// Check target doesn't already have HEAVY status
		assertFalse(victim.hasStatus(StatusType.HEAVY));

		// Get player to attack target
		StendhalRPAction.startAttack(player, victim);
		player.stopAttack();
		
		// Check target now has HEAVY status after attack
		assertTrue(victim.hasStatus(StatusType.HEAVY));
	}
	
	@Test
	public void shouldReduceBaseSpeedOfCreatureOnAttack() {
		
		// Set up the fixture
		final Player player = PlayerTestHelper.createPlayer("hyde");
		final Item wand = SingletonRepository.getEntityManager().getItem("sluggish_wand");
		Creature victim = SingletonRepository.getEntityManager().getCreature("mouse");
		
		// Equip sluggish wand to player
		player.equip("lhand", wand);
		
		// Add target to zone
		zone.add(victim);
		
		// Get initial values
		double initBaseSpeed = victim.getBaseSpeed();

		// Get player to attack target
		StendhalRPAction.startAttack(player, victim);
		player.stopAttack();
		
		// Check target is now slower than it was before attack
		assertEquals(initBaseSpeed/2, victim.getBaseSpeed(), 0.001);
	}
	
	@Test
	public void shouldReduceBaseSpeedOfPlayerOnAttack() {
		// Set up the fixture
		final Player player = PlayerTestHelper.createPlayer("hyde");
		final Player victim = PlayerTestHelper.createPlayer("bob");
		final Item wand = SingletonRepository.getEntityManager().getItem("sluggish_wand");
		
		// Equip sluggish wand to player
		player.equip("lhand", wand);
		
		// Add target and player to zone
		zone.add(victim);
		zone.add(player);
		
		// Get initial values
		double initBaseSpeed = victim.getBaseSpeed();

		// Get player to attack target
		StendhalRPAction.startAttack(player, victim);
		player.stopAttack();
		
		// Check target is now slower than it was before attack
		assertEquals(initBaseSpeed/2, victim.getBaseSpeed(), 0.001);
	}

}
