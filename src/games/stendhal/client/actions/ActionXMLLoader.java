package games.stendhal.client.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ActionXMLLoader extends DefaultHandler{
	private static final Logger logger = Logger.getLogger(ActionXMLLoader.class);

	private String type;

	private String implementation;

	private Map<String, HashMap<String, String>> attributeValues;

	private List<DefaultAction> loadedActions;

	private boolean attributeTagFound;

	private String minParams;
	
	private String maxParams;
	
	private String prev;

	private String name;
	
	public ActionXMLLoader() {}
	
	public Collection<DefaultAction> load (URI uri) throws SAXException{
		loadedActions = new LinkedList<DefaultAction>();
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Parse the input
			final SAXParser saxParser = factory.newSAXParser();

			final InputStream is = ActionXMLLoader.class.getResourceAsStream(uri.getPath());

			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + uri
						+ "' in classpath");
			}
			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			logger.error(t);
		} catch (final IOException e) {
			logger.error(e);
			throw new SAXException(e);
		}
		return loadedActions;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("action")) {
			name = attributes.getValue("name");
		}
		if (qName.equals("type")) {
			type = attributes.getValue(qName);
		}
		if (qName.equals("min_params")) {
			minParams = attributes.getValue(qName);
		}
		if (qName.equals("max_params")) {
			maxParams = attributes.getValue(qName);
		}
		if (qName.equals("implementation")) {
			implementation = attributes.getValue(qName);
		}
		if (qName.equals("attributes")) {
			attributeValues = new HashMap<String, HashMap<String, String>>();
			attributeTagFound = true;
		}
		if(attributeTagFound) {
			if ((qName.equals("param") || qName.equals("remainder") || qName.equals("string"))) {
				HashMap<String, String> inputMap = new HashMap<String, String>();
				inputMap.put(qName, attributes.getValue("value"));
				attributeValues.put(prev, inputMap);
			}
		}
		this.prev = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("action")) {
			DefaultAction action = new DefaultAction(type, implementation);
			action.setMinParams(minParams);
			action.setMaxParams(maxParams);
			action.setName(name);
			for (String key: attributeValues.keySet()) {
				Map<String, String> source = new HashMap<String, String>();
				if (attributeValues.get(key).containsKey("param")){
					source.put("param", attributeValues.get(key).get("param"));
					action.addAttribute(qName, source);
				}
				else if (attributeValues.get(key).containsKey("string")) {
					source.put("string", attributeValues.get(key).get("string"));
					action.addAttribute(qName, source);
				}
				else {
					source.put("remainder", attributeValues.get(key).get("remainder"));
					action.addAttribute(qName, source);
				}
			}
			loadedActions.add(action);
		}
		if(qName.equals("attributes")) {
			attributeTagFound = false;
		}
	}
}
