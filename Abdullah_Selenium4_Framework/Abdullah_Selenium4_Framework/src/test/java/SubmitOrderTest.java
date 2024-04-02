import TestComponents.TestBase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends TestBase {

    @Test(dataProvider = "getData" , groups = {"Purchase"})
    public void OrderItemTest(HashMap<String, String> input) {
        ProductCatalogue productCatalogue = loginPage.login(input.get("email"), input.get("password"));

        System.out.println("Products Retrieved: \n" + productCatalogue.getProductTitles());
        productCatalogue.getProductByName(input.get("productName"));
        productCatalogue.addItemToCart(input.get("productName"));
        CartPage cartPage = productCatalogue.goToCartPage();

        Assert.assertTrue(cartPage.checkItemAddedToCart(input.get("productName")));
        PaymentPage paymentPage = cartPage.checkout();

        paymentPage.fillShippingInformation(input.get("email"), "egypt");
        SummaryPage summaryPage = paymentPage.placeOrder();

        Assert.assertTrue(summaryPage.isSuccessMessageDisplayed());
        Assert.assertEquals(summaryPage.getSuccessMsg(), "THANKYOU FOR THE ORDER.");
        summaryPage.logOut();
    }

    @Test(dependsOnMethods = {"OrderItemTest"})
    public void CheckItemIsOrdered(){
        ProductCatalogue productCatalogue = loginPage.login("abdullah123@test.com", "P@ssword123");
        OrdersPage ordersPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(ordersPage.checkItemIsOrdered("ZARA COAT 3"));
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> mapList =  readJsonData(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\PurchaseOrder.json");

        return new Object[][] {{mapList.get(0)}, {mapList.get(1)}};
    }

    /**
     * @return 2D Array Object

     @DataProvider
    public Object[][] getData(){
        return new Object[][] {{"abdullah123@test.com", "P@ssword123", "ZARA COAT 3"}, {"anshika@gmail.com", "Iamking@000", "ADIDAS ORIGINAL"}};
    }


     * Return Data using Hashmap
     * HashMap<String, String> map = new HashMap<String, String>();
     * map.put("email", "abdullah123@test.com");
     * map.put("password", "P@ssword123");
     * map.put("productName", "ZARA COAT 3");
     *
     * HashMap<String, String> map2 = new HashMap<String, String>();
     *      * map2.put("email", "anshika@gmail.com");
     *      * map2.put("password", "Iamking@000");
     *      * map2.put("productName", "ADIDAS ORIGINAL");
     * return new Object[][] {{map, map2}};
     */
}
