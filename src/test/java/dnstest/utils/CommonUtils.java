package dnstest.utils;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class CommonUtils {
    public static WebElement randomElement(List<WebElement> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size() - 1));
    }
}
