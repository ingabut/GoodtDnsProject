package dnstest.base;

import dnstest.pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTests {

    private WebDriver driver;
    protected MainPage mp;
    int timeout = 5;

    @BeforeClass
    public void setUp() {
        DriverManager.initiate();
        driver = DriverManager.getDriverThread();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void goHome() {
        driver.get("https://www.dns-shop.ru/");
        mp = new MainPage();
    }

    @AfterMethod
    public void logout() {
        driver.manage().deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverManager.quit();
    }
}
