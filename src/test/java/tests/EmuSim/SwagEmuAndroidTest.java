package tests.EmuSim;


import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static tests.Config.host;
import static tests.Config.region;


public class SwagEmuAndroidTest {

    private static ThreadLocal<AndroidDriver> androidDriver = new ThreadLocal<AndroidDriver>();
    private  ThreadLocal<String> sessionId = new ThreadLocal<>();

    String usernameID = "test-Username";
    String passwordID = "test-Password";
    String submitButtonID = "test-LOGIN";
    By ProductTitle = By.xpath("//android.widget.TextView[@text='PRODUCTS']");


    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {
        System.out.println("Sauce Android Native - BeforeMethod hook");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String methodName = method.getName();
        String appName = "Android.SauceLabs.Mobile.Sample.app.2.3.0.apk";
        URL url;

        if (host.equals("saucelabs")) {
            String username = System.getenv("SAUCE_USERNAME");
            String accesskey = System.getenv("SAUCE_ACCESS_KEY");
            String sauceUrl;
            if (region.equalsIgnoreCase("eu")) {
                sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
            } else {
                sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
            }
            String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl + "/wd/hub";
            url = new URL(SAUCE_REMOTE_URL);

            capabilities.setCapability("deviceName", "Android Emulator");
            capabilities.setCapability("platformVersion", "8.0");
            capabilities.setCapability("app", "storage:filename=" + appName);
            capabilities.setCapability("name", methodName);
            capabilities.setCapability("appiumVersion", "1.18.1");
        } else {
            // Run on local Appium Server
            capabilities.setCapability("deviceName", "emulator-5554");
            capabilities.setCapability("app", "/Users/eyalyovel/Documents/sauce/demo/apps/android/" + appName);
            url = new URL("http://localhost:4723/wd/hub");
        }

        capabilities.setCapability("appActivity", "com.swaglabsmobileapp.MainActivity");
        //        capabilities.setCapability("appWaitActivity", "com.swaglabsmobileapp.MainActivity");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");

        try {
            androidDriver.set(new AndroidDriver(url, capabilities));
        } catch (Exception e) {
            System.out.println("*** Problem to create the Android driver " + e.getMessage());
            throw new RuntimeException(e);
        }

        String id = ((RemoteWebDriver) getAndroidDriver()).getSessionId().toString();
        sessionId.set(id);
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("Sauce - AfterMethod hook");
        try {
            if (host.equals("saucelabs")) {
                ((JavascriptExecutor) getAndroidDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            }
        } finally {
            System.out.println("Sauce - release driver");
            getAndroidDriver().quit();
        }
    }

    public  AndroidDriver getAndroidDriver() {
        return androidDriver.get();
    }

    @Test
    public void loginToSwagLabsTestValid() {
        System.out.println("Sauce - Start loginToSwagLabsTestValid test");

        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());
    }

    @Test
    public void loginTestValidProblem() {
        System.out.println("Sauce - Start loginTestValidProblem test");

        login("problem_user", "secret_sauce");

        // Verificsation - we on Product page
        Assert.assertTrue(isOnProductsPage());
    }

    public void login(String user, String pass){
        AndroidDriver driver = getAndroidDriver();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        final WebElement usernameEdit = wait.until(ExpectedConditions.visibilityOfElementLocated(new MobileBy.ByAccessibilityId(usernameID)));

        //WebElement usernameEdit = (WebElement) driver.findElementByAccessibilityId(usernameID);
        usernameEdit.click();
        usernameEdit.sendKeys(user);

        WebElement passwordEdit = (WebElement) driver.findElementByAccessibilityId(passwordID);
        passwordEdit.click();
        passwordEdit.sendKeys(pass);

        WebElement submitButton = (WebElement) driver.findElementByAccessibilityId(submitButtonID);
        submitButton.click();
    }

    public boolean isOnProductsPage() {
        AndroidDriver driver = getAndroidDriver();
        //Create an instance of a Selenium explicit wait so that we can dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, 5);

        //wait for the product field to be visible and store that element into a variable
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ProductTitle));
        } catch (TimeoutException e){
            return false;
        }
        return true;
    }

}