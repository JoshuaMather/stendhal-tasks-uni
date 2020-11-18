package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.EquipActionConsts;
import games.stendhal.server.actions.equip.EquipAction;
import games.stendhal.server.actions.equip.EquipmentAction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.StatusType;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPAction;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;

public class SluggishWandTest {
	private StendhalRPZone zone;
	private String playername = "player";
	private Player player;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new DatabaseFactory().initializeDatabase();
		MockStendlRPWorld.get();
	}

	@After
	public void tearDown() throws Exception {
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}

	@Before
	public void setUp() throws Exception {
		zone = new StendhalRPZone("the zone where the corpse shall be slain");
		player = PlayerTestHelper.createPlayer(playername);
		zone.add(player);
	}

	/**
	 * Tests if the heavy status is applied to a creature when attacked by the wand
	 */
	@Test
	public void testForShouldApplyHeavyStatusToCreatureOnAttack() {
		
		
		// Set up the fixture
		final Item wand = SingletonRepository.getEntityManager().getItem("sluggish wand");
		Creature victim = SingletonRepository.getEntityManager().getCreature("mouse");
		Item arrow = SingletonRepository.getEntityManager().getItem("wooden arrow");
		
		// Add target to zone
		zone.add(victim);
		
		// Equip sluggish wand to player
		player.equip("bag", wand);
		player.equip("lhand", arrow);
		final EquipmentAction action = new EquipAction();
		RPAction equip = new RPAction();
		equip.put("type", "equip");
		equip.put(EquipActionConsts.BASE_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.BASE_SLOT, "bag");
		equip.put(EquipActionConsts.BASE_ITEM, wand.getID().getObjectID());

		equip.put(EquipActionConsts.TARGET_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.TARGET_SLOT, "rhand");

		action.onAction(player, equip);
		
		// Check target doesn't already have HEAVY status
		assertFalse(victim.hasStatus(StatusType.HEAVY));

		// Get player to attack target
		player.setTarget(victim);
		player.attack();
		
		// Check target now has HEAVY status after attack
		assertTrue(victim.hasStatus(StatusType.HEAVY));
	}
	
	/**
	 * Tests if the speed of the creature is reduced when attacked by the wand
	 */
	@Test
	public void testForShouldReduceBaseSpeedOfCreatureOnAttack() {
		
		// Set up the fixture
		final Item wand = SingletonRepository.getEntityManager().getItem("sluggish wand");
		Creature victim = SingletonRepository.getEntityManager().getCreature("mouse");
		Item arrow = SingletonRepository.getEntityManager().getItem("wooden arrow");
		
		// Add target to zone
		zone.add(victim);
		
		// Equip sluggish wand to player
		player.equip("bag", wand);
		player.equip("lhand", arrow);
		final EquipmentAction action = new EquipAction();
		RPAction equip = new RPAction();
		equip.put("type", "equip");
		equip.put(EquipActionConsts.BASE_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.BASE_SLOT, "bag");
		equip.put(EquipActionConsts.BASE_ITEM, wand.getID().getObjectID());

		equip.put(EquipActionConsts.TARGET_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.TARGET_SLOT, "rhand");

		action.onAction(player, equip);
		
		// Get initial values
		double initBaseSpeed = victim.getBaseSpeed();

		// Get player to attack target
		player.setTarget(victim);
		player.attack();
		
		// Check target is now slower than it was before attack
		assertTrue(initBaseSpeed > victim.getBaseSpeed());
	}
	
	/**
	 * Tests if the speed of another player is reduced when attacked by the wand
	 */
	@Test
	public void testForShouldReduceBaseSpeedOfPlayerOnAttack() {
		// Set up the fixture
		final Player victim = PlayerTestHelper.createPlayer("bob");
		final Item wand = SingletonRepository.getEntityManager().getItem("sluggish wand");
		Item arrow = SingletonRepository.getEntityManager().getItem("wooden arrow");
		
		// Add target to zone
		zone.add(victim);
		
		// Equip sluggish wand to player
		player.equip("bag", wand);
		player.equip("lhand", arrow);
		final EquipmentAction action = new EquipAction();
		RPAction equip = new RPAction();
		equip.put("type", "equip");
		equip.put(EquipActionConsts.BASE_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.BASE_SLOT, "bag");
		equip.put(EquipActionConsts.BASE_ITEM, wand.getID().getObjectID());

		equip.put(EquipActionConsts.TARGET_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.TARGET_SLOT, "rhand");

		action.onAction(player, equip);
		
		// Get initial values
		double initBaseSpeed = victim.getBaseSpeed();

		// Get player to attack target
		player.setTarget(victim);
		player.attack();
		
		// Check target is now slower than it was before attack
		assertTrue(initBaseSpeed > victim.getBaseSpeed());
	}
	
	/**
	 * Tests that after unequipping the wand, an attack should not add the status effect
	 */
	@Test
	public void testForShouldNotSlowAfterUnequip() {
		// Set up the fixture
		final Item wand = SingletonRepository.getEntityManager().getItem("sluggish wand");
		Creature victim = SingletonRepository.getEntityManager().getCreature("mouse");
		Item arrow = SingletonRepository.getEntityManager().getItem("wooden arrow");
		
		// Add target to zone
		zone.add(victim);
		
		// Equip sluggish wand to player
		player.equip("bag", wand);
		player.equip("lhand", arrow);
		final EquipmentAction action = new EquipAction();
		RPAction equip = new RPAction();
		equip.put("type", "equip");
		equip.put(EquipActionConsts.BASE_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.BASE_SLOT, "bag");
		equip.put(EquipActionConsts.BASE_ITEM, wand.getID().getObjectID());

		equip.put(EquipActionConsts.TARGET_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.TARGET_SLOT, "rhand");
		
		action.onAction(player, equip);
		
		RPAction unequip = new RPAction();
		unequip.put("type", "equip");
		unequip.put(EquipActionConsts.BASE_OBJECT, player.getID().getObjectID());
		unequip.put(EquipActionConsts.BASE_SLOT, "rhand");
		unequip.put(EquipActionConsts.BASE_ITEM, wand.getID().getObjectID());

		unequip.put(EquipActionConsts.TARGET_OBJECT, player.getID().getObjectID());
		unequip.put(EquipActionConsts.TARGET_SLOT, "bag");
		
		action.onAction(player, unequip);
		
		
		// Get initial values
		double initBaseSpeed = victim.getBaseSpeed();

		
		// Get player to attack target
		player.drop(wand);
		player.setTarget(victim);
		player.attack();
		
		// Check target is now slower than it was before attack
		assertEquals(initBaseSpeed, victim.getBaseSpeed(), 0.001);
	}

}
