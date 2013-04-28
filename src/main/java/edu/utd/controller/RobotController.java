package edu.utd.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import edu.utd.model.Robot;
import edu.utd.robocode.RobocodeRunner;
import edu.utd.service.RobotService;

@Controller
public class RobotController {

    @Autowired
    private RobotService robotService;

    private int id = 30;
    
    @RequestMapping("/")
    public String listRobot(Map<String, Object> map) {

        new RobocodeRunner(new String[] {"sample.RamFire", "sample.Corners"});
        map.put("robot", new Robot());
        map.put("robotList", robotService.listRobot());

        return "robot";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addRobot() {
        
        Robot robot = new Robot();
        robot.setRobot_ID("R-" + String.format("%05d", id++));
        robot.setName("New_Name");
        robot.setSource_Code("");
        robotService.addRobot(robot);

        return "redirect:/robot/";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveRobot(@ModelAttribute("robot") Robot robot, BindingResult result) {

        robotService.addRobot(robot);

        return "redirect:/robot/";
    }

    @RequestMapping("/delete/{robot_Id}")
    public String deletePerson(@PathVariable("robot_Id") String robot_Id) {

        robotService.removeRobot(robot_Id);

        return "redirect:/robot/";
    }
}