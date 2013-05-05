package edu.utd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.utd.model.Robot;
import edu.utd.robocode.RobocodeRunner;
import edu.utd.robocode.RobotApi;
import edu.utd.service.RobotService;
import edu.utd.service.SessionService;

@Controller
public class RobotController
{

    @Autowired
    private RobotService robotService;

    @Autowired
    private SessionService sessionService;

    private static List<String> robotList = new ArrayList<String>();

    private static String session_id = "";

    @RequestMapping("/")
    public String listRobot(Map<String, Object> map)
    {
        // new RobocodeRunner(new String[] { "sample.RamFire",
        // "edu.utd.robot.Honda*" });

        map.put("robot", new Robot());
        map.put("robotList", robotService.listRobot());

        return "robot";
    }

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String joinGame(@RequestParam("robot_name") String robotName)
    {
        // ClassLoader loader = ClassLoader.getSystemClassLoader();
        // URL[] urls = ((URLClassLoader) loader).getURLs();
        // for (URL url : urls)
        // {
        // // System.out.println(url.getFile());
        // Logger.logMessage("Running class path:" + url.getFile());
        // Logger.logMessage("user.dir:" + System.getProperty("user.dir"));
        // }
        // Logger.logMessage("Robot Directory: " +
        // RobocodeEngine.getRobotsDir());
        robotList.add(robotName);
        RobotApi.addAndCompileRobot(robotName);
        if (2 == robotList.size())
        {
            // Session session = new Session();
            // session.setCapacity(6);
            // session.setRoomName("Blue");
            // session.setPlayer_Number(2);
            // session.setStatus("Playing");
            // session.setPlayers_1(robotList.get(0));
            // session.setPlayers_2(robotList.get(1));
            // session.setPlayers_3(robotList.get(2));
            // session.setPlayers_4(robotList.get(3));
            // session.setPlayers_5(robotList.get(4));
            // session.setPlayers_6(robotList.get(5));
            // session_id = sessionService.addSession(session);
            for (int i = 0; i < robotList.size(); i++)
            {
                robotList.set(i, robotList.get(i) + "*");
            }
            new RobocodeRunner(robotList.toArray(new String[0]));
            return "Game start";
        }
        return "joined";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addRobot()
    {
        Robot robot = new Robot();
        robotService.addRobot(robot);

        return "redirect:/robot/";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveRobot(@ModelAttribute("robot") Robot robot,
            BindingResult result)
    {

        robotService.addRobot(robot);

        return "redirect:/robot/";
    }

    @RequestMapping("/delete/{robot_Id}")
    public String deletePerson(@PathVariable("robot_Id") String robot_Id)
    {

        robotService.removeRobot(robot_Id);

        return "redirect:/robot/";
    }
}