package edu.utd.robocode;

import org.junit.Test;

public class TestRobocodeRunner
{

    @Test
    public void test()
    {
        new RobocodeRunner(new String[] {"sample.Corners", "edu.utd.robot.Honda*"});
    }

}
