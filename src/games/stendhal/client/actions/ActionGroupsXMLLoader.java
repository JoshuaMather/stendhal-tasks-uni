package games.stendhal.client.actions;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import games.stendhal.server.core.config.GroupsXMLLoader;

/**
 * ActionGroupsXMLLoader class loads all of the different action groups contained in the actions.xml file.
 */
public class ActionGroupsXMLLoader {
	
	private static final Logger logger = Logger.getLogger(ActionGroupsXMLLoader.class);
	protected URI uri;
	
	public ActionGroupsXMLLoader(final URI uri) {
		this.uri = uri;
	}
	
	/**
	 * Load creates an instance of GroupsXMLLoader to access different XML group files.
	 * Uses ActionXMLLoader to load in all of the actions from each of the separate groups.
	 * @return a list containing all of the actions loaded from the XML groups.
	 * @throws SAXException
	 * @throws IOException
	 */
	public List<DefaultAction> load() throws SAXException, IOException {
		final GroupsXMLLoader groupsLoader = new GroupsXMLLoader(this.uri);

		final List<URI> groups = groupsLoader.load();
		final ActionXMLLoader loader = new ActionXMLLoader();

		final List<DefaultAction> list = new LinkedList<DefaultAction>();
		for (final URI groupUri : groups) { //foreach group
			logger.debug("Loading action group [" + groupUri + "]");
			list.addAll(loader.load(groupUri));
		}

		return list;
	}
}
