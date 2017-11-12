package cvCommonUtils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen C-F Lin on 2017/10/21.
 */
public class ReportGenerator {

    public static void buildReport(String jsonReportDirectory, String reportOutputDirectory) {
        List<String> jsonReports = new ArrayList<>();
        File dir = new File(jsonReportDirectory);
        File[] files = dir.listFiles();

        if(!dir.isDirectory()) {
            throw new Error("NotDirectoryException");
        }

        if(files.length<1) {
            throw new Error("EmptyStackException: No files in the directory");
        }

        for(File file: files) {

            if(!file.isFile()) {
                continue;
            }

            if(!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {
                continue;
            }

            jsonReports.add(file.getAbsolutePath());
        }

        if(jsonReports.size()<1) {
            throw new Error("EmptyStackException: No json files");
        }

        buildReport(jsonReports, reportOutputDirectory);
    }

    public static void buildReport(List<String> jsonReports, String reportOutputDirectory) {
        File outputDirectory = new File(reportOutputDirectory);

        if(jsonReports.size()<1) {
            throw new Error("EmptyStackException: No json files");
        }

        String buildNumber = "-1";
        String projectName = "cucumber-testng-enzo";
        boolean runWithJenkins = false;
        boolean parallelTesting = false;
        boolean skippedFails = true;
        boolean pendingFails = true;
        boolean undefinedFails = true;
        boolean missingFails = true;
        boolean flashCharts = true;
        boolean highCharts = false;
        boolean artifactsEnabled = false;
        String artifactConfig = "";

        Configuration configuration = new Configuration(outputDirectory, projectName);
        configuration.setParallelTesting(parallelTesting);
        configuration.setRunWithJenkins(runWithJenkins);
        configuration.setBuildNumber(buildNumber);

        configuration.addClassifications("Platform", "Windows");
        configuration.addClassifications("Browser", "Firefox");
        configuration.addClassifications("Branch", "release/1.0");

        ReportBuilder reportBuilder = new ReportBuilder(jsonReports, configuration);
        Reportable result = reportBuilder.generateReports();
    }
}
