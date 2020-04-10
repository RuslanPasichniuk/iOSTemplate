package tests;

import conf.Configuration;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class TestEventsListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
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
        if (iTestContext.getFailedConfigurations().getAllResults().size() > 0) {
            System.err.println("There are Configuration Failures.");
            Runtime.getRuntime().halt(1);
        }
        if (iTestContext.getFailedTests().size() > 0) {
            StringBuilder sb = new StringBuilder();
            prepareReportHeader(sb, iTestContext);
            addTestFailuresToTestReport(sb, iTestContext);
            System.err.println(sb.toString());
            Runtime.getRuntime().halt(1);
        }
    }

    private void prepareReportHeader(StringBuilder reportString, ITestContext testCases) {
        reportString.append("\n\n===============================================\n");
        reportString.append(testCases.getSuite().getName());
        reportString.append("\nTotal tests run: ");
        reportString.append(testCases.getAllTestMethods().length);
        reportString.append(", Passes: ");
        reportString.append(testCases.getPassedTests().size());
        reportString.append(", Failures: ");
        reportString.append(testCases.getFailedTests().size());
        reportString.append(", Skips: ");
        reportString.append(testCases.getSkippedTests().size());
        reportString.append("\n===============================================\n\n");
    }

    private void addTestFailuresToTestReport(StringBuilder reportString, ITestContext testCases) {
        testCases.getFailedTests().getAllResults().forEach(iTestResult -> {
            reportString.append(">>> Test method failed: ")
                    .append(iTestResult.getTestClass().getName())
                    .append(".")
                    .append(iTestResult.getName())
                    .append("()\nFailed cause: ")
                    .append(iTestResult.getThrowable().getMessage()
                    );
            reportString.append("\n===========================================\n");
        });
    }
}
