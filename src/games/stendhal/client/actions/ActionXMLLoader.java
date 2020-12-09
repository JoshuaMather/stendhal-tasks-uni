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
/**
 * ActionXMLLoader class extends DefaultHandler to parse and read from XML files.
 * Returns a List of all actions loaded to ActionXMLGroupLoader using a load method.
 */
public class ActionXMLLoader extends DefaultHandler{
	private static final String VALUE = "value";

	private static final Logger logger = Logger.getLogger(ActionXMLLoader.class);

	private String type; //current action type.

	private String implementation; //current action implementation

	private Map<String, HashMap<String, String>> attributeValues; //Map of all attribute within XML

	private List<DefaultAction> loadedActions; //List of actions loaded

	private boolean attributeTagFound; //flag to check if attributes tag has been hit.

	private String minParams; //action minparams
	
	private String maxParams; //action maxparams
	
	private String prev; //previous item loaded by XML

	private String name; //action name
	
	public ActionXMLLoader() {}
	
	/**
	 * Loads the parser and throws an error if the actions couldn't be loaded.
	 * @param uri
	 * @return A list of loaded actions
	 * @throws SAXException
	 */
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
	
	/**
	 * Start of an XML element.
	 * Places the correct values into the current type, minParams etc.
	 * If attribute tag has been found, builds the attributeValues HashMap.
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		switch (qName) {
			case "action":
				name = attributes.getValue("name");
				break;
			case "type": //action type
				type = attributes.getValue(VALUE);
				break;
			case "min_params": //action min params
				minParams = attributes.getValue(VALUE);
				break;
			case "max_params": //action max params
				maxParams = attributes.getValue(VALUE);
				break;
			case "implementation": //action implementation
				implementation = attributes.getValue(VALUE);
				break;
			case "attributes": //action attributes
				attributeValues = new HashMap<String, HashMap<String, String>>();
				attributeTagFound = true;
				break;
			default:
				
		}
		if(attributeTagFound) { //create map of <"val", <"source" , "val">>
			if ((qName.equals("param") || qName.equals("remainder") || qName.equals("string"))) {
				HashMap<String, String> inputMap = new HashMap<String, String>();
				inputMap.put(qName, attributes.getValue(VALUE));
				attributeValues.put(prev, inputMap);
			}
		}
		this.prev = qName;
	}
	
	/**
	 * Logic for the end of each XML element.
	 * Interested only in the end of each 'action' element.
	 * Loads all of the required attributes into a new instance of DefaultAction.
	 * Adds created default actions into the list of loaded actions.
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("action")) {
			DefaultAction action = new DefaultAction(type, implementation);
			action.setMinParams(minParams);
			action.setMaxParams(maxParams);
			action.setName(name);
			for (String key: attributeValues.keySet()) { //for each attribute type
				Map<String, String> source = new HashMap<String, String>(); //init map
				if (attributeValues.get(key).containsKey("param")){
					source.put("param", attributeValues.get(key).get("param"));
					action.addAttribute(key, source); 
				}
				else if (attributeValues.get(key).containsKey("string")) {
					source.put("string", attributeValues.get(key).get("string"));
					action.addAttribute(key, source);
				}
				else {
					source.put("remainder", attributeValues.get(key).get("remainder"));
					action.addAttribute(key, source);
				}
			}
			loadedActions.add(action); //add to loaded actions
		}
		if(qName.equals("attributes")) {
			attributeTagFound = false;
		}
	}
}
