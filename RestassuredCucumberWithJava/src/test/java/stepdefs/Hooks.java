package stepdefs;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    public static Scenario scenario;
    static final Logger logger = LogManager.getLogger(Hooks.class);
    @Before
    public void beforeScenario(Scenario scenarioObj){
        scenario = scenarioObj;
        logInfo("\n ***** "+ scenario.getName() + " Test Scenerio Started ***** \n");
    }

    @After
    public void afterScenario(){
        logInfo("\n ***** "+ scenario.getName() + " Test Scenerio Completed *****\n");
    }

    public static void cucumberReportInfoLog(String messageText) {
        logInfo(messageText);
        cucumberLogger(messageText);
    }

    public static void cucumberReportErrorLog(String messageText) {

        logError(messageText);
        cucumberLogger(messageText);
    }

    private static void cucumberLogger(String messageText)
    {
        scenario.write("\n"+ messageText +"\n");
    }

    private static void logInfo(String messageText)
    {
        logger.info("\n"+ messageText +"\n");
    }

    private static void logError(String messageText)
    {
        logger.error("\n"+ messageText +"\n");
    }
}
