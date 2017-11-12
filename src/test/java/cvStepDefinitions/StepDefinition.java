package cvStepDefinitions;

import PageObjects.BasePage;
import PageObjects.Google.Home;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cvBrowserDrivers.BrowserManager;
import cvCommonUtils.Util;
import cvCommonUtils.WebDriverControl;
import cvListeners.ExecutionListener;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.*;

/**
 * Created by Stephen C-F Lin on 2017/10/21.
 */
public class StepDefinition {
    protected Properties config = ExecutionListener.config;
    protected WebDriver driver = BrowserManager.getDriver();
    protected ThreadLocal<Map> customerdetail = new ThreadLocal<Map>();

    Home googleHome = null;

    @Given("^Tester goes to the \"([^\"]*)\" website$")
    public void givenAccessToWebSite(String url) {
        driver.navigate().to(url);

        if(url.contains("google")) {
            WebDriverControl.waitElementPresent(driver, Home.CBTN_SEARCH_SUBMIT);
            googleHome = BasePage.initPage(driver, Home.class);
        } else {
            throw new Error("[ERROR] undefined website to access");
        }

        customerdetail.set(new HashMap());
    }

    @When("^Tester enters \"([^\"]*)\" into the search field$")
    public void whenEnterSearchString(String value) {
        List<String> varargouts = new ArrayList<String>();
        varargouts.add(varargouts.size(), value);
        performAction("search via google", varargouts);

        customerdetail.get().put("googleSearchString", value);
    }

    @Then("^Tester verifies the search inputs are entered$")
    public void thenVerifySearchString() {
        if(!googleHome.isPageLoaded()) {
            throw new Error("[ERROR] Google home is not loaded");
        }

        Assert.assertEquals(driver.findElement(By.xpath(Home.TEXT_SEARCH_FIELD)).getAttribute("value"),
                customerdetail.get().get("googleSearchString"));
    }

    @Given("^Tester fails a test case$")
    public void givenFailATestCase() {
        Assert.fail("[ERROR] this test case is failed for demonstration purpose");
    }

    private void performAction(String action, List<String> varargins) {
        if(StringUtils.isBlank(action)) {
            throw new Error("[ERROR] 1st string input must be specified");
        }

        switch(action.toUpperCase()) {
            case "SEARCH VIA GOOGLE":
                if(varargins.size()!=1| StringUtils.isBlank(varargins.get(0))) {
                    throw new Error("[ERROR] 2nd string input must be specified");
                }
                googleHome.enterSearchText(varargins.get(0));
                break;
            default:
                throw new Error("[ERROR] undefined action to be performed");
        }
    }

    @Before()
    public void beforeScenarios(Scenario scenario) throws Exception {
        if(config==null) {
            config = Util.readConfigurationPropertiesFromResourcePath("cvConfigurations//Configuration.properties");
        }
    }

    @After()
    public void afterScenarios(Scenario scenario) {
        scenario.embed(Util.takeScreenshot(driver), "image/png");
    }
}
