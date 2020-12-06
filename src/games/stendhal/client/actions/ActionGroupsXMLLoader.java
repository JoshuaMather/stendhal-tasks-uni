package games.stendhal.client.actions;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import games.stendhal.server.core.config.GroupsXMLLoader;

public class ActionGroupsXMLLoader {
	
	private static final Logger logger = Logger.getLogger(ActionGroupsXMLLoader.class);
	protected URI uri;
	
	public ActionGroupsXMLLoader(final URI uri) {
		this.uri = uri;
	}
	
	public List<DefaultAction> load() throws SAXException, IOException {
		final GroupsXMLLoader groupsLoader = new GroupsXMLLoader(this.uri);

		final List<URI> groups = groupsLoader.load();
		final ActionXMLLoader loader = new ActionXMLLoader();

		final List<DefaultAction> list = new LinkedList<DefaultAction>();
		for (final URI groupUri : groups) {
			logger.debug("Loading spell group [" + groupUri + "]");
			list.addAll(loader.load(groupUri));
		}

		return list;
	}
}
