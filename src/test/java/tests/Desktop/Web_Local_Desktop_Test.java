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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static tests.Config.region;


public class Web_Local_Desktop_Test {

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
        sauceOptions.setCapability("tunnelIdentifier", System.getenv("TUNNEL_NAME"));
        sauceOptions.setCapability("build", System.getenv("BUILD_NAME"));
        List<String> tags = Arrays.asList("sauceLocalDemo", "Web", "javaTest");
        sauceOptions.setCapability("tags", tags);

        capabilities.setCapability("sauce:options", sauceOptions);

        driver.set(new RemoteWebDriver(url, capabilities));
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("Sauce - AfterMethod hook");
        try {
            ((JavascriptExecutor) getDriver()).executeScript("sauce:job-result=" +
                    (result.isSuccess() ? "passed" : "failed"));
            // built the execution URL
            String executionURL = "";
            if (region.equalsIgnoreCase("eu")) {
                executionURL = "https://app.eu-central-1.saucelabs.com/tests/";
            } else {
                executionURL = "https://app.saucelabs.com/tests/";
            }
            String sessionID = ((RemoteWebDriver)getDriver()).getSessionId().toString();
            System.out.println("The execution URL:");
            System.out.println(executionURL + sessionID);

        } finally {

            System.out.println("Sauce - release driver");
            getDriver().quit();
        }
    }

    public  WebDriver getDriver() {
        return driver.get();
    }

    @Test
    public void checkLocalSwagLabsTitle() {
        System.out.println("Sauce - Start checkLocalSwagLabsTitle test");
        WebDriver driver = getDriver();


        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        Assert.assertEquals(getTitle, "Swag Labs");
    }

    @Test
    public void checkLocalSwagLabsTitle2() {
        System.out.println("Sauce - Start checkLocalSwagLabsTitle2 test");
        WebDriver driver = getDriver();


        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        Assert.assertEquals(getTitle, "Swag Labs");
    }

    public static void setEnv(String key, String value) {
        try {
            Map<String, String> env = System.getenv();
            Class<?> cl = env.getClass();
            Field field = cl.getDeclaredField("m");
            field.setAccessible(true);
            Map<String, String> writableEnv = (Map<String, String>) field.get(env);
            writableEnv.put(key, value);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to set environment variable", e);
        }
    }
}