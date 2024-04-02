import TestComponents.Retry;
import TestComponents.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidationTest extends TestBase {

    @Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
    public void InvalidLoginCredentials_Test() {
        loginPage.login("abdullah123test@test.com", "P@ssword123");
        Assert.assertTrue(loginPage.isErrorMsgDisplayed());
        Assert.assertEquals(loginPage.getErrorMsgText(), "Incorrect email or password.");
    }

    @Test(groups = {"ErrorHandling"})
    public void InvalidEmailFormat_Test() {
        loginPage.login("abdullah123", "P@ssword123");
        Assert.assertTrue(loginPage.isValidationMsgDisplayed());
        Assert.assertEquals(loginPage.getValidationText(), "*Enter Valid Email");
    }
}
