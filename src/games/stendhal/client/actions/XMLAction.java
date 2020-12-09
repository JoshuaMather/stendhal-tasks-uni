package games.stendhal.client.actions;

import java.util.Map;


import games.stendhal.client.ClientSingletonRepository;
import marauroa.common.game.RPAction;

public class XMLAction implements SlashAction {
	private String name;
	private DefaultAction action;
	private Map<String,Map<String,String>> map;
	private int maxParams;
	private int minParams;
	
	public XMLAction() {
	}
	
	/**
	 * Constructor for XMLAction that sets name, DefaultAction and min and max parameters
	 * @param name
	 * @param action
	 */
	public XMLAction(String name, DefaultAction action) {
		this.name=name;
		this.action=action;
		this.map=action.getAttributes();
		this.maxParams=Integer.parseInt(action.getMaxParams());
		this.minParams=Integer.parseInt(action.getMinParams());
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the action
	 */
	public DefaultAction getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(DefaultAction action) {
		this.action = action;
	}

	/**
	 * @return the map
	 */
	public Map<String, Map<String, String>> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, Map<String, String>> map) {
		this.map = map;
	}

	/**
	 * @return the maxParams
	 */
	public int getMaxParams() {
		return maxParams;
	}

	/**
	 * @param maxParams the maxParams to set
	 */
	public void setMaxParams(int maxParams) {
		this.maxParams = maxParams;
	}

	/**
	 * @return the minParams
	 */
	public int getMinParams() {
		return minParams;
	}

	/**
	 * @param minParams the minParams to set
	 */
	public void setMinParams(int minParams) {
		this.minParams = minParams;
	}
	
	
	/**
	 * Method for executing the action
	 * @param params
	 * @param  remainder
	 */
	@Override
	public boolean execute(String[] params, String remainder) {
		final RPAction add = new RPAction();
		add.put("type", name);
		if(map== null) {
			return false;
		}

		for(String key: map.keySet())
		{	
			if(map.get(key).containsKey("param")) {
				add.put(key, params[Integer.parseInt(map.get(key).get("param"))]);
			}else
				if(map.get(key).containsKey("string"))
					add.put(key, map.get(key).get("string"));
				else
					if(map.get(key).containsKey("remainder")) {
						add.put(key, remainder);

					}else
						return false;
		}
		ClientSingletonRepository.getClientFramework().send(add);
		return true;
	}

	/**
	 * Method to get the maximum parameters for an action
	 */
	@Override
	public int getMaximumParameters() {
		return maxParams;
	}

	/**
	 * Method to get the minimum parameters for an action
	 */
	@Override
	public int getMinimumParameters() {
		return minParams;
	}

}
