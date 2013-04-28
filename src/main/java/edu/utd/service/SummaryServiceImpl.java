package edu.utd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.force.api.ApiSession;
import com.force.api.ForceApi;
import com.force.api.QueryResult;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

import edu.utd.model.Summary;

@Service
public class SummaryServiceImpl implements SummaryService {
    
    private ForceApi getForceApi() {
        SecurityContext sc = ForceSecurityContextHolder.get();

        ApiSession s = new ApiSession();
        s.setAccessToken(sc.getSessionId());
        s.setApiEndpoint(sc.getEndPointHost());

        return new ForceApi(s);
    }
    

	public void addSummary(Summary summary) 
	{
        getForceApi().createSObject("Summary", summary);
        
	}
        
    public List<Summary> listSummary() 
    {
            QueryResult<Summary> res = getForceApi().query("SELECT robocode__Name__c, robocode__C_Win__c, robocode__C_Lose__c, robocode__C_Total__c FROM robocode__Summary__c", Summary.class);
            return res.getRecords();
            
    }

    public void removeSummary(String summary) {
            getForceApi().deleteSObject("Summary", summary);
    }
}
