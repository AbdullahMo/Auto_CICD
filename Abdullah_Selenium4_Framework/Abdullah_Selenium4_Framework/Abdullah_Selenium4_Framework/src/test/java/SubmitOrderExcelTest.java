import ExcelDataReader.ExcelDataReader;
import TestComponents.TestBase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;

public class SubmitOrderExcelTest extends TestBase {

    @Test(dataProvider = "getExcelData", groups = {"Purchase"})
    public void OrderItemTest(String username, String password, String product) {
        ProductCatalogue productCatalogue = loginPage.login(username, password);

        System.out.println("Products Retrieved: \n" + productCatalogue.getProductTitles());
        productCatalogue.getProductByName(product);
        productCatalogue.addItemToCart(product);
        CartPage cartPage = productCatalogue.goToCartPage();

        Assert.assertTrue(cartPage.checkItemAddedToCart(product));
        PaymentPage paymentPage = cartPage.checkout();

        paymentPage.fillShippingInformation(username, "egypt");
        SummaryPage summaryPage = paymentPage.placeOrder();

        Assert.assertTrue(summaryPage.isSuccessMessageDisplayed());
        Assert.assertEquals(summaryPage.getSuccessMsg(), "THANKYOU FOR THE ORDER.");
        summaryPage.logOut();
    }

    @Test(dependsOnMethods = {"OrderItemTest"})
    public void CheckItemIsOrdered() {
        ProductCatalogue productCatalogue = loginPage.login("abdullah123@test.com", "P@ssword123");
        OrdersPage ordersPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(ordersPage.checkItemIsOrdered("ZARA COAT 3"));
    }

    @DataProvider
    public Object[][] getExcelData() throws IOException {
        ExcelDataReader data = new ExcelDataReader();
        return data.readExcelData("PurchaseOrder");
    }

}