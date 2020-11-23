package tests.RDC;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static tests.Config.region;


public class SwagRDSAndroidWebTest {

    private static ThreadLocal<AndroidDriver> androidDriver = new ThreadLocal<AndroidDriver>();
    private  ThreadLocal<String> sessionId = new ThreadLocal<>();

    String url = "https://www.saucedemo.com/";

    By usernameInput = By.id("user-name");
    By passwordInput = By.id("password");
    By submitButton = By.className("btn_action");
    By productTitle = By.className("product_label");


    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {

        System.out.println("Sauce Android Web - BeforeMethod hook");
        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");
        String sauceUrl;
        if (region.equalsIgnoreCase("eu")) {
            sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        } else {
            sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        }

        //String sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";

        String methodName = method.getName();
        URL url = new URL(SAUCE_REMOTE_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Samsung.*");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("cacheId", "1234");

        androidDriver.set(new AndroidDriver(url, capabilities));
        String id = ((RemoteWebDriver) getAndroidDriver()).getSessionId().toString();
        sessionId.set(id);
    }

    @AfterClass
    public void teardown() {

        try {
            System.out.println("Sauce - AfterMethod hook");
        //    ((JavascriptExecutor) getAndroidDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        } finally {
            System.out.println("Sauce - release driver");
            getAndroidDriver().quit();
        }
    }

    public  AndroidDriver getAndroidDriver() {
        return androidDriver.get();
    }

    @Test
    public void loginToSwagLabsWebTestValid() {
        System.out.println("Sauce - Start loginToSwagLabsTestValid test");
        AndroidDriver driver = getAndroidDriver();
        driver.get(url);
        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());
    }

    @Test
    public void loginToSwagLabsWebTestValid2() {
        System.out.println("Sauce - Start loginToSwagLabsTestValid test");
        AndroidDriver driver = getAndroidDriver();
        driver.get(url);
        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());
    }

    public void login(String user, String pass){
        AndroidDriver driver = getAndroidDriver();

        driver.findElement(usernameInput).sendKeys(user);
        driver.findElement(passwordInput).sendKeys(pass);

        driver.findElement(submitButton).click();

    }

    public boolean isOnProductsPage() {
        AndroidDriver driver = getAndroidDriver();

        return driver.findElement(productTitle).isDisplayed();
    }

}