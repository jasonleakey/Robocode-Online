package edu.utd.model;

import java.util.Calendar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSummary
{
    @JsonProperty(value = "Id")
    private String id;
    
    @JsonProperty(value = "robocode__Race_Time__c")
    private Calendar race_Time;

    @JsonProperty(value = "robocode__Result__c")
    private long result;

    public Calendar getRace_Time()
    {
        return race_Time;
    }

    public void setRace_Time(Calendar race_Time)
    {
        this.race_Time = race_Time;
    }

    public long getResult()
    {
        return result;
    }

    public void setResult(long result)
    {
        this.result = result;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

}
