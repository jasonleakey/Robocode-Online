package edu.utd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.force.api.ApiSession;
import com.force.api.ForceApi;
import com.force.api.QueryResult;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

import edu.utd.model.User_Summary;

@Service
public class User_SummaryServiceImpl implements User_SummaryService {
    
    private ForceApi getForceApi() {
        SecurityContext sc = ForceSecurityContextHolder.get();

        ApiSession s = new ApiSession();
        s.setAccessToken(sc.getSessionId());
        s.setApiEndpoint(sc.getEndPointHost());

        return new ForceApi(s);
    }
    

	public void addUser_Summary(User_Summary user_summary) 
	{
        getForceApi().createSObject("User_Summary", user_summary);
        
	}
        
    public List<User_Summary> listUser_Summary() 
    {
            QueryResult<User_Summary> res = getForceApi().query("SELECT robocode__Result__c, robocode__Race_Time__c FROM robocode__User_Summary__c", User_Summary.class);
            return res.getRecords();
            
    }

    public void removeUser_Summary(String User_Summary) {
            getForceApi().deleteSObject("User_Summary", User_Summary);
    }

}
