package games.stendhal.client.actions;

import java.util.HashMap;
import java.util.Map;


public class DefaultAction {
	
	private String type;
	
	private String implementation;
	
	private Map<String, Map<String, String>> attributes = new HashMap<String, Map<String, String>>();
	
	private String minParams;

	private String maxParams;
	
	private String Name;

	public DefaultAction (String type, String implementation) {
		this.type = type;
		this.implementation = implementation;
	}
	
	public void setType(String typeIn) {
		this.type = typeIn;
	}
	
	public void setImplementation(String imp) {
		this.implementation = imp;
	}
	
	public String getImplementation (){
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

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}
