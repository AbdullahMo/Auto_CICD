package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ProductCatalogue extends AbstractComponent {
    WebDriver driver;

    public ProductCatalogue(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css=".mb-3")
    private List<WebElement> products;

    @FindBy(css="#toast-container")
    private WebElement addedToCartToast;


    private By addToCartBtn = By.cssSelector("button[class*='w-10']");


    public List<WebElement> getProductList(){
        waitForElementsToBeVisible(products);
        return products;
    }

    public WebElement getProductByName(String product){
        return getProductList().stream().filter(p -> p.findElement(By.cssSelector("b")).getText().contains(product)).findFirst().orElse(null);
    }

    public List<String> getProductTitles(){
        return getProductList().stream().map(product -> product.findElement(By.cssSelector("b")).getText()).collect(Collectors.toList());
    }


    public void addItemToCart(String item){
        WebElement product = getProductByName(item);
        product.findElement(addToCartBtn).click();
        waitForElementToBeVisible(addedToCartToast);
        waitForElementToBeInvisible(addedToCartToast);
    }




}
