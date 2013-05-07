package edu.utd.robocode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

import robocode.control.RobocodeEngine;
import edu.utd.model.Robot;
import edu.utd.service.RobotService;
import edu.utd.service.RobotServiceImpl;

public class RobotApi
{
    public static boolean compileRobot(String source)
    {
        File f = RobocodeEngine.getRobotsDir();
        Logger.getAnonymousLogger().info("Robot Dir is " + f.toString());
        Logger.getAnonymousLogger().info(
                "java.class.path:" + System.getProperty("java.class.path"));

        Pattern p = Pattern.compile("(?<=package ).*(?=;)");
        Matcher m = p.matcher(source);
        String packageName = new String();
        if (m.find())
        {
            packageName = m.group().replaceAll("\\.", File.separator);
        }
        else
        {
            Logger.getAnonymousLogger().severe(
                    "Could not create folder for Robot! Check you Robot code.");
        }
        // create robot dir.
        new File(f.getAbsolutePath() + File.separator + packageName).mkdirs();

        p = Pattern.compile("(?<=public class )\\w*");
        m = p.matcher(source);
        String robotClassName = new String();
        if (m.find())
        {
            robotClassName = m.group();
        }

        try
        {
            // create robot source file.
            FileUtils.writeStringToFile(new File(f.getAbsolutePath()
                    + File.separator + packageName + File.separator
                    + robotClassName + ".java"), source);

            String robotDir = f.getAbsolutePath() + File.separator
                    + packageName;
            Logger.getAnonymousLogger().info("robotDir: " + robotDir);

            String robotFile = f.getAbsolutePath() + File.separator
                    + packageName + File.separator + robotClassName + ".java";
            Logger.getAnonymousLogger().info("robotFile: " + robotFile);

            // List all the files.
            Logger.getAnonymousLogger().info(
                    System.getProperty("user.dir") + "====>");
            List<File> files = (List<File>) FileUtils.listFiles(
                    new File(System.getProperty("user.dir")), null, true);
            for (File file : files)
            {
                // Logger.getAnonymousLogger().info("file:" + file);
            }

            // compile the robot
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            StandardJavaFileManager fileManager = compiler
                    .getStandardFileManager(diagnostics, null, null);
            Iterable<? extends JavaFileObject> compilationUnits = fileManager
                    .getJavaFileObjectsFromStrings(Arrays.asList(robotFile));
            List<String> optionList = new ArrayList<String>();
            // set compiler's classpath to be same as the runtime's
            optionList.addAll(Arrays.asList("-classpath",
                    System.getProperty("java.class.path")));
            JavaCompiler.CompilationTask task = compiler.getTask(null,
                    fileManager, diagnostics, null, null, compilationUnits);
            boolean success = task.call();
            for (Diagnostic diagnostic : diagnostics.getDiagnostics())
            {
                Logger.getAnonymousLogger()
                        .severe(String
                                .format("Error on line %d in %s, kind=%s, message=%s%n",
                                        diagnostic.getLineNumber(),
                                        ((JavaFileObject) diagnostic
                                                .getSource()).toUri(),
                                        diagnostic.getKind(), diagnostic
                                                .getMessage(Locale.ENGLISH)));
            }
            fileManager.close();

            System.out.println("Success: " + success);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean addAndCompileRobot(String robotName)
    {
        RobotService robotService = new RobotServiceImpl();
        Robot robot = robotService.getRobot(robotName);
        return compileRobot(robot.getSource_Code());
    }

    public static void main(String[] args)
    {
        try
        {
            RobotApi.compileRobot(FileUtils
                    .readFileToString(new File(
                            "/home/jasonleakey/Robocode/Robocode-SaaS/robots/edu/utd/robot/Yang3.java")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}