package games.stendhal.client.actions;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;


public class XMLActionTest {
	
	
	private static XMLAction action = new XMLAction();
	private static Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
	
	@Before
	public  void setUpBefore() throws Exception {
	map = new HashMap<String,Map<String,String>>();
	action = new XMLAction();
	action.setMap(new HashMap<String,Map<String,String>>());
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.import static org.hamcrest.CoreMatchers.is;
	 */
	@Test
	public void testExecuteAddBuddy() {
		
		Map<String, String> pair=new HashMap<String, String>();
		pair.put("param", "0");		
		map.put("target", pair);
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
	public void testExecuteEmote() {
		

		action.setName("emote");
		Map<String,String> pair=new HashMap<String,String>();
		pair.put("remainder","-1");		
		map.put("text",pair);
		System.out.println(map);
		//pair.clear();
		
		action.setMap(map);
		
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
					assertEquals("emote", action.get("type"));
					assertEquals("<3", action.get("text"));
			}
		};
		assertTrue(action.execute(null,"<3"));
	}
	
	@Test
	public void testExecuteAlter() {
		

		action.setName("alter");
		Map<String,String> pair=new HashMap<String,String>();
		pair.put("param","0");		
		map.put("target",pair);
		Map<String,String> pair2=new HashMap<String,String>();
		pair2.put("param","1");		
		map.put("stat",pair2);
		Map<String,String> pair3=new HashMap<String,String>();
		pair3.put("param","2");		
		map.put("mode",pair3);
		Map<String,String> pair4=new HashMap<String,String>();
		pair4.put("remainder","-1");		
		map.put("value",pair4);
		
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

		assertTrue(action.execute(new String[] { "schnick", "schnack", "schnuck" }, "blabla"));
	}
	
	@Test
	public void getMinimumParametersWho() {
		action.setName("who");
		action.setMinParams(0);

		assertThat(action.getMinimumParameters(), is(0));
	}
	
	@Test
	public void getMaximumParametersWho() {
		action.setName("who");
		action.setMinParams(0);
		
		assertThat(action.getMaximumParameters(), is(0));
	}

	@Test
	public void getMinimumParametersAlter() {
		action.setName("alter");
		action.setMinParams(3);
		
		assertThat(action.getMinimumParameters(), is(3));	}
	
	
	@Test
	public void getMaximumParametersAlter() {
		action.setName("alter");
		action.setMaxParams(3);

		assertThat(action.getMaximumParameters(), is(3));	}

}
