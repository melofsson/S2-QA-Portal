package se.soprasteria.s2qaportal.GUITest.Main.PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import se.soprasteria.automatedtesting.webdriver.api.base.BasePageObject;
import se.soprasteria.automatedtesting.webdriver.helpers.driver.AutomationDriver;

public class Login extends BasePageObject {


    @FindBy(id = "username")
    WebElement usernNameField;
    @FindBy(id = "password")
    WebElement passwordField;
    @FindBy(id = "login")
    WebElement loginButton;

    public Login(AutomationDriver driver) {
        super(driver);
        defaultWebpageElementLocator(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return false;
    }

    public void setPassword(String password) {
        elementHelper.sendKeysWithControlledSpeed(passwordField, password, 0);
    }

    public void setUsername(String username) {
        elementHelper.sendKeysWithControlledSpeed(usernNameField, username, 0);
    }

    public void clickLogin() {
        elementHelper.clickWithinTime(loginButton,2000);
    }
}
