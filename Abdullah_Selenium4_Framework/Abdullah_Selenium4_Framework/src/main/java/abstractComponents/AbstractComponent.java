package abstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.LoginPage;
import pages.OrdersPage;

import java.time.Duration;
import java.util.List;

public class AbstractComponent {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "[routerlink*='cart']")
    WebElement shoppingCartLink;

    @FindBy(css = "[routerlink*='myorders']")
    WebElement ordersLink;

    @FindBy(css=".fa-sign-out")
    WebElement logOutBtn;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void waitForElementsToBeVisible(List<WebElement> elements){
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void waitForElementToBeVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeInvisible(WebElement element){
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForByElementToBeClickable(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public CartPage goToCartPage(){
        shoppingCartLink.click();

        return new CartPage(driver);
    }

    public OrdersPage goToOrdersPage(){
        ordersLink.click();

        return new OrdersPage(driver);
    }

    public LoginPage logOut(){
        logOutBtn.click();
        return new LoginPage(driver);
    }
}
