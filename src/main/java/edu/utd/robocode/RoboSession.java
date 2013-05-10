package edu.utd.robocode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import robocode.BattleResults;

public class RoboSession
{
    private List<String> robotList = new ArrayList<String>();

    private List<BattleResults> results = new LinkedList<BattleResults>();

    private Integer sessionCapacity = 0;

    private String sessionName = "";

    private String sessionStatus = "";

    private Integer players = 0;

    public void addRobot(String robotName)
    {
        robotList.add(robotName + "*");
        players = robotList.size();
    }

    public String getRobot(int idx)
    {
        StringBuilder buf = new StringBuilder(robotList.get(idx));
        // Remove the '*' character in the end;
        return (buf.deleteCharAt(buf.length() - 1)).toString();
    }

    public String removeRobot(String robotName)
    {
        for (String robot_name : robotList)
        {
            if (robot_name.equals(robotName))
            {
                robotList.remove(robot_name);
                players = robotList.size();
                return robot_name;
            }
        }
        return null;
    }

    public List<String> getRobotList()
    {
        return robotList;
    }

    public void setRobotList(List<String> robotList)
    {
        this.robotList = robotList;
        this.players = this.robotList.size();
    }

    public List<BattleResults> getResults()
    {
        return results;
    }

    public void setResults(List<BattleResults> results)
    {
        this.results = results;
    }

    public BattleResults findResult(String robotName)
    {
        for (BattleResults result : results)
        {
            if (result.getTeamLeaderName().equals(robotName + "*"))
            {
                return result;
            }
        }
        return null;
    }

    public void delResult(BattleResults result)
    {
        results.remove(result);
    }

    public Integer getSessionCapacity()
    {
        return sessionCapacity;
    }

    public void setSessionCapacity(Integer sessionCapacity)
    {
        this.sessionCapacity = sessionCapacity;
    }

    public String getSessionName()
    {
        return sessionName;
    }

    public void setSessionName(String sessionName)
    {
        this.sessionName = sessionName;
    }

    public String getSessionStatus()
    {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus)
    {
        this.sessionStatus = sessionStatus;
    }

    public boolean isEnded()
    {
        return results.isEmpty() && "Completed".equals(sessionStatus);
    }

    public Integer getPlayers()
    {
        return players;
    }
}