package pages;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractComponent {
    WebDriver driver;

    public LoginPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id="userEmail")
    private WebElement usernameField;

    @FindBy(id="userPassword")
    private WebElement passwordField;

    @FindBy(id="login")
    private WebElement loginBtn;

    @FindBy(css=".invalid-feedback div")
    private WebElement validationLabel;

    @FindBy(css="[class*='flyInOut']")
    private WebElement errorMessage;



    public ProductCatalogue login(String username, String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginBtn.click();

        return new ProductCatalogue(driver);

    }

    public void goToPage() {
        driver.get("https://rahulshettyacademy.com/client/");
    }

    public boolean isErrorMsgDisplayed(){
        waitForElementToBeVisible(errorMessage);
        return errorMessage.isDisplayed();
    }

    public String getErrorMsgText(){
        waitForElementToBeVisible(errorMessage);
        return errorMessage.getText();
    }

    public boolean isValidationMsgDisplayed(){
        waitForElementToBeVisible(validationLabel);
        return validationLabel.isDisplayed();
    }

    public String getValidationText(){
        waitForElementToBeVisible(validationLabel);
        return validationLabel.getText();
    }
}
