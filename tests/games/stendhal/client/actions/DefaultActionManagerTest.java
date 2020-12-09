package games.stendhal.client.actions;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultActionManagerTest {
	
	private static DefaultActionManager manager;
	private static Map<String, DefaultAction> actions;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		manager = new DefaultActionManager();
		actions = manager.getActions();
	}
	
	
	/**
	 * Tests that whoAction is loaded from the XML.
	 */
	@Test
	public void testForWhoAction() {	
		assertTrue(actions.containsKey("who"));
		assertTrue(actions.get("who") != null);
	}
	
	/**
	 * Tests that emoteActions is loaded from the XML.
	 */
	@Test
	public void testForEmoteAction() {	
		assertTrue(actions.containsKey("emote"));
		assertTrue(actions.get("emote") != null);
	}
	
	/**
	 * Tests that gagAction is loaded from the XML.
	 */
	@Test
	public void testForGagAction() {	
		assertTrue(actions.containsKey("gag"));
		assertTrue(actions.get("gag") != null);
	}
	
	/**
	 * Tests that banAction is loaded from the XML.
	 */
	@Test
	public void testForBanAction() {	
		assertTrue(actions.containsKey("ban"));
		assertTrue(actions.get("ban") != null);
	}

}