package se.soprasteria.s2qaportal.GUITest.Main;

import se.soprasteria.automatedtesting.webdriver.api.base.BaseTestCase;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;
import se.soprasteria.s2qaportal.GUITest.Main.PageObjects.Login;

public class TestBase extends BaseTestCase {

    protected Login login;

    public void initPages(AutomationDriver driver){
        login = new Login(driver);

    }

    @Override
    protected void initializeDriver(AutomationDriver driver) throws Exception {
        if (driver.isWeb()){
            driver.manage().window().maximize();
        }
        driver.navigate().to("https://snapshot-s2-qa-portal.herokuapp.com/");
    }

    @Override
    protected String getDefaultDriverConfig() {
        return "local_chrome";
    }

    @Override
    protected String getDefaultPropertyFile() {
        return "config.xml";
    }
}
