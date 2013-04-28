package edu.utd.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)

public class User_Summary {

	@JsonProperty(value="User_Summary_ID")
	private String User_Summary_ID;
	
	@JsonProperty(value="robocode__Race_Time__c")
	private String Race_Time;
	
	@JsonProperty(value="robocode__Result__c")
	private String Result;

	public String getRace_Time() {
		return Race_Time;
	}

	public void setRace_Time(String race_Time) {
		Race_Time = race_Time;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}
	
}
