package games.stendhal.client.actions;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * DefaultActionManager class to handle the loading and parsing of actions from XML.
 */
public class DefaultActionManager {
	
	private static final Logger LOGGER = Logger.getLogger(DefaultActionManager.class);

	private Map<String, DefaultAction> defaultActions;
	
	
	/**
	 * Contructor for DefaultActionManager
	 * sets up the hashMap of default actions and calls buildActionsTable to populate the HashMap.
	 */
	
	public DefaultActionManager() {
		defaultActions = new HashMap<String, DefaultAction>();
		buildActionsTables();
	}
	
	/**
	 * Creates a new instance of ActionGroupsXMLLoader and loads all of the actions from XML.
	 * Calls addAction method to place the action within the defaultActions list.
	 */
	private void buildActionsTables() {
		try {
			final ActionGroupsXMLLoader loader = new ActionGroupsXMLLoader(new URI("/data/conf/actions.xml"));
			List<DefaultAction> loadedDefaultAction = loader.load();
			for (DefaultAction defaultAction: loadedDefaultAction) {
				addAction(defaultAction);
			}
		} catch (Exception e) {
			LOGGER.error("actions.xml could not be loaded", e);
		}
	}
	
	/**
	 * Adds an action, checking if the action is already in the list of actions.
	 * @param action
	 */
	private boolean addAction(DefaultAction action) {
		if(defaultActions.containsKey(action.getName())) {
			LOGGER.warn("Repeated action name: "+ action.getName());
		}
		defaultActions.put(action.getName(), action);
		return true;
	}

	/**
	 * @return all of the actions loaded from XML.
	 */
	public Map<String, DefaultAction> getActions() {
		return this.defaultActions;
	}
}
