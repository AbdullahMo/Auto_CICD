package cucumber.stepDefinitions;

import TestComponents.TestBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.*;

import java.io.IOException;

public class MyStepdefs1 extends TestBase {
    public LoginPage loginPage;
    public ProductCatalogue productCatalogue;
    public CartPage cartPage;
    public PaymentPage paymentPage;
    public SummaryPage summaryPage;


    @Given("I landed on ECommerce page")
    public void iLandedOnECommercePage() throws IOException {
        loginPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void loggedInWithUsernameAndPassword(String username, String password) {
        productCatalogue = loginPage.login(username, password);

    }

    @When("^I add product (.+) to Cart$")
    public void iAddProductProductNameToCart(String productName) {
        productCatalogue.getProductByName(productName);
        productCatalogue.addItemToCart(productName);
    }

    @When("^Checkout (.+) and submit order$")
    public void checkoutProductNameAndSubmitOrder(String productName) {
        cartPage = productCatalogue.goToCartPage();

        Assert.assertTrue(cartPage.checkItemAddedToCart(productName));
        paymentPage = cartPage.checkout();

        paymentPage.fillShippingInformation("abdullah123@testmc.com", "egypt");
        summaryPage = paymentPage.placeOrder();
    }


    @Then("{string} message is displayed on ConfirmationPage")
    public void messageIsDisplayedOnConfirmationPage(String arg0) {
        Assert.assertTrue(summaryPage.isSuccessMessageDisplayed());
        Assert.assertEquals(summaryPage.getSuccessMsg(), arg0);
        summaryPage.logOut();
        driver.close();
    }
}
