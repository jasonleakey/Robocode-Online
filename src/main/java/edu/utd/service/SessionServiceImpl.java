package edu.utd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.force.api.ApiSession;
import com.force.api.ForceApi;
import com.force.api.QueryResult;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

import edu.utd.model.Session;

@Service
public class SessionServiceImpl implements SessionService
{

    private ForceApi getForceApi()
    {
        SecurityContext sc = ForceSecurityContextHolder.get();

        ApiSession s = new ApiSession();
        s.setAccessToken(sc.getSessionId());
        s.setApiEndpoint(sc.getEndPointHost());

        return new ForceApi(s);
    }

    public void addSession(Session session)
    {
        getForceApi().createSObject("Session", session);

    }

    public List<Session> listSession()
    {
        QueryResult<Session> res = getForceApi()
                .query("SELECT robocode__RoomName__c, robocode__Player_Number__c, robocode__Capacity__c, robocode__Status__c, robocode__Players_5__c, robocode__Players_6__c, robocode__Players_3__c, robocode__Players_4__c, robocode__Players_1__c,  FROM robocode__Session__c",
                        Session.class);
        return res.getRecords();

    }

    public void removeSession(String Session)
    {
        getForceApi().deleteSObject("Session", Session);
    }
}
