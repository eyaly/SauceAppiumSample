package tests.crossDevices.listeners;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.Config;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static tests.Config.region;

public class BaseTestListener implements ITestListener {

    private static ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<AppiumDriver>();
    private static ThreadLocal<Boolean> bAndroid = new ThreadLocal<>();

    // Locators
    public String usernameID = "test-Username";
    public String passwordID = "test-Password";
    public String submitButtonID = "test-LOGIN";
    public By android_ProductTitle = By.xpath("//android.widget.TextView[@text='PRODUCTS']");
    public By iOS_ProductTitle = By.xpath("//XCUIElementTypeStaticText[@name=\"PRODUCTS\"]\n");


    @Override
    public void onTestStart(ITestResult iTestResult) {
        String testName = iTestResult.getName();
        System.out.println("Sauce - In Hook onTestStart. Test name: " + testName);

        // Get all test parameter from testng xml file
        Map<String, String> allParameters = iTestResult.getTestContext().getCurrentXmlTest().getAllParameters();

        try {
            createSauceDriver(allParameters, testName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Sauce - In Hook onTestSuccess");

        try {
            updateTestResult(iTestResult);
        }
        finally {
            getDriver().quit();
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("Sauce - In Hook onTestFailure");

        try {
            updateTestResult(iTestResult);
        }
        finally {
            getDriver().quit();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Sauce - In Hook onTestSkipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Sauce - In Hook onTestFailedButWithinSuccessPercentage");
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("Sauce - In Hook onStart");

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("Sauce - In Hook onFinish");

    }

    private void updateTestResult(ITestResult iTestResult) {
        ((JavascriptExecutor)getDriver()).executeScript("sauce:job-result=" + (iTestResult.isSuccess() ? "passed" : "failed"));
    }

    public boolean isAndroidPlatform(){
        System.out.println("Sauce isAndroidPlatform. android = " + bAndroid);
        return bAndroid.get();
    }

    private void setPlatform(String platform)
    {
        System.out.println("Sauce setPlatform. platform = " + platform);
        Boolean isAndroid = platform.equalsIgnoreCase("android");
        bAndroid.set(isAndroid);
    }

    private void createSauceDriver(Map<String, String> allParameters, String testName) throws MalformedURLException {

        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");
        String sauceUrl;
        if (Config.region.equalsIgnoreCase("eu")) {
            sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        } else {
            sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        }


        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        URL url = new URL(SAUCE_REMOTE_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();

        // set the capabilities from the test ng parameters
        allParameters.forEach((k,v)->{

            if (v.equals("true") || v.equals("false")){
                // boolean
                capabilities.setCapability(k, Boolean.parseBoolean(v));
            } else {
                capabilities.setCapability(k, v);
            }
        });

        capabilities.setCapability("name", testName);
        setPlatform(capabilities.getCapability("platformName").toString());

        try {
            appiumDriver.set(new AppiumDriver(url, capabilities));
        } catch (Exception e) {
            System.out.println("*** Problem to create the driver " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public  AppiumDriver getDriver() {
        return appiumDriver.get();
    }
}
