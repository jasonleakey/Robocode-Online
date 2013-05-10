package edu.utd.service;

import java.util.List;

import edu.utd.model.UserSummary;

public interface UserSummaryService
{

    public void addUserSummary(UserSummary user_summary);

    public List<UserSummary> listUserSummary();

    public void removeUserSummary(String id);

}
