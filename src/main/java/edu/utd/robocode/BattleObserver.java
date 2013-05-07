package edu.utd.robocode;

import net.sf.robocode.io.Logger;
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
    public void onBattleStarted(BattleStartedEvent e)
    {
        Logger.logMessage("-- Battle was started --");
    }

    public void onBattleFinished(BattleFinishedEvent e)
    {
        if (e.isAborted())
        {
            Logger.logMessage("-- Battle was aborted --");
        }
        else
        {
            Logger.logMessage("-- Battle was finished succesfully --");
        }
    }

    public void onBattleCompleted(BattleCompletedEvent e)
    {
        Logger.logMessage("-- Battle has completed --");

        // Print out the sorted results with the robot names
        Logger.logMessage("\n-- Battle results --");
        for (BattleResults result : e.getSortedResults())
        {
            Logger.logMessage("  " + result.getTeamLeaderName() + ": "
                    + result.getScore());
        }
    }

    public void onBattleMessage(BattleMessageEvent e)
    {
        Logger.logMessage("Msg> " + e.getMessage());
    }

    public void onBattleError(BattleErrorEvent e)
    {
        Logger.logMessage("Err> " + e.getError());
    }
}
