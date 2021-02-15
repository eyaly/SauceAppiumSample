package tests.Desktop;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
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

import static tests.Config.region;


public class Web_Desktop_Test {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {
        System.out.println("Sauce Desktop - BeforeMethod hook");
        String methodName = method.getName();
        URL url;

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

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("platformName", "Windows 10");
        capabilities.setCapability("browserVersion", "latest");

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("name", methodName);
        sauceOptions.setCapability("build", "build-test-5");
        sauceOptions.setCapability("tags", "web");

        capabilities.setCapability("sauce:options", sauceOptions);

        driver.set(new RemoteWebDriver(url, capabilities));

    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("Sauce - AfterMethod hook");
        try {
            ((JavascriptExecutor) getDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        } finally {
            System.out.println("Sauce - release driver");
            getDriver().quit();
        }
    }

    public  WebDriver getDriver() {
        return driver.get();
    }

    @Test
    public void checkSwagLabsTitle() {
        System.out.println("Sauce - Start checkSwagLabsTitle test");
        WebDriver driver = getDriver();


        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        Assert.assertEquals(getTitle, "Swag Labs");
    }

    @Test
    public void checkSwagLabsTitle2() {
        System.out.println("Sauce - Start checkSwagLabsTitle2 test");
        WebDriver driver = getDriver();


        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        Assert.assertEquals(getTitle, "Swag Labs");
    }
}