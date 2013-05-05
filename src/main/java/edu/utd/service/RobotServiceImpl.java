package edu.utd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.force.api.ApiSession;
import com.force.api.ForceApi;
import com.force.api.QueryResult;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

import edu.utd.model.Robot;

@Service
public class RobotServiceImpl implements RobotService
{

    private ForceApi getForceApi()
    {
        SecurityContext sc = ForceSecurityContextHolder.get();

        ApiSession s = new ApiSession();
        s.setAccessToken(sc.getSessionId());
        s.setApiEndpoint(sc.getEndPointHost());

        return new ForceApi(s);
    }

    public void addRobot(Robot robot)
    {
        getForceApi().createSObject("Robot", robot);
    }

    public List<Robot> listRobot()
    {
        QueryResult<Robot> res = getForceApi()
                .query("SELECT robocode__Name__c, robocode__Source_Code__c FROM robocode__Robot__c",
                        Robot.class);
        return res.getRecords();
    }

    public Robot getRobot(String name)
    {
        QueryResult<Robot> res = getForceApi()
                .query("SELECT robocode__Name__c, robocode__Source_Code__c FROM robocode__Robot__c WHERE robocode__Name__c='"
                        + name + "'", Robot.class);
        return res.getRecords().get(0);
    }

    public void removeRobot(String robot)
    {
        getForceApi().deleteSObject("Robot", robot);
    }
}
