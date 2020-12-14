package tests.crossDevices;


import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.crossDevices.listeners.BaseTestListener;


public class SwagRDSCrossDevices2Test extends BaseTestListener {

    @Test
    public void loginToSwagLabsTestValid2() {
        System.out.println("Sauce - Start loginToSwagLabsTestValid2 test. isAndrroid: " + isAndroidPlatform() + " .Session " + getDriver().getSessionId().toString());

        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());
    }

    @Test
    public void loginTestValidProblem2() {
        System.out.println("Sauce - Start loginTestValidProblem2 test. isAndrroid: " + isAndroidPlatform() + " .Session " + getDriver().getSessionId().toString());

        login("problem_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());
    }


    public void login(String user, String pass){
        AppiumDriver driver = getDriver();
        System.out.println("login 2. Session is: " + driver.getSessionId().toString());
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
        System.out.println("isOnProductsPage 2. Session is: " + driver.getSessionId().toString());
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