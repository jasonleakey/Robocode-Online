package edu.utd.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Robot
{
    @JsonProperty(value = "Id")
    private String id;
    
    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "robocode__Score__c")
    private long score;

    @JsonProperty(value = "robocode__Source_Code__c")
    private String source_Code;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSource_Code()
    {
        return source_Code;
    }

    public void setSource_Code(String source_Code)
    {
        this.source_Code = source_Code;
    }

    public long getScore()
    {
        return score;
    }

    public void setScore(long score)
    {
        this.score = score;
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
