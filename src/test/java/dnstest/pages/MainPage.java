package dnstest.pages;

import dnstest.base.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

import static dnstest.utils.CommonUtils.randomElement;

public class MainPage {
    @FindBy(xpath = "//a[contains(@class, \"catalog-menu__root-item-info\")]")
    private List<WebElement> elements;

    @FindBy(xpath = "//span[@class = \"header-catalog-submenu-item__count\"]")
    private List<WebElement> subCountElements;

    //@FindBy(xpath = "//a[@class = \"ui-link header-catalog-submenu-item\"]")
    //private List<WebElement> subElements;

    @FindBy(xpath = "//span[@class = \"catalog-menu__popup-count\"]")
    private List<WebElement> items;

    private static final Logger LOG = LoggerFactory.getLogger(MainPage.class);

    public MainPage() {
        PageFactory.initElements(DriverManager.getDriverThread(), this);
    }
    private static final WebDriverWait wait = DriverManager.getWait();

    @Step("Assert amount of items to be equal to total amount")
    public void assertAmounts() {
        Actions actions = new Actions(DriverManager.getDriverThread());
        WebElement randomItem = randomElement(elements);
        actions.moveToElement(randomItem).perform();
        LOG.info(String.format("Hover over an element \"%s\"", randomItem.getText()));
        wait.until(ExpectedConditions.visibilityOfAllElements(subCountElements));
        //LOG.info("textContent: " + subElements.get(0).getAttribute("textContent"));
        if (subCountElements.size() == 0) {
            LOG.info("There are no sub items");
            Allure.addAttachment("Result", "There are no sub items");
            return;
        }
        WebElement randomElement = randomElement(subCountElements);
        actions.moveToElement(randomElement).perform();
        LOG.info(String.format("Hover over an element with amount %s", randomElement.getText()));
        int totalValue = Integer.parseInt(randomElement.getText().trim());

        int sum = items.stream()
                .map(el -> Integer.parseInt(el.getText().trim()))
                .mapToInt(Integer::intValue).sum();
        if (sum == 0) {
            LOG.info("There are no sub items");
            Allure.addAttachment("Result", "There are no sub items");
            return;
        }
        Allure.addAttachment("Total amount: ", String.valueOf(totalValue));
        Allure.addAttachment("Sum of sub categories: ", String.valueOf(sum));

        LOG.info(String.format("Total amount: %d Sum of sub categories: %d", totalValue, sum));

        Assert.assertEquals(totalValue, sum, "Common amount of items doesn't equal to sum of sub items");
    }

}
