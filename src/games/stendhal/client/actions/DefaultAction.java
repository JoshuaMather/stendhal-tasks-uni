package games.stendhal.client.actions;

import java.util.HashMap;
import java.util.Map;


public class DefaultAction {
	
	private String type; //action type
	
	private String implementation; //action implementation
	
	private Map<String, Map<String, String>> attributes = new HashMap<String, Map<String, String>>(); //map of all an action's attributes
	
	private String minParams; //action minParams

	private String maxParams; //action maxParams
	
	private String Name; //action name
	
	/**
	 * Constructor for DefaultAction sets type and implementation.
	 * @param type
	 * @param implementation
	 */
	public DefaultAction (String type, String implementation) {
		this.type = type;
		this.implementation = implementation;
	}
	
	/**
	 * Setter for type
	 * @param typeIn
	 */
	public void setType(String typeIn) {
		this.type = typeIn;
	}
	
	/**
	 * Setter for implementation
	 * @param imp
	 */
	public void setImplementation(String imp) {
		this.implementation = imp;
	}
	
	/**
	 * Getter for implementation
	 * @return
	 */
	public String getImplementation (){
		return this.implementation;
	}
	
	/**
	 * Getter for type
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Adds a new attribute to the attributesMap
	 * @param type
	 * @param source
	 */
	public void addAttribute (String type, Map<String, String> source) {
		this.attributes.put(type, source);
	}
	
	/**
	 * Getter for maxParams
	 * @return maxParams
	 */
	public String getMaxParams() {
		return maxParams;
	}
	
	/**
	 * Setter for maxParams
	 * @param maxParams
	 */
	public void setMaxParams(String maxParams) {
		this.maxParams = maxParams;
	}

	/**
	 * Getter for minParams
	 * @return minParams
	 */
	public String getMinParams() {
		return minParams;
	}

	/**
	 * Setter for minParams
	 * @param minParams
	 */
	public void setMinParams(String minParams) {
		this.minParams = minParams;
	}
	
	/**
	 * Getter for attributes
	 * @return map of attributes
	 */
	public Map<String, Map<String, String>> getAttributes() {
		return this.attributes;
	}
	
	/**
	 * Getter for name
	 * @return string of name
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * Setter for name
	 * @param name
	 */
	public void setName(String name) {
		Name = name;
	}
}
