package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src\\test\\java\\cucumber", glue = "cucumber.stepDefinitions",
monochrome = true,tags = "@Regression", plugin = {"html:target/cucumber.html"})
public class TestNGRunner extends AbstractTestNGCucumberTests {

}
