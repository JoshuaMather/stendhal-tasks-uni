package games.stendhal.client.actions;

import java.util.Map;

import org.apache.log4j.Logger;

public class DefaultAction {
    
    private static final Logger logger = Logger.getLogger(DefaultAction.class);
    
    private String type; //action type
    
    private Class<?> implementation; //action implementation
    
    private Map<String, Map<String, String>> attributes; //map of all action's attributes
    
    private String minParams; //action minParams

    private String maxParams; //action maxParams
    
    /**
	 * Constructor for DefaultAction sets type and implementation.
	 * @param type
	 * @param implementation
	 */
    public DefaultAction (String type, String implementation) {
   	 try {
   		 this.type = type;
   		 this.implementation = Class.forName(implementation);
   	 }
   	 catch (ClassNotFoundException e) {
   		 logger.error("Error while creating DefaultSpell", e);
   	 }
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
    public void setImplementation(Class<?> imp) {
   	 this.implementation = imp;
    }
    
    /**
	 * Getter for implementation
	 * @return
	 */
    public Class<?> getImplementation (){
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
}
