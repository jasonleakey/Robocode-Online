package edu.utd.robocode;

import org.junit.Assert;
import org.junit.Test;

public class TestCompileRobot
{
    @Test
    public void test()
    {
        Assert.assertEquals(RobotApi.compileRobot(FileUtils.read_file("/home/jasonleakey/Robocode/Robocode-SaaS/robots/edu/utd/robot/Jason1.java")), true);
    }
}
