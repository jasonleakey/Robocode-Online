package edu.utd.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
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
import edu.utd.robocode.RoboSession;
import edu.utd.robocode.RobotApi;
import edu.utd.robocode.SessionManager;
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

    @RequestMapping("/")
    public String listRobot(Map<String, Object> map)
    {
        map.put("robot", new Robot());
        map.put("robotList", robotService.listRobot());
        map.put("sessionList", SessionManager.listSessions());

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
            if (!SessionManager.createSession(sessionName, capacity))
            {
                return "error: Session exists, please use another name";
            }
        }
        if (!SessionManager.isSessionExisted(sessionName))
        {
            return "error: Session " + sessionName + " does not exist.";
        }

        SessionManager.addRobotToSession(robotName, sessionName);
        RobotApi.addAndCompileRobot(robotName);
        
        String response = "joined";
        if (SessionManager.isReady(sessionName))
        {
            SessionManager.runSession(sessionName);
            response = "Game started!";
        }
        // For saving results to Force.com
        Thread thread = new Thread(new ResultUpdater(robotName, sessionName,
                ForceSecurityContextHolder.get()));
        thread.start();
        return response;
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

    class ResultUpdater implements Runnable
    {
        private String robotName;

        private String sessionName;

        private SecurityContext sc;

        public ResultUpdater(String robotName, String sessionName,
                SecurityContext sc)
        {
            this.robotName = robotName;
            this.sessionName = sessionName;
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
                    if (updateResult())
                    {
                        return;
                    }
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

        private synchronized boolean updateResult() throws NoSuchMethodException,
                SecurityException, IllegalAccessException,
                IllegalArgumentException, InvocationTargetException
        {
            RoboSession roboSession = SessionManager.getSession(sessionName);
            if (null == roboSession)
            {
                return false;
            }

            BattleResults result = roboSession.findResult(robotName);
            if (null == result)
            {
                return false;
            }
            robotService.addScore(robotName, result.getScore());

            UserSummary summary = new UserSummary();
            // Get current time
            summary.setRace_Time(Calendar.getInstance());
            summary.setResult(result.getScore());
            userSummaryService.addUserSummary(summary);

            Session session = new Session();
            session.setCapacity(roboSession.getSessionCapacity());
            session.setRoomName(roboSession.getSessionName());
            session.setStatus(roboSession.getSessionStatus());
            for (int i = 0; i < roboSession.getRobotList().size(); i++)
            {
                Method method = Session.class.getMethod(
                        "setPlayer_" + String.valueOf(i + 1), String.class);
                method.invoke(session, roboSession.getRobot(i));
            }
            sessionService.addSession(session);

            roboSession.delResult(result);
            if (roboSession.isEnded())
            {
                SessionManager.destroySession(sessionName);
            }
            return true;
        }
    }
}