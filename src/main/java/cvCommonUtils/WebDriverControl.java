package cvCommonUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Stephen C-F Lin on 2017/10/23.
 */
public class WebDriverControl {
    private static final Integer MAX_ATTEMPTS = 10;
    private static final long MAX_TIME_OUT_IN_SECONDS = 90;

    public static void waitElementPresent(WebDriver driver, String xpath) {
        waitElementPresent(driver, By.xpath(xpath), MAX_TIME_OUT_IN_SECONDS);
    }

    public static void waitElementPresent(WebDriver driver, By locator, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void sendKeysAndCheck(WebElement webElement, String s) {
        int currentAttempts = 0;

        while(true) {
            if(currentAttempts>=MAX_ATTEMPTS) {
                throw new Error("Attempts to perform sendKeys reach the maximum attempts");
            }

            if(webElement.getText().equals(s) | webElement.getAttribute("valuse").equals(s)) {
                return;
            }

            webElement.click();
            webElement.clear();
            webElement.sendKeys(s);

            pause(500);

            currentAttempts += 1;
        }
    }

    private static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch(Exception e) {
            throw new Error("[ERROR] Error occurred when performing sleep");
        }
    }
}
