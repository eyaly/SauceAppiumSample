package tests.EmuSim;


import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Appium_iOS_SIM_App_Test {

    private static ThreadLocal<IOSDriver> iosDriver = new ThreadLocal<IOSDriver>();

    String usernameID = "test-Username";
    String passwordID = "test-Password";
    String submitButtonID = "test-LOGIN";
    By ProductTitle = By.xpath("//XCUIElementTypeStaticText[@name=\"PRODUCTS\"]\n");


    @BeforeMethod
       public void setup(Method method) throws MalformedURLException {

        System.out.println("Sauce iOS Native - BeforeMethod hook");
        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");

        String sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        String appName = "iOS.Simulator.SauceLabs.Mobile.Sample.app.2.3.0.zip";

        String methodName = method.getName();
        URL url = new URL(SAUCE_REMOTE_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","iPhone 8 Simulator");
        capabilities.setCapability("platformVersion","13.4");

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCuiTest");
        capabilities.setCapability("app", "storage:filename="+appName);
        capabilities.setCapability("name", methodName);

        iosDriver.set(new IOSDriver(url, capabilities));

    }

    @AfterMethod
     public void teardown(ITestResult result) {
        System.out.println("Sauce - AfterMethod hook");
      //  ((JavascriptExecutor)getiosDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getiosDriver().quit();
    }

    public  IOSDriver getiosDriver() {
        return iosDriver.get();
    }

    @Test
    public void loginToSwagLabsTestValid() {
        System.out.println("Sauce - Start loginToSwagLabsTestValid test");
        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());

    }

    @Test
    public void loginToSwagLabsTestValid2() {
        System.out.println("Sauce - Start loginToSwagLabsTestValid2 test");
        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());

    }

    public void login(String user, String pass){
        IOSDriver driver = getiosDriver();

        WebElement usernameEdit = (WebElement) driver.findElementByAccessibilityId(usernameID);

        usernameEdit.click();
        usernameEdit.sendKeys(user);


        WebElement passwordEdit = (WebElement) driver.findElementByAccessibilityId(passwordID);
        passwordEdit.click();
        passwordEdit.sendKeys(pass);

        WebElement submitButton = (WebElement) driver.findElementByAccessibilityId(submitButtonID);
        submitButton.click();
    }

    public boolean isOnProductsPage() {
        IOSDriver driver = getiosDriver();
        return driver.findElement(ProductTitle).isDisplayed();
    }

}