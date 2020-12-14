package tests.crossDevices;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tests.crossDevices.listeners.BaseTestListener;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static tests.Config.region;


public class SwagRDSCrossDevicesTest extends BaseTestListener {

    @Test
    public void loginToSwagLabsTestValid() {
        System.out.println("Sauce - Start loginToSwagLabsTestValid test. isAndrroid: " + isAndroidPlatform() + " .Session " + getDriver().getSessionId().toString());

        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());
    }

    @Test
    public void loginTestValidProblem() {
        System.out.println("Sauce - Start loginTestValidProblem test. isAndrroid: " + isAndroidPlatform() + " .Session " + getDriver().getSessionId().toString());

        login("problem_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());
    }

    public void login(String user, String pass){
        AppiumDriver driver = getDriver();
        System.out.println("login 1. Session is: " + driver.getSessionId().toString());

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
        AppiumDriver driver = getDriver();
        System.out.println("isOnProductsPage 1. Session is: " + driver.getSessionId().toString());
        //Create an instance of a Selenium explicit wait so that we can dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, 5);

        //wait for the product field to be visible and store that element into a variable
        try {
            if (isAndroidPlatform()) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(android_ProductTitle));
            }
            else {
                wait.until(ExpectedConditions.visibilityOfElementLocated(iOS_ProductTitle));
            }
        } catch (TimeoutException e){
            return false;
        }
        return true;
    }

}