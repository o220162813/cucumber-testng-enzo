package cvCommonUtils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * Created by Stephen C-F Lin on 2017/10/21.
 */
public class Util {
    public static Boolean isElementPresent(WebDriver driver, String xpath) {
        return isElementPresent(driver, By.xpath(xpath));
    }

    public static Boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size()>0;
    }

    public static byte[] takeScreenshot(WebDriver driver) {
        if (!(driver instanceof TakesScreenshot)) {
            return null;
        }

        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static Properties readConfigurationPropertiesFromResourcePath(String relativepath) {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream(relativepath);
            properties.load(input);
        } catch (IOException e) {
            throw new Error("Unable to read properties file at " + relativepath);
        }
        return properties;
    }

    public static void pause() {
        pause(1000);
    }

    public static void pause(long millis) {
        try {
            //perform a thread sleep
            Thread.sleep(millis);
        } catch(Exception e) {
            throw new Error("[ERROR] Error occurred when performing sleep");
        }
    }
}
