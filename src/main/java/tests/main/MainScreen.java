package tests.main;

import org.testng.annotations.Test;
import pages.MainPage;

import static tests.BaseTest.getDriver;

public class MainScreen {
    @Test
    public void verifyMainScreen() {
        new MainPage(getDriver())
                .goToTheAlertsScreen()
                .goBackToTheHomeScreen();
    }
}
