package tests;

import exec.TestsExecutor;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.testng.Reporter;
import org.testng.annotations.*;
import utils.AppiumInstance;

import java.io.IOException;
import java.util.HashMap;

public class BaseTest {
    public static HashMap<String, AppiumInstance> simulators = new HashMap<>();

    @BeforeTest
    @Parameters({"wdaPort", "simulatorPort", "iOSVersion", "deviceName"})
    public void setUpAppium(String wdaPort, String simulatorPort, String iOSVersion, String deviceName) {
        initDriver(wdaPort, simulatorPort, iOSVersion, deviceName);
    }

    @AfterTest
    @Parameters({"iOSVersion", "deviceName"})
    public void tearDownSimulator(String iOSVersion, String deviceName) {
        simulators.get(iOSVersion + deviceName).teardown();
    }

    @AfterSuite
    public void tearDownAllSimulators() {
        System.out.println("_______________________________________________" +
                "\nTotal elapsed time:" +
                "\n" +
                TestsExecutor.stopwatch +
                "\n_______________________________________________");
        try {
            String[] killAllAppiumServices = {"/bin/bash", "-c", "for pid in $(" +
                    "ps -A " +
                    "| grep appium " +
                    "| grep 'main.js --port ' " +
                    "| awk '{print $1}'" +
                    "); do kill -9 $pid; done"};
            Runtime.getRuntime().exec(killAllAppiumServices);

            String[] deleteAllAppiumSimulators = {"/bin/bash", "-c", "for udid in $(" +
                    "/usr/bin/xcrun simctl list " +
                    "| grep 'appiumTest-' " +
                    "| grep '(Booted)' " +
                    "| cut -d \"(\" -f2 " +
                    "| cut -d \")\" -f1" +
                    "); do /usr/bin/xcrun simctl delete $udid; done"};
            Runtime.getRuntime().exec(deleteAllAppiumSimulators);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IOSDriver<IOSElement> getDriver() {
        String iOSVersion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("iOSVersion");
        String deviceName = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("deviceName");
        return simulators.get(iOSVersion + deviceName).getDriver();
    }

    private static void initDriver(String wdaPort, String simulatorPort, String iOSVersion, String deviceName) {
        AppiumInstance ai = new AppiumInstance(wdaPort, simulatorPort, iOSVersion, deviceName);
        simulators.put(iOSVersion + deviceName, ai);
    }
}
