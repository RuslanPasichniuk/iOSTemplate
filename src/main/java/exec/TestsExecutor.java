package exec;

import com.google.common.base.Stopwatch;
import conf.Configuration;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.*;
import tests.TestEventsListener;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestsExecutor {
    public static Stopwatch stopwatch = Stopwatch.createStarted();

    public static void main(String[] args) {
        TestsExecutor executor = new TestsExecutor();
        executor.generateTestNGRunner().run();
    }

    private TestNG generateTestNGRunner() {
        XmlSuite suite = createTestNGSuite();
        generateTests(suite);
        TestNG runner = new TestNG();
        runner.setUseDefaultListeners(false);
        TestListenerAdapter tla = new TestListenerAdapter();
        runner.addListener(tla);
        runner.addListener(new TestEventsListener());
        runner.setXmlSuites(wrapSuiteInList(suite));
        return runner;
    }

    private XmlSuite createTestNGSuite() {
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG XML created from Java Code");
        suite.setParallel(XmlSuite.ParallelMode.TESTS);
        suite.setThreadCount(Configuration.TESTNG_THREADS);
        return suite;
    }

    private void generateTests(XmlSuite suite) {
        int simulatorPort = 4000;
        int wdaPort = 4100;

        for (int iosVersionNum = 0; iosVersionNum < Configuration.IOS.length; iosVersionNum++) {
            String iOS = Configuration.IOS[iosVersionNum];
            for (int simulatorNum = 0; simulatorNum < Configuration.PHONES.length; simulatorNum++) {
                String simulatorName = Configuration.PHONES[simulatorNum];

                XmlTest test = new XmlTest(suite);
                test.setName("Test on iOS v" + iOS + "; Simulator name: " + simulatorName + "; Appium simulator port: " + simulatorPort + "; WDAPort: " + wdaPort);
                test.addParameter("simulatorPort", "" + (simulatorPort++));
                test.addParameter("wdaPort", "" + wdaPort++);
                test.addParameter("iOSVersion", iOS);
                test.addParameter("deviceName", simulatorName);
                List<XmlPackage> packages = new LinkedList<>();
                packages.add(new XmlPackage("tests.*"));
                packages.add(new XmlPackage("tests.*.*"));
                test.setXmlPackages(packages);

                simulatorPort = getFreePortFrom(simulatorPort + 1);
                wdaPort = getFreePortFrom(wdaPort + 1);
                if (simulatorPort == wdaPort) simulatorPort = getFreePortFrom(simulatorPort + 1);
            }
        }
    }

    private List<XmlSuite> wrapSuiteInList(XmlSuite suite) {
        List<XmlSuite> suiteFiles = new ArrayList<>();
        suiteFiles.add(suite);
        return suiteFiles;
    }

    private int getFreePortFrom(int startingPort) {
        while (startingPort <= 5000) {
            try (Socket ignored = new Socket("localhost", startingPort)) {
            } catch (IOException e) {
                return startingPort;
            }
            startingPort++;
        }
        throw new RuntimeException("All HTTP ports 4000 - 5000 are busy!");
    }
}
