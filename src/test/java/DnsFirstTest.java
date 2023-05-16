import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class DnsFirstTest {
    public static void main(String[] args) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        String baseUrl = "https://www.dns-shop.ru/";

        driver.get(baseUrl);
        List<WebElement> elements = driver.findElements(By.xpath("//a[contains(@class, \"catalog-menu__root-item-info\")]"));
        elements.remove(elements.size() - 1);
        Actions actions = new Actions(driver);
        WebElement randomItem = randomElement(elements);
        System.out.println(randomItem.getText());
        actions.moveToElement(randomItem).perform();

        List<WebElement> subElements = driver.findElements(By.xpath("//span[@class = \"header-catalog-submenu-item__count\"]"));//a[@class = 'ui-link header-catalog-submenu-item']"));
        if (subElements.size() == 0) {
            System.out.println("There are no sub items");
        }
        WebElement randomElement = randomElement(subElements);
        actions.moveToElement(randomElement).perform();

        int commonValue = Integer.parseInt(randomElement.getText().trim());

        List<WebElement> items = driver.findElements(By.xpath("//span[@class = \"catalog-menu__popup-count\"]"));
        int sum = items.stream()
                .map(el -> Integer.parseInt(el.getText().trim()))
                .mapToInt(Integer::intValue).sum();
        System.out.println("Sum " + sum +" Common sum: " + commonValue);
        if (sum == 0) {
            System.out.println("There are no sub items");
            driver.close();
            return;
        }
        Assert.assertEquals(commonValue, sum, "Common amount of items doesn't equal to sum of sub items");
        //assert sum == commonValue;
        driver.close();
    }
    public static WebElement randomElement(List<WebElement> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}
