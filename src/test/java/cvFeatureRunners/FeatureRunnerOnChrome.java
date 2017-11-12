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
        tags = {"@TestingOnChrome"},
        format = {"pretty","html:target/RawHtmlResult1","json:target/RawJsonResult/report1.json"}
)

public class FeatureRunnerOnChrome extends AbstractTestNGCucumberTests {
}
