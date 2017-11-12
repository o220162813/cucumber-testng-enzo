package PageObjects.Google;

import PageObjects.BasePage;
import cvBrowserDrivers.BrowserManager;
import cvCommonUtils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Created by Stephen C-F Lin on 2017/10/21.
 */
public class Home extends BasePage {
    //declare class variables
    public static final String url = "https://www.google.com";

    //declare xpath
    public static final String PAGE_CHECKER = "//title[text()='Google']";
    public static final String TEXT_SEARCH_FIELD = "//input[@name='q']";
    public static final String CBTN_SEARCH_SUBMIT = "//input[@name='btnK']";

    //declare WebElement
    @FindBy(xpath=TEXT_SEARCH_FIELD)
    WebElement text_searchField;

    public void enterSearchText(String searchtext) {
        //check whether google page is loaded
        Assert.assertTrue(isPageLoaded(),
                "[ERROR] " + "[" + this.getClass().getName() + "]: " + "Google page is not loaded" );

        //check the existence of google search field
        Assert.assertTrue(Util.isElementPresent(driver, TEXT_SEARCH_FIELD),
                "[ERROR] " + "[" + this.getClass().getName() + "]: " + "Google search field is not present");

        //enter the search text
        text_searchField.clear();
        text_searchField.click();
        text_searchField.sendKeys(searchtext);
    }

    public static Boolean isPageLoaded() {
        //declare method variables
        Boolean isLoaded = true;
        WebDriver driver = BrowserManager.getDriver();

        //verify whether the page is loaded
        isLoaded &= Util.isElementPresent(driver, PAGE_CHECKER);

        //return the verification
        return isLoaded;
    }
}
