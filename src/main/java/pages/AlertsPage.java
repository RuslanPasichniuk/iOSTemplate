package pages;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class AlertsPage extends BasePage {
    private IOSDriver<IOSElement> driver;
    public AlertsPage(IOSDriver<IOSElement> driver) {
        this.driver = driver;
        backButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Back'");
        textField = findByPredicate(driver, "type == 'XCUIElementTypeTextField'");
        decoratedButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Button'");
        staticLabel = findByPredicate(driver, "type == 'XCUIElementTypeStaticText' && label CONTAINS 'Label'");
        createAppAlertButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Create App Alert'");
        createNotificationAlertButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Create Notification Alert'");
        createCameraRollAlertButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Create Camera Roll Alert'");
        createGPSAccessAlertButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Create GPS access Alert'");
        createSheetAlertButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Create Sheet Alert'");
        createAlertForceTouchButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Create Alert (Force Touch)'");
        verifyAlertsPage();
    }

    private IOSElement backButton;
    private IOSElement textField;
    private IOSElement decoratedButton;
    private IOSElement staticLabel;
    private IOSElement createAppAlertButton;
    private IOSElement createNotificationAlertButton;
    private IOSElement createCameraRollAlertButton;
    private IOSElement createGPSAccessAlertButton;
    private IOSElement createSheetAlertButton;
    private IOSElement createAlertForceTouchButton;

    public MainPage goBackToTheHomeScreen() {
        backButton.click();
        return new MainPage(driver);
    }

    private void verifyAlertsPage() {
        verifyElement(backButton);
        verifyElement(textField);
        verifyElement(decoratedButton);
        verifyElement(staticLabel);
        verifyElement(createAppAlertButton);
        verifyElement(createNotificationAlertButton);
        verifyElement(createCameraRollAlertButton);
        verifyElement(createGPSAccessAlertButton);
        verifyElement(createSheetAlertButton);
        assertTrue(createAlertForceTouchButton.isDisplayed());
        assertFalse(createAlertForceTouchButton.isEnabled());
        assertFalse(createAlertForceTouchButton.isSelected());
    }
}
