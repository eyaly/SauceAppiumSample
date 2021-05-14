package tests.RDC;


import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static tests.Config.host;
import static tests.Config.region;
import static tests.helpers.Utils.waiting;


public class Appium_Android_RDC_App_Test {

    private static ThreadLocal<AndroidDriver> androidDriver = new ThreadLocal<AndroidDriver>();

    String usernameID = "test-Username";
    String passwordID = "test-Password";
    String submitButtonID = "test-LOGIN";
    By ProductTitle = By.xpath("//android.widget.TextView[@text='PRODUCTS']");
    String testMenu = "test-Menu";
    String testWebViewItem = "test-WEBVIEW";

    String urlEdit = "test-enter a https url here...";
    String goToSiteBtn = "test-GO TO SITE";
    By usernameInputWeb = By.id("user-name");

    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {

        System.out.println("Sauce Android Native - BeforeMethod hook");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        String methodName = method.getName();
        String appName = "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
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

            // https://" + username + ":" + accesskey@ondemand.eu-central-1.saucelabs.com:443/wd/hub
            String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl + "/wd/hub";
            url = new URL(SAUCE_REMOTE_URL);

            capabilities.setCapability("deviceName", "Samsung.*");
            capabilities.setCapability("app", "storage:filename=" + appName);
            capabilities.setCapability("name", methodName);
//        capabilities.setCapability("appiumVersion", "1.17.0");

              capabilities.setCapability("noReset", true);
              capabilities.setCapability("cacheId", "1234");
            List<String> tags = Arrays.asList("sauceDemo", "demoTest", "Android", "javaTest");
            capabilities.setCapability("tags", tags);
            capabilities.setCapability("build", "myBuild1");

        }
        else{
            // Run on local Appium Server
            capabilities.setCapability("deviceName", "2271469230027ece");
            capabilities.setCapability("app", "/Users/eyalyovel/Documents/sauce/demo/apps/android/" + appName);
            capabilities.setCapability("noReset", false);
            url = new URL("http://localhost:4723/wd/hub");
        }

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("orientation", "PORTRAIT");
        capabilities.setCapability("appWaitActivity", ".MainActivity");


        try {
            androidDriver.set(new AndroidDriver(url, capabilities));
        } catch (Exception e) {
            System.out.println("*** Problem to create the Android driver " + e.getMessage());
            throw new RuntimeException(e);
        }

        waiting(5);
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

        // Verificsation
        Assert.assertTrue(isOnProductsPage());
    }

    @Test
    public void webViewTest() {
        AndroidDriver driver = getAndroidDriver();
        System.out.println("Sauce - Start webViewTest test");

        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());

        WebElement menu = (WebElement) driver.findElementByAccessibilityId(testMenu);
        menu.click();

        WebElement webViewItem = (WebElement) driver.findElementByAccessibilityId(testWebViewItem);
        webViewItem.click();

        // set web url
        WebElement editURL = (WebElement) driver.findElementByAccessibilityId(urlEdit);
        editURL.click();
        editURL.sendKeys("https://www.saucedemo.com");

        WebElement goTo = (WebElement) driver.findElementByAccessibilityId(goToSiteBtn);
        goTo.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // in the webview page
        String currentContext = driver.getContext();
        System.out.println("*** current Context: " + currentContext);
        Set<String> contextNames = driver.getContextHandles();

        // print all contexts
        for (String contextName : contextNames) {
            System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
        }

        driver.context(contextNames.toArray()[1].toString()); // set context to WEBVIEW
        System.out.println("*** current Context: " + driver.getContext());

        driver.findElementByCssSelector("#user-name").sendKeys("sauce_standard");
        // ... Need to add the webView test

        // Back to native app context
        driver.context("NATIVE_APP");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void login(String user, String pass){
        AndroidDriver driver = getAndroidDriver();

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