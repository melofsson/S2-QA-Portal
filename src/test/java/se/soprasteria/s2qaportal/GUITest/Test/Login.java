package se.soprasteria.s2qaportal.GUITest.Test;

import org.testng.annotations.Test;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;
import se.soprasteria.s2qaportal.GUITest.Main.TestBase;

public class Login extends TestBase {

    final String USERNAME = "s2qaportal";
    final String PASSWORD = "portaltobe";

    @Test (timeOut = 180000, dataProvider="getDriver")
    public void login(AutomationDriver driver) {
        initPages(driver);
        login.setUsername(USERNAME);
        login.setPassword(PASSWORD);
        login.clickLogin();
    }

}
