package edu.utd.service;
import java.util.List;

import edu.utd.model.Session;

public interface SessionService {

	public String addSession(Session session);
    public List<Session> listSession();
    public void removeSession(String id);
    
}