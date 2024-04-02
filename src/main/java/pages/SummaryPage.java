package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SummaryPage extends AbstractComponent {
    WebDriver driver;

    public SummaryPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(className="hero-primary")
    private WebElement successMsg;

    @FindBy(id="back-to-products")
    private WebElement backToHomeBtn;



    public boolean isSuccessMessageDisplayed(){
        return successMsg.isDisplayed();
    }

    public String getSuccessMsg(){
        return successMsg.getText();
    }


}
