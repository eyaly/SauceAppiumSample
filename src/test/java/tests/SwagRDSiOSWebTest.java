package tests;


import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class SwagRDSiOSWebTest {

    private static ThreadLocal<IOSDriver> iosDriver = new ThreadLocal<IOSDriver>();
    private  ThreadLocal<String> sessionId = new ThreadLocal<>();

    String url = "https://www.saucedemo.com/";

    By usernameInput = By.id("user-name");
    By passwordInput = By.id("password");
    By submitButton = By.className("btn_action");
    By productTitle = By.className("product_label");


    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {

        System.out.println("Sauce iOS Web - BeforeMethod hook");
        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");

        String sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";

        String methodName = method.getName();
        URL url = new URL(SAUCE_REMOTE_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "iPhone 8*");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCuiTest");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("cacheId", "1234");

        iosDriver.set(new IOSDriver(url, capabilities));
        String id = ((RemoteWebDriver) getiosDriver()).getSessionId().toString();
        sessionId.set(id);
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("Sauce - AfterMethod hook");
        ((JavascriptExecutor)getiosDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getiosDriver().quit();
    }

    public  IOSDriver getiosDriver() {
        return iosDriver.get();
    }

    @Test
    public void loginToSwagLabsWebTestValid() {
        System.out.println("Sauce - Start loginToSwagLabsWebTestValid test");
        IOSDriver driver = getiosDriver();
        driver.get(url);

        login("standard_user", "secret_sauce");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Verificsation
        Assert.assertTrue(isOnProductsPage());

    }

    public void login(String user, String pass){
        IOSDriver driver = getiosDriver();

        driver.findElement(usernameInput).sendKeys(user);
        driver.findElement(passwordInput).sendKeys(pass);

        driver.findElement(submitButton).click();

    }

    public boolean isOnProductsPage() {
        IOSDriver driver = getiosDriver();

        return driver.findElement(productTitle).isDisplayed();
    }

}