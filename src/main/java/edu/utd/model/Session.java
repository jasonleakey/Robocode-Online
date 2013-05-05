package edu.utd.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session
{
    @JsonProperty(value = "robocode__RoomName__c")
    private String RoomName;

    @JsonProperty(value = "robocode__Status__c")
    private String Status;

    @JsonProperty(value = "robocode__Player_Number__c")
    private int Player_Number;

    @JsonProperty(value = "robocode__Capacity__c")
    private int Capacity;

    @JsonProperty(value = "robocode__Player_1__c")
    private String Players_1;

    @JsonProperty(value = "robocode__Player_2__c")
    private String Players_2;

    @JsonProperty(value = "robocode__Player_3__c")
    private String Players_3;

    @JsonProperty(value = "robocode__Player_4__c")
    private String Players_4;

    @JsonProperty(value = "robocode__Player_5__c")
    private String Players_5;

    @JsonProperty(value = "robocode__Player_6__c")
    private String Players_6;

    public String getRoomName()
    {
        return RoomName;
    }

    public void setRoomName(String roomName)
    {
        RoomName = roomName;
    }

    public String getStatus()
    {
        return Status;
    }

    public void setStatus(String status)
    {
        Status = status;
    }

    public int getPlayer_Number()
    {
        return Player_Number;
    }

    public void setPlayer_Number(int player_Number)
    {
        Player_Number = player_Number;
    }

    public int getCapacity()
    {
        return Capacity;
    }

    public void setCapacity(int capacity)
    {
        Capacity = capacity;
    }

    public String getPlayers_1()
    {
        return Players_1;
    }

    public void setPlayers_1(String players_1)
    {
        Players_1 = players_1;
    }

    public String getPlayers_2()
    {
        return Players_2;
    }

    public void setPlayers_2(String players_2)
    {
        Players_2 = players_2;
    }

    public String getPlayers_3()
    {
        return Players_3;
    }

    public void setPlayers_3(String players_3)
    {
        Players_3 = players_3;
    }

    public String getPlayers_4()
    {
        return Players_4;
    }

    public void setPlayers_4(String players_4)
    {
        Players_4 = players_4;
    }

    public String getPlayers_5()
    {
        return Players_5;
    }

    public void setPlayers_5(String players_5)
    {
        Players_5 = players_5;
    }

    public String getPlayers_6()
    {
        return Players_6;
    }

    public void setPlayers_6(String players_6)
    {
        Players_6 = players_6;
    }
}
