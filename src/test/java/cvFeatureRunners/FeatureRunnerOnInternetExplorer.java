package cvFeatureRunners;

import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.*;

/**
 * Created by Stephen C-F Lin on 2017/10/21.
 */

@CucumberOptions(
        features = {"src/test/resources/cvFeatures/"},
        glue = {"cvStepDefinitions/"},
        tags = {"@TestingOnInternetExplorer"},
        format = {"pretty","html:target/cvRobot-RawHtmlResult1","json:target/cvRobot-RawJsonResult/report3.json"}
)

public class FeatureRunnerOnInternetExplorer extends AbstractTestNGCucumberTests {
}
