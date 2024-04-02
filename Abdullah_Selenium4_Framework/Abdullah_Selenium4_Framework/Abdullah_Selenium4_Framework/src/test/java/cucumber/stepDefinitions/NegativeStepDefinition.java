package cucumber.stepDefinitions;

import TestComponents.TestBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.LoginPage;

import java.io.IOException;

public class NegativeStepDefinition extends TestBase {
    LoginPage loginPage;

    @Given("I landed on ECommerce pagee")
    public void iLandedOnECommercePagee() throws IOException {
        loginPage = launchApplication();
    }
    @Given("^I Log in with invalid username (.+) or password (.+)$")
    public void iLogInWithInvalidUsernameNameOrPasswordPassword(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("{string} message is displayed on LoginPage")
    public void messageIsDisplayedOnLoginPage(String arg0) {
        Assert.assertTrue(loginPage.isErrorMsgDisplayed());
        Assert.assertEquals(loginPage.getErrorMsgText(), arg0);
        driver.close();
    }
}
