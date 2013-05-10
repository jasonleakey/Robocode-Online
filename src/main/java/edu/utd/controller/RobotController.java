package edu.utd.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import robocode.BattleResults;

import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

import edu.utd.model.Robot;
import edu.utd.model.Session;
import edu.utd.model.UserSummary;
import edu.utd.robocode.BattleObserver;
import edu.utd.robocode.RobocodeRunner;
import edu.utd.robocode.RobotApi;
import edu.utd.service.RobotService;
import edu.utd.service.SessionService;
import edu.utd.service.UserSummaryService;

@Controller
public class RobotController
{
    @Autowired
    private RobotService robotService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserSummaryService userSummaryService;

    private static List<String> robotList = new ArrayList<String>();

    private static List<BattleResults> results = new LinkedList<BattleResults>();

    private static Integer sessionCapacity = 0;

    private static String sessionName = "";

    private static String sessionStatus = "";

    @RequestMapping("/")
    public String listRobot(Map<String, Object> map)
    {
        map.put("robot", new Robot());
        map.put("robotList", robotService.listRobot());

        return "robot";
    }

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public @ResponseBody
    String joinGame(@RequestParam("robot_name") String robotName,
            @RequestParam("session_name") String sessionName,
            @RequestParam("session_capacity") Integer capacity)
    {
        if (null == robotName || null == sessionName)
        {
            return "error: Robot Name or Session Name missing.";
        }
        if (null != capacity)
        {
            results.clear();
            robotList.clear();
            RobotController.sessionStatus = "Waiting";
            RobotController.sessionCapacity = capacity;
            RobotController.sessionName = sessionName;
        }
        if (!RobotController.sessionName.equals(sessionName)
                || "Completed".equals(RobotController.sessionStatus))
        {
            return "error: Session " + sessionName
                    + " does not exist or it is completed.";
        }

        RobotController.sessionStatus = "Waiting";
        robotList.add(robotName);
        RobotApi.addAndCompileRobot(robotName);
        if (RobotController.sessionCapacity == robotList.size())
        {
            RobotController.sessionStatus = "Playing";
            for (int i = 0; i < robotList.size(); i++)
            {
                robotList.set(i, robotList.get(i) + "*");
            }
            BattleObserver listener = new BattleObserver();
            RobocodeRunner runner = new RobocodeRunner(listener);
            runner.run(robotList.toArray(new String[0]));
        }
        Thread thread = new Thread(new ResultUpdater(robotName,
                ForceSecurityContextHolder.get()));
        thread.start();
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

    public static void setGameResult(BattleResults[] battleresults)
    {
        for (BattleResults battleresult : battleresults)
        {
            results.add(battleresult);
        }
    }

    class ResultUpdater implements Runnable
    {
        private String robotName;

        private SecurityContext sc;

        public ResultUpdater(String robotName, SecurityContext sc)
        {
            this.robotName = robotName;
            this.sc = sc;
        }

        @Override
        public void run()
        {
            try
            {
                ForceSecurityContextHolder.set(sc);
                while (true)
                {
                    Thread.sleep(500);
                    updateResult();
                }
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        private synchronized void updateResult() throws NoSuchMethodException,
                SecurityException, IllegalAccessException,
                IllegalArgumentException, InvocationTargetException
        {
            for (BattleResults result : results)
            {
                if (result.getTeamLeaderName().equals(robotName + "*"))
                {
                    robotService.addScore(robotName, result.getScore());

                    UserSummary summary = new UserSummary();
                    // Get current time
                    summary.setRace_Time(Calendar.getInstance());
                    summary.setResult(result.getScore());
                    userSummaryService.addUserSummary(summary);

                    Session session = new Session();
                    session.setCapacity(sessionCapacity);
                    session.setRoomName(sessionName);
                    session.setStatus("Completed");
                    for (int i = 0; i < robotList.size(); i++)
                    {
                        Method method = Session.class.getMethod("setPlayer_"
                                + String.valueOf(i + 1), String.class);
                        StringBuilder buf = new StringBuilder(robotList.get(i));
                        // Remove the '*' character in the end;
                        method.invoke(session,
                                (buf.deleteCharAt(buf.length() - 1)).toString());
                    }
                    sessionService.addSession(session);

                    RobotController.sessionStatus = "Completed";
                    results.remove(result);
                    return;
                }
            }
        }
    }
}