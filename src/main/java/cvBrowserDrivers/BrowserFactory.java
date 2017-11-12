package cvBrowserDrivers;

import cvCommonUtils.Util;
import org.apache.commons.lang.BooleanUtils;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by Stephen C-F Lin on 2017/10/21.
 */
public class BrowserFactory {
    private static final long implicitTimeout = 90;
    private static final String defaultBrowser = "Chrome";

    public static WebDriver initialiseBrowser() {
        return initialiseBrowser(defaultBrowser);
    }

    public static WebDriver initialiseBrowser(String browser) {
        return initialiseBrowser(browser, null);
    }

    public static WebDriver initialiseBrowser(String browser, String[] varargin) {
        WebDriver driver;
        Boolean isRemote = false;
        Platform platform = null;
        String version = null;
        String urlRemoteBrowser = "http://192.168.1.4:4444/wd/hub";

        if (varargin!=null) {
            if (varargin.length%2==1) {
                throw new Error("Input arguments must be an even number");
            }

            for (int k=0; k<varargin.length; k+=2) {
                //skip if the value is null
                if(varargin[k+1]==null) {
                    continue;
                }

                switch (varargin[k].toUpperCase()) {
                    case "ISREMOTE":
                        isRemote = BooleanUtils.toBoolean(varargin[k + 1]);
                        break;
                    case "URLREMOTEDRIVER":
                        urlRemoteBrowser = varargin[k+1];
                        break;
                    case "VERSION":
                        version = varargin[k+1];
                        break;
                    case "PLATFORM":
                        platform = str2platform(varargin[k+1]);
                        break;
                    default:
                        throw new Error("No such input arguments");
                }
            }
        }

        isRemote = System.getProperty("user.dir").contains("jenkins") & urlRemoteBrowser!=null;
        if (isRemote) {
            driver = initialiseRemoteWebDriver(browser, urlRemoteBrowser, version, platform);
        } else {
            driver = initialiseWebDriver(browser);
        }

        return driver;
    }

    public static WebDriver initialiseWebDriver(String browser) {
        WebDriver driver;
        DesiredCapabilities dc;

        if(browser==null) {
            browser = defaultBrowser;
        }

        switch (browser.toUpperCase()) {
            case "CHROME":
                dc = DesiredCapabilities.chrome();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("chrome.switches", "--disable-extensions");
                dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                dc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                System.setProperty("webdriver.chrome.driver",
                        "C:/Users/Stephen C-F Lin/Desktop/Cucumber Java - Test Automation/chromedriver.exe");

                driver = new ChromeDriver(dc);
                break;
            case "FIREFOX":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                dc = DesiredCapabilities.firefox();

                System.setProperty("webdriver.gecko.driver",
                        "C:/Users/Stephen C-F Lin/Desktop/Cucumber Java - Test Automation/geckodriver_win64_v0.18.exe");

                firefoxOptions.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
                firefoxOptions.setLogLevel(Level.OFF);

                dc.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
                dc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                dc.setCapability(FirefoxDriver.MARIONETTE, true);

                driver = new FirefoxDriver(dc);
                break;
            case "INTERNET EXPLORER":
                dc = DesiredCapabilities.internetExplorer();
                dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                dc.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, UnexpectedAlertBehaviour.ACCEPT);
                dc.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
                dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                dc.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                dc.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "https://www.google.com");
                dc.setCapability(InternetExplorerDriver.SILENT, true);

                System.setProperty("webdriver.ie.driver",
                        "C:/Users/Stephen C-F Lin/Desktop/Cucumber Java - Test Automation/IEDriverServer_win64_v3.4.exe");

                driver = new InternetExplorerDriver(dc);
                break;
            default:
                throw new Error("No such browser");
        }

        driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
        return driver;
    }

    public static WebDriver initialiseRemoteWebDriver(
            String browser, String urlRemoteWebDriver, String version, Platform platform) {
        WebDriver driver;
        DesiredCapabilities dc;

        if(browser==null) {
            browser = defaultBrowser;
        }

        switch (browser.toUpperCase()) {
            case "CHROME":
                dc = DesiredCapabilities.chrome();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("chrome.switches", "--disable-extensions");
                dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                dc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                break;
            case "FIREFOX":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                dc = DesiredCapabilities.firefox();

                firefoxOptions.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");

                dc.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
                dc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                dc.setCapability(FirefoxDriver.MARIONETTE, true);
                break;
            case "INTERNET EXPLORER":
                dc = DesiredCapabilities.internetExplorer();
                dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                dc.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, UnexpectedAlertBehaviour.ACCEPT);
                dc.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "https://www.google.com");
                dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                dc.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                break;
            default:
                throw new Error("No such browser");
        }

        if(version!=null) {
            dc.setCapability(CapabilityType.VERSION, version);
        }
        if(platform!=null) {
            dc.setCapability(CapabilityType.PLATFORM, platform);
        }

        try {
            driver = new RemoteWebDriver(new URL(urlRemoteWebDriver), dc);
            driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
            return driver;
        } catch (Exception e) {
            throw new Error("RemoteWebDriver cannot be initialised");
        }
    }

    public static Boolean hasWindowHandles(WebDriver webDriver) {
        try {
            return webDriver.getWindowHandles().size()>0;
        } catch(NoSuchSessionException e) {
            return false;
        }
    }

    public static void quiteWebDriver(List<WebDriver> webDrivers) {
        for(WebDriver webDriver: webDrivers) {
            quitWebDriver(webDriver);
        }
    }

    public static void quitWebDriver(WebDriver webDriver) {
        webDriver.quit();
    }

    private static Platform str2platform(String platform) {
        Platform value;

        switch(platform.toUpperCase()) {
            case "WINDOWS":
                value = Platform.WINDOWS;
            case "XP":
                value = Platform.XP;
            case "VISTA":
                value = Platform.VISTA;
            case "WIN8":
                value = Platform.WIN8;
            case "WIN10":
                value = Platform.WIN10;
            case "MAC":
                value = Platform.MAC;
            case "LINUX":
                value = Platform.LINUX;
            case "UNIX":
                value = Platform.UNIX;
            case "ANDROID":
                value = Platform.ANDROID;
            default:
                value = null;
        }
        return value;
    }
}
