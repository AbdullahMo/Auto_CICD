package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrdersPage extends AbstractComponent {
    WebDriver driver;

    public OrdersPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css="tbody td:nth-child(3)")
    private List<WebElement> orderNames;


    public boolean checkItemIsOrdered(String product){
        waitForElementsToBeVisible(orderNames);
        return orderNames.stream().anyMatch(p -> p.getText().equalsIgnoreCase(product));
    }
}
