package edu.utd.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Robot {

	@JsonProperty(value="Robot_ID")
	private String Robot_ID;
	
	@JsonProperty(value="robocode__Name__c")
	private String Name;

	@JsonProperty(value="robocode__Source_Code__c")
	private String Source_Code;
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSource_Code() {
		return Source_Code;
	}

	public void setSource_Code(String source_Code) {
		Source_Code = source_Code;
	}

	public String getRobot_ID() {
		return Robot_ID;
	}

	public void setRobot_ID(String robot_ID) {
		Robot_ID = robot_ID;
	}
}
