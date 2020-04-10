package pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BasePage {
    public static IOSElement findByPredicate(IOSDriver<IOSElement> driver, String predicate) {
        return driver.findElementByIosNsPredicate(predicate);
        /*(IOSElement) new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.iOSNsPredicateString(predicate)));*/
    }

    public static void verifyElement(IOSElement element) {
        assertTrue(element.isDisplayed());
        assertTrue(element.isEnabled());
        assertFalse(element.isSelected());
    }
}
