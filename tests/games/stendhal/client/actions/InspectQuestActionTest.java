package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

import static games.stendhal.common.constants.Actions.INSPECTQUEST;

public class InspectQuestActionTest {

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
				assertEquals(INSPECTQUEST, action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("1", action.get("quest_slot"));
			}
		};
		final InspectQuestAction action = new InspectQuestAction();
		assertTrue(action.execute(new String []{"schnick", "1"}, null));
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final InspectQuestAction action = new InspectQuestAction();
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final InspectQuestAction action = new InspectQuestAction();
		assertThat(action.getMinimumParameters(), is(2));
	}

}
