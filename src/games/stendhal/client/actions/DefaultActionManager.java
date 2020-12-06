/**
 * 
 */
package games.stendhal.client.actions;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * @author csimage
 *
 */
public class DefaultActionManager {
	
	private static final Logger LOGGER = Logger.getLogger(DefaultActionManager.class);

	private Map<String, DefaultAction> defaultActions;
	
	
	/**
	 * Contructor for DefaultActionManager
	 * 
	 */
	
	public DefaultActionManager() {
		defaultActions = new HashMap<String, DefaultAction>();
		buildActionsTables();
	}
	
	
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
	
	private boolean addAction(DefaultAction action) {
		if(defaultActions.containsKey(action.getType())) {
			LOGGER.warn("Repeated action name: "+ action.getType());
		}
		defaultActions.put(action.getType(), action);
		return true;
	}

	/**
	 * @return all of the actions loaded from XML.
	 */
	public Map<String, DefaultAction> getActions() {
		return this.defaultActions;
	}
}
