package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PaymentPage extends AbstractComponent {
    WebDriver driver;

    public PaymentPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css=".user__name.mt-5 input")
    private WebElement userEmail;

    @FindBy(css="[placeholder*='Select']")
    private WebElement country;

    private By countryList = By.cssSelector("span[class*='ng-star-inserted']");

    @FindBy(css=".action__submit")
    private WebElement placeOrderBtn;

    public void fillShippingInformation(String email, String countryName){
        userEmail.sendKeys(email);
        country.sendKeys(countryName);

        waitForByElementToBeClickable(countryList);

        List<WebElement> countryList = driver.findElements(By.cssSelector("span[class*='ng-star-inserted']"));
        WebElement Egypt = countryList.stream().filter(country -> country.getText().equalsIgnoreCase(countryName)).findFirst().orElse(null);
        Egypt.click();

    }

    public SummaryPage placeOrder() {
        placeOrderBtn.click();
        return new SummaryPage(driver);
    }
}
