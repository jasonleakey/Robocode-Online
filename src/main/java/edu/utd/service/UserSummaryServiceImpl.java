package edu.utd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.force.api.ApiSession;
import com.force.api.ForceApi;
import com.force.api.QueryResult;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

import edu.utd.model.UserSummary;

@Service
public class UserSummaryServiceImpl implements UserSummaryService
{
    private ForceApi getForceApi()
    {
        SecurityContext sc = ForceSecurityContextHolder.get();

        ApiSession s = new ApiSession();
        s.setAccessToken(sc.getSessionId());
        s.setApiEndpoint(sc.getEndPointHost());

        return new ForceApi(s);
    }

    public void addUserSummary(UserSummary user_summary)
    {
        getForceApi().createSObject("robocode__User_Summary__c", user_summary);
    }

    public List<UserSummary> listUserSummary()
    {
        QueryResult<UserSummary> res = getForceApi()
                .query("SELECT Id, robocode__Result__c, robocode__Race_Time__c FROM robocode__User_Summary__c",
                        UserSummary.class);
        return res.getRecords();
    }

    public void removeUserSummary(String id)
    {
        getForceApi().deleteSObject("robocode__User_Summary__c", id);
    }

}
