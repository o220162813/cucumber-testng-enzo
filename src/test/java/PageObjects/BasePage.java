package PageObjects;

import cvBrowserDrivers.BrowserManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Stephen C-F Lin on 2017/10/21.
 */
public class BasePage {
    protected WebDriver driver = BrowserManager.getDriver();

    public static <T> T initPage(WebDriver driver, Class<T> pageClassToProxy) {
        return PageFactory.initElements(driver, pageClassToProxy);
    }
}
