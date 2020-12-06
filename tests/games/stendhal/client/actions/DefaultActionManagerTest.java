package games.stendhal.client.actions;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class DefaultActionManagerTest {
	
	DefaultActionManager manager;
	
	public DefaultActionManagerTest() {
		manager = new DefaultActionManager();
	}
	
	@Test
	public void testForWhoAction() {	
		Map<String, DefaultAction> actions = manager.getActions();
		assertTrue(actions.containsKey("who"));
		assertTrue(actions.get("who") != null);
	}
	
	@Test
	public void testForEmoteAction() {	
		Map<String, DefaultAction> actions = manager.getActions();
		assertTrue(actions.containsKey("emote"));
		assertTrue(actions.get("emote") != null);
	}
	
	@Test
	public void testForGagAction() {	
		Map<String, DefaultAction> actions = manager.getActions();
		assertTrue(actions.containsKey("gag"));
		assertTrue(actions.get("gag") != null);
	}
	
	@Test
	public void testForBanAction() {	
		Map<String, DefaultAction> actions = manager.getActions();
		assertTrue(actions.containsKey("ban"));
		assertTrue(actions.get("ban") != null);
	}

}