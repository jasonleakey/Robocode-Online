package edu.utd.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)

public class Summary {

	@JsonProperty(value="Summary_ID")
	private String Summary_ID;
	
	@JsonProperty(value="robocode__Name__c")
	private String Name;
	
	@JsonProperty(value="robocode__C_Win__c")
	private String C_Win;
	
	@JsonProperty(value="robocode__C_Lose__c")
	private String C_Lose;
	
	@JsonProperty(value="robocode__C_Total__c")
	private String C_Total;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getC_Win() {
		return C_Win;
	}

	public void setC_Win(String c_Win) {
		C_Win = c_Win;
	}

	public String getC_Lose() {
		return C_Lose;
	}

	public void setC_Lose(String c_Lose) {
		C_Lose = c_Lose;
	}

	public String getC_Total() {
		return C_Total;
	}

	public void setC_Total(String c_Total) {
		C_Total = c_Total;
	}
}
