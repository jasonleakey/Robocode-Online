package edu.utd.robocode;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import robocode.BattleResults;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.BattleMessageEvent;
import robocode.control.events.BattleStartedEvent;

/**
 * BattleListener for handling the battle event we are interested in.
 */
public class BattleObserver extends BattleAdaptor
{
    private RoboSession session;
    
    public BattleObserver(RoboSession session)
    {
        super();
        this.session = session;
    }

    public void onBattleStarted(BattleStartedEvent e)
    {
        Logger.getAnonymousLogger().info("-- Battle was started --");
    }

    public void onBattleFinished(BattleFinishedEvent e)
    {
        if (e.isAborted())
        {
            Logger.getAnonymousLogger().info("-- Battle was aborted --");
        }
        else
        {
            Logger.getAnonymousLogger().info(
                    "-- Battle was finished succesfully --");
        }
    }

    public void onBattleCompleted(BattleCompletedEvent e)
    {
        Logger.getAnonymousLogger().info("-- Battle has completed --");

        List<BattleResults> results = new ArrayList<BattleResults>();

        // Print out the sorted results with the robot names
        Logger.getAnonymousLogger().info("\n-- Battle results --");
        for (BattleResults result : e.getSortedResults())
        {
            Logger.getAnonymousLogger().info(
                    "  " + result.getTeamLeaderName() + ": "
                            + result.getScore());
            // Transform the array to a list and return it to the session.
            results.add(result);
        }
        
        session.setResults(results);
        session.setSessionStatus("Completed");
    }

    public void onBattleMessage(BattleMessageEvent e)
    {
        Logger.getAnonymousLogger().info("Msg> " + e.getMessage());
    }

    public void onBattleError(BattleErrorEvent e)
    {
        Logger.getAnonymousLogger().info("Err> " + e.getError());
    }
}
