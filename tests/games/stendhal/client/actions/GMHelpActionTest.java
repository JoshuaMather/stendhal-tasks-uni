package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.MockClientUI;
import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class GMHelpActionTest {

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
//				assertEquals(INSPECTQUEST, action.get("type"));
//				assertEquals("schnick", action.get("target"));
//				assertEquals("1", action.get("quest_slot"));
			}
		};
		MockClientUI ui = new MockClientUI();
		ClientSingletonRepository.setUserInterface(ui);
		
		final GMHelpAction action = new GMHelpAction();
		assertTrue(action.execute(new String [] {null}, null));
		assertTrue(action.execute(new String []{"alter"}, null));
		assertTrue(action.execute(new String []{"script"}, null));
		assertTrue(action.execute(new String []{"support"}, null));
		assertFalse(action.execute(new String []{"not an option"}, null));
		assertFalse(action.execute(new String []{"one", "two"},  null));
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
