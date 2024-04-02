package TestComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class TestBase {
    public WebDriver driver;
    public LoginPage loginPage;

    public WebDriver initializeDriver() throws IOException {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\GlobalData.properties");
        Properties properties = new Properties();
        properties.load(fis);

        String browserName = System.getProperty("browser")!=null ? System.getProperty("browser"):properties.getProperty("browser");

        if(browserName.contains("chrome")){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            if(browserName.contains("headless")){
                options.addArguments("headless");
            }
            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public LoginPage launchApplication() throws IOException {
        driver = initializeDriver();
        loginPage = new LoginPage(driver);
        loginPage.goToPage();
        return loginPage;
    }
    @AfterMethod(alwaysRun = true)
    public void quitBrowser(){
        driver.quit();
    }

    public List<HashMap<String, String>> readJsonData(String filePath) throws IOException {
        //Read File to String
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

        //Convert String to HashMap using Jackson Databind
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
    }

    public String takeScreenShot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "//screenshots//" + testCaseName + ".png"));
        return System.getProperty("user.dir") + "//screenshots//" + testCaseName + ".png";
    }
}
