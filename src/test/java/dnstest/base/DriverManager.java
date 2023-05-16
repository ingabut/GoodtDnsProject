package dnstest.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<> ();

    private static WebDriverWait wait;

    public static WebDriver getDriverThread() {
        return driverThread.get();
    }

    public static void setDriverThread(WebDriver driver) {
        driverThread.set(driver);
    }

    public static void initiate() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(chromeOptions);
        setDriverThread(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public static void quit() {
        getDriverThread().quit();
    }

    public static WebDriverWait getWait() {
        return wait;
    }
}
