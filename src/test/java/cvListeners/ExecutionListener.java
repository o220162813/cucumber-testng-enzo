package cvListeners;

import cvBrowserDrivers.BrowserFactory;
import cvBrowserDrivers.BrowserManager;
import cvCommonUtils.ReportGenerator;
import cvCommonUtils.Util;
import org.apache.commons.lang.BooleanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IExecutionListener;

import java.util.Properties;

/**
 * Created by Stephen C-F Lin on 2017/10/21.
 */
public class ExecutionListener implements IExecutionListener {
    public static Properties config = null;

    @Override
    public void onExecutionStart() {
        config = Util.readConfigurationPropertiesFromResourcePath("cvConfigurations//Configuration.properties");
    }

    @Override
    public void onExecutionFinish() {
        ReportGenerator.buildReport(config.getProperty("reportInputDirectory"),
                config.getProperty("reportOutputDirectory"));

        if(BooleanUtils.toBoolean(config.getProperty("shutDownBrowser"))) {
            BrowserFactory.quiteWebDriver(BrowserManager.createdDrivers);
        }
    }

}
