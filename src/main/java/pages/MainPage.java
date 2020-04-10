package pages;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class MainPage extends BasePage {
    private IOSDriver<IOSElement> driver;
    // Page factory disabled due to Appium issues
    // https://github.com/serenity-bdd/serenity-core/issues/1053
    /*
    public MainPage(IOSDriver<IOSElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.of(5, ChronoUnit.SECONDS)), this);
    }
    // Error: Can't locate an element by this strategy: By.id: alertsButton
    @iOSXCUITBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' AND label CONTAINS 'Alerts'")
    private IOSElement alertsButton;*/
    public MainPage(IOSDriver<IOSElement> driver) {
        this.driver = driver;
        alertsButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Alerts'");
        deadlockAppButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Deadlock app'");
        attributesButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Attributes'");
        scrollingButton = findByPredicate(driver, "type == 'XCUIElementTypeButton' && label CONTAINS 'Scrolling'");
        portraitLabel = findByPredicate(driver, "type == 'XCUIElementTypeStaticText' && label CONTAINS 'Portrait'");
        verifyMainPage();
    }

    private IOSElement alertsButton;
    private IOSElement deadlockAppButton;
    private IOSElement attributesButton;
    private IOSElement scrollingButton;
    private IOSElement portraitLabel;

    public AlertsPage goToTheAlertsScreen() {
        alertsButton.click();
        return new AlertsPage(driver);
    }

    private void verifyMainPage() {
        verifyElement(alertsButton);
        verifyElement(deadlockAppButton);
        verifyElement(attributesButton);
        verifyElement(scrollingButton);
        verifyElement(portraitLabel);
    }
}
