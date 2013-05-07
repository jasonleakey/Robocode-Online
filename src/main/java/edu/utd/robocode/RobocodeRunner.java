package edu.utd.robocode;

/*******************************************************************************
 * Copyright (c) 2001-2013 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************/

import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

/**
 * Sample application that runs two sample robots in Robocode.
 * 
 * @author Flemming N. Larsen (original)
 */
public class RobocodeRunner
{
    public static void main(String[] args)
    {
        new RobocodeRunner(new String[]{"edu.utd.robot.Jason2*", "edu.utd.robot.Yang3*"});
    }
    
    public RobocodeRunner(String[] robots)
    {
        // Battle listener used for receiving battle events
        BattleObserver battleListener = new BattleObserver();

        // Create the RobocodeEngine
        // IRobocodeEngine engine = new RobocodeEngine(new
        // java.io.File("C:/Robocode")); // Run from C:/Robocode
        RobocodeEngine engine = new RobocodeEngine(); // Run from current
                                                      // working directory

        // Add battle listener to our RobocodeEngine
        engine.addBattleListener(battleListener);

        // Show the battles
        engine.setVisible(false);

        // Setup the battle specification

        int numberOfRounds = 5;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(
                800, 600); // 800x600

        Logger.getAnonymousLogger().info(RobocodeEngine.getRobotsDir().toString());

        RobotSpecification[] selectedRobots = engine
                .getLocalRepository(StringUtils.join(robots, ','));
        // RobotSpecification[] selectedRobots = engine.getLocalRepository();
        for (RobotSpecification robot : selectedRobots)
        {
            System.out.print(robot.getName() + ", ");
        }
        // RobotSpecification[] selectedRobots =
        // engine.getLocalRepository("tested.robots.BattleLost,tested.robots.EnvAttacks");

        BattleSpecification battleSpec = new BattleSpecification(
                numberOfRounds, battlefield, selectedRobots);

        // Run our specified battle and let it run till it's over
        engine.runBattle(battleSpec, true/* wait till the battle is over */);
    }
}
