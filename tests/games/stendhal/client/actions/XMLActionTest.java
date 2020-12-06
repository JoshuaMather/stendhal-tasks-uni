package games.stendhal.client.actions;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;


public class XMLActionTest {
	
	
	private static XMLAction action=new XMLAction();
	private static Map<String,Map<String,String>> map=new HashMap<String,Map<String,String>>();
	
	@Before
	public  void setUpBefore() throws Exception {
	map=new HashMap<String,Map<String,String>>();
	action.setMap(new HashMap<String,Map<String,String>>());
	action=new XMLAction();
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecuteAddBuddy() {
		
		Map<String,String> pair=new HashMap<String,String>();
		pair.put("param","0");		
		map.put("target",pair);
		action.setMap(map);
		action.setName("addbuddy");
		
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("addbuddy", action.get("type"));
				assertEquals("schnick", action.get("target"));
			}
		};
		assertTrue(action.execute(new String []{"schnick"},null));
	}
	
	@Test
	public void testExecuteWho() {
		

		action.setName("who");
		
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				for (final String attrib : action) {
					assertEquals("type", attrib);
					assertEquals("who", (action.get(attrib)));
				}
			}
		};
		assertTrue(action.execute(null, null));
	}
	
	@Test
	public void testExecuteAlter() {
		

		action.setName("alter");
		Map<String,String> pair=new HashMap<String,String>();
		pair.put("param","0");		
		map.put("target",pair);
		pair.clear();
		pair.put("param","1");		
		map.put("stat",pair);
		pair.clear();
		pair.put("param","2");		
		map.put("mode",pair);
		pair.clear();
		pair.put("remainder","-1");		
		map.put("value",pair);
		pair.clear();
		
		action.setMap(map);
		
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("alter", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("schnack", action.get("stat"));
				assertEquals("schnuck", action.get("mode"));
				assertEquals("blabla", action.get("value"));
			}
		};
		final AlterAction action = new AlterAction();
		assertFalse(action.execute(null, null));
		assertFalse(action.execute(new String[] { "schnick" }, null));
		assertFalse(action.execute(new String[] { "schnick", "schnick" }, null));
		assertFalse(action.execute(new String[] { "schnick", "schnick", "schnick" }, null));

		assertTrue(action.execute(new String[] { "schnick", "schnack", "schnuck" }, "blabla"));
	}



}
