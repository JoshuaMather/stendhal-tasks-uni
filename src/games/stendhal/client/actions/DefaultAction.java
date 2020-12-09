package games.stendhal.client.actions;

import java.util.Map;

import org.apache.log4j.Logger;

public class DefaultAction {
    
    private static final Logger logger = Logger.getLogger(DefaultAction.class);
    
    private String type;
    
    private Class<?> implementation;
    
    private Map<String, Map<String, String>> attributes;
    
    private String minParams;

    private String maxParams;

    public DefaultAction (String type, String implementation) {
   	 try {
   		 this.type = type;
   		 this.implementation = Class.forName(implementation);
   	 }
   	 catch (ClassNotFoundException e) {
   		 logger.error("Error while creating DefaultSpell", e);
   	 }
    }
    
    public void setType(String typeIn) {
   	 this.type = typeIn;
    }
    
    public void setImplementation(Class<?> imp) {
   	 this.implementation = imp;
    }
    
    public Class<?> getImplementation (){
   	 return this.implementation;
    }
    
    public String getType() {
   	 return this.type;
    }
    
    public void addAttribute (String type, Map<String, String> source) {
   	 this.attributes.put(type, source);
    }

    public String getMaxParams() {
   	 return maxParams;
    }

    public void setMaxParams(String maxParams) {
   	 this.maxParams = maxParams;
    }

    public String getMinParams() {
   	 return minParams;
    }

    public void setMinParams(String minParams) {
   	 this.minParams = minParams;
    }
    
    public Map<String, Map<String, String>> getAttributes() {
   	 return this.attributes;
    }
}
