package TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import resources.ExtentReporterNG;

import java.io.IOException;

public class Listener extends TestBase implements ITestListener {
    ExtentReports extentReports = ExtentReporterNG.getReportObject();
    ExtentTest test;
    ThreadLocal<ExtentTest> testThread = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extentReports.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "Test Successfully Passed!");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testThread.get().fail(result.getThrowable());

        //Get Driver Instance for Taking a Screenshot
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }

        String filePath = null;
        //Taking a Screenshot
        try {
            filePath = takeScreenShot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        testThread.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        //ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
       // ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
