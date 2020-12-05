package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class GagActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("gag", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("10", action.get("minutes"));
			}
		};
		final GagAction action = new GagAction();
		assertFalse(action.execute(new String[]{"schnick", "10"}, ""));
		assertTrue(action.execute(new String[]{"schnick", "10"}, "reason"));
	}
	
	/**
	* Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final GagAction action = new GagAction();
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final GagAction action = new GagAction();
		assertThat(action.getMinimumParameters(), is(2));
	}
}