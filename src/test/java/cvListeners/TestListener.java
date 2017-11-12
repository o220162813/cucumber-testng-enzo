package cvListeners;

import cvBrowserDrivers.BrowserFactory;
import cvBrowserDrivers.BrowserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Created by Stephen C-F Lin on 2017/10/25.
 */
public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    }

    @Override
    public void onTestFailure(ITestResult result) {
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
        String platform = context.getCurrentXmlTest().getLocalParameters().get("platform");
        String browserName = context.getCurrentXmlTest().getLocalParameters().get("browserName");
        String version = context.getCurrentXmlTest().getLocalParameters().get("version");

        String[] remoteBrowserProperties= {"platform", platform, "version", version, "urlRemoteDriver", null};
        WebDriver driver = BrowserFactory.initialiseBrowser(browserName, remoteBrowserProperties);
        BrowserManager.setWebDriver(driver);
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    private static String getTestMethodName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getName();
    }
}
