package edu.utd.service;

import java.util.List;

import edu.utd.model.User_Summary;

public interface User_SummaryService {

	public void addUser_Summary(User_Summary user_summary);
    public List<User_Summary> listUser_Summary();
    public void removeUser_Summary(String id);
    
}
