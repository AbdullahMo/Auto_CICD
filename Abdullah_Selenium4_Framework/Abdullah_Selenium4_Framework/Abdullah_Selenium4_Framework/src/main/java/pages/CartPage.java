package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponent {
    WebDriver driver;

    public CartPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css=".cartSection h3")
    private List<WebElement> cartItems;

    @FindBy(css=".totalRow button")
    private WebElement checkoutBtn;

    public boolean checkItemAddedToCart(String product){
        waitForElementsToBeVisible(cartItems);
        return cartItems.stream().anyMatch(p -> p.getText().equalsIgnoreCase(product));
    }

    public PaymentPage checkout() {
        checkoutBtn.click();

        return new PaymentPage(driver);
    }
}
