package tests.RDC;


import io.appium.java_client.Setting;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Appium_iOS_RDC_App_Test {

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
        String appName = "iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa";

        String methodName = method.getName();
        URL url = new URL(SAUCE_REMOTE_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "iPhone 8.*");
//        capabilities.setCapability("privateDevicesOnly", "true");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCuiTest");
        capabilities.setCapability("app", "storage:filename="+appName);
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("cacheId", "1234");
        capabilities.setCapability("tags", "sauceDemo1");
        capabilities.setCapability("build", "myBuild1");
        try {
            iosDriver.set(new IOSDriver(url, capabilities));
        } catch (Exception e) {
            System.out.println("*** Problem to create the iOS driver " + e.getMessage());
            throw new RuntimeException(e);
        }
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
    public void loginToSwagLabsTestValid() {
        System.out.println("Sauce - Start loginToSwagLabsTestValid test");
        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());

    }

    @Test
    public void loginToSwagLabsTestValid2() {
        System.out.println("Sauce - Start loginToSwagLabsTestValid test");
        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());

    }

    public void login(String user, String pass){
        IOSDriver driver = getiosDriver();

//        WebElement usernameEdit = (WebElement) driver.findElementByAccessibilityId(usernameID);
        List<IOSElement> usernameEdit = (List<IOSElement>) driver.findElementsByAccessibilityId(usernameID);

        usernameEdit.get(0).click();
        usernameEdit.get(0).sendKeys(user);


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