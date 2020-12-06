package games.stendhal.client.actions;

import java.util.Map;


import games.stendhal.client.ClientSingletonRepository;
import marauroa.common.game.RPAction;

public class XMLAction implements SlashAction {

	
	
	private String name;
	private DefaultAction action;
	private Map<String,Map<String,String>> map;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DefaultAction getAction() {
		return action;
	}

	public void setAction(DefaultAction action) {
		this.action = action;
	}

	public Map<String, Map<String, String>> getMap() {
		return map;
	}


	public void setMap(Map<String, Map<String, String>> map) {
		this.map = map;
	}

	public XMLAction() {
	}
	
	public XMLAction(String name, DefaultAction action) {
		this.name=name;
		this.action=action;
		this.map=action.getAttributes();
	}

	
	
	
	
	
	@Override
	public boolean execute(String[] params, String remainder) {
		final RPAction add = new RPAction();
		add.put("type", name);
		if(map!= null)
		for(String key: map.keySet())
		{
			
			if(map.get(key).containsKey("param"))
				add.put(key, params[Integer.parseInt(map.get(key).get("param"))]);
			else
				if(map.get(key).containsKey("string"))
					add.put(key, map.get(key).get("string"));
				else
					add.put(key, remainder);
				
		
			ClientSingletonRepository.getClientFramework().send(add);
		}
		return true;
	}

	@Override
	public int getMaximumParameters() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinimumParameters() {
		// TODO Auto-generated method stub
		return 0;
	}

}
