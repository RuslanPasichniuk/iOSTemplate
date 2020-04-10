package utils;

import conf.Configuration;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

public class AppiumInstance {
    private AppiumDriverLocalService service;
    private IOSDriver<IOSElement> driver;
    private String UDID;

    public AppiumInstance(String wdaPort, String simulatorPort, String iOSVersion, String deviceName) {
        runAppiumService(wdaPort);
        DesiredCapabilities capabilities = prepareDriverCapabilities(simulatorPort, iOSVersion, deviceName);
        driver = new IOSDriver<>(service.getUrl(), capabilities);
        UDID = driver.getCapabilities().getCapability("udid").toString();
    }

    public IOSDriver<IOSElement> getDriver() {
        return driver;
    }

    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
        try {
            Runtime.getRuntime().exec("/usr/bin/xcrun simctl delete " + UDID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runAppiumService(String wdaPort) {
        service = new AppiumServiceBuilder()
                .usingPort(Integer.parseInt(wdaPort))
                .withArgument(() -> "--allow-insecure", "get_server_logs")
                .withArgument(() -> "--log-level", "error")
                .build();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started");
        }
    }

    private DesiredCapabilities prepareDriverCapabilities(String simulatorPort, String iOSVersion, String deviceName) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(IOSMobileCapabilityType.SHOW_XCODE_LOG, false);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, iOSVersion);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, simulatorPort);
        capabilities.setCapability("appium-version", "1.16.0");
        capabilities.setCapability("autoAcceptAlerts", false);
        capabilities.setCapability("noReset", true);
        capabilities.setCapability(MobileCapabilityType.APP, Configuration.APP);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 30000);
        return capabilities;
    }
}
