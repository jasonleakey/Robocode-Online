package edu.utd.service;


import java.util.List;

import edu.utd.model.Robot;

public interface RobotService {
    
    public void addRobot(Robot robot);
    public List<Robot> listRobot();
    public Robot getRobot(String name);
    public void removeRobot(String id);
}