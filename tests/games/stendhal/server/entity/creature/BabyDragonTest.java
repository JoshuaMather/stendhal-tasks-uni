/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.creature;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.ConsumableItem;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPObject;
import utilities.PlayerTestHelper;
import utilities.RPClass.BabyDragonTestHelper;

public class BabyDragonTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BabyDragonTestHelper.generateRPClasses();
		MockStendlRPWorld.get();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		MockStendlRPWorld.reset();
	}

	List<String> foods = Arrays.asList("ham", "pizza", "meat");

	/**
	 * Tests for babyDragon.
	 */
	@Test
	public void testBabyDragon() {
		final BabyDragon drako = new BabyDragon();
		assertThat(drako.getFoodNames(), is(foods));
	}

	/**
	 * Tests for babyDragonPlayer.
	 */
	@Test
	public void testBabyDragonPlayer() {

		final StendhalRPZone zone = new StendhalRPZone("zone");
		final Player bob = PlayerTestHelper.createPlayer("bob");
		zone.add(bob);
		final BabyDragon drako = new BabyDragon(bob);

		assertThat(drako.getFoodNames(), is(foods));
	}

	/**
	 * Tests for babyDragonRPObjectPlayer.
	 */
	@Test
	public void testBabyDragonRPObjectPlayer() {
		RPObject template = new RPObject();
		template.put("hp", 30);
		final BabyDragon drako = new BabyDragon(template, PlayerTestHelper.createPlayer("bob"));
		assertThat(drako.getFoodNames(), is(foods));
	}
	
	/**
	 * Tests for whether pets will drink potions when their hp is above 100 and when their hp is below 100.
	 */
	@Test
	public void testPetPotionDrinkingLogic() {
		final Map<String, String> attributes = new HashMap<String, String>();
		
		final StendhalRPZone zone = new StendhalRPZone("zone");
		final Player bob = PlayerTestHelper.createPlayer("bob");
		zone.add(bob);
		final BabyDragon testPet = new BabyDragon(bob);
		testPet.setPosition(0, 6);
		testPet.damage(400, bob);
		attributes.put("amount", "100");
		attributes.put("quantity", "1");
		attributes.put("regen", "100");
		attributes.put("frequency", "1");
		ConsumableItem testPotion = new ConsumableItem("potion", "drink", "potion", 
														attributes);
		zone.add(testPotion);
		testPotion.setPosition(0,6);
		testPet.logic();
		assertEquals(100, testPet.getHP());
		testPet.damage(50, bob);
		testPet.logic();
		assertEquals(150, testPet.getHP());
	}

}
