package conf;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.github.cdimascio.dotenv.Dotenv;

public class Configuration {
    private static Dotenv dotenv = Dotenv
            .configure()
            .ignoreIfMissing()
            .load();
    public static IOSDriver<IOSElement> driver;
    public static final String [] IOS = get("COMPONENT_PARAM_IOS").split(",");
    public static final String [] PHONES = get("COMPONENT_PARAM_PHONES").split(",");
    public static final String APP = get("COMPONENT_PARAM_IOS_APP");
    public static final int TESTNG_THREADS = Integer.parseInt(get("COMPONENT_PARAM_TESTNG_THREADS"));

    private static String get(String parameterName) {
        String property = System.getProperty(parameterName);
        if (property == null) {
            property = dotenv.get(parameterName);
        }
        return property;
    }
}
