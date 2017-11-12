package cvBrowserDrivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen C-F Lin on 2017/10/21.
 */
public class BrowserManager {
    public static List<WebDriver> createdDrivers = new ArrayList<WebDriver>();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    public static ThreadLocal<String> browserName = new ThreadLocal<String>();
    public static ThreadLocal<String> version =  new ThreadLocal<String>();
    public static ThreadLocal<String> platform  = new ThreadLocal<String>();

    public static WebDriver getDriver() {
        return getDriver(null);
    }

    public static WebDriver getDriver(String browserName) {
        if(driver.get()==null) {
            if(browserName==null) {
                setWebDriver(BrowserFactory.initialiseBrowser());
            } else {
                setWebDriver(BrowserFactory.initialiseBrowser(browserName));
            }
        }

        return driver.get();
    }

    public static void setWebDriver(WebDriver webDriver) {
        driver.set(webDriver);

        browserName.set(((RemoteWebDriver) webDriver).getCapabilities().getBrowserName());
        version.set(((RemoteWebDriver) webDriver).getCapabilities().getVersion());
        platform.set(((RemoteWebDriver) webDriver).getCapabilities().getPlatform().toString());

        createdDrivers.add(webDriver);
    }

    public static String getDriverInfo(String s) {
        return ((RemoteWebDriver) BrowserManager.getDriver()).getCapabilities().getCapability(s).toString();
    }

    public static WebDriver reinitialiseWebDriver() {
        if(driver.get()==null) {
            throw new Error("To reinitialise WebDriver, it must not be null");
        }

        if(browserName.get()==null) {
            throw new Error("To reinitialise WebDriver, browser name must not be null");
        }

        if(browserName.get().isEmpty()) {
            throw new Error("To reinitialise WebDriver, browser name must not be empty");
        }

        String[] remoteBrowserProperties= {"platform", platform.get(), "version", version.get(), "urlRemoteDriver", null};
        driver.get().close();
        driver.set(BrowserFactory.initialiseBrowser(browserName.get(), remoteBrowserProperties));

        return driver.get();
    }
}
