package edu.utd.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session
{
    @JsonProperty(value = "Id")
    private String id;
    
    @JsonProperty(value = "Name")
    private String roomName;

    @JsonProperty(value = "robocode__Status__c")
    private String status;

    @JsonProperty(value = "robocode__Num_Of_Players__c")
    private int player_Number;

    @JsonProperty(value = "robocode__Capacity__c")
    private int capacity;

    @JsonProperty(value = "robocode__Player_1__c")
    private String player_1;

    @JsonProperty(value = "robocode__Player_2__c")
    private String player_2;

    @JsonProperty(value = "robocode__Player_3__c")
    private String player_3;

    @JsonProperty(value = "robocode__Player_4__c")
    private String player_4;

    @JsonProperty(value = "robocode__Player_5__c")
    private String player_5;

    @JsonProperty(value = "robocode__Player_6__c")
    private String player_6;

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getPlayer_Number()
    {
        return player_Number;
    }

    public void setPlayer_Number(int player_Number)
    {
        this.player_Number = player_Number;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public String getPlayer_1()
    {
        return player_1;
    }

    public void setPlayer_1(String player_1)
    {
        this.player_1 = player_1;
    }

    public String getPlayer_2()
    {
        return player_2;
    }

    public void setPlayer_2(String player_2)
    {
        this.player_2 = player_2;
    }

    public String getPlayer_3()
    {
        return player_3;
    }

    public void setPlayer_3(String player_3)
    {
        this.player_3 = player_3;
    }

    public String getPlayer_4()
    {
        return player_4;
    }

    public void setPlayer_4(String player_4)
    {
        this.player_4 = player_4;
    }

    public String getPlayer_5()
    {
        return player_5;
    }

    public void setPlayer_5(String player_5)
    {
        this.player_5 = player_5;
    }

    public String getPlayer_6()
    {
        return player_6;
    }

    public void setPlayer_6(String player_6)
    {
        this.player_6 = player_6;
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
