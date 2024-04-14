package automation.testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/*
 *@className - ExtentReporterNG
 * @classObjective- To define for reporting the logic in entire framework.
 */
public class ExtentReporterNG {
    //report generation with values added to displayed in report genrated by reporter.
    public static ExtentReports getReportObject(){
        Logger logger = LogManager.getLogger(ExtentReporterNG.class);;
        //providing path where file is created and config
        String path = System.getProperty("user.dir")+ "//reports//index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");
        reporter.config().setTheme(Theme.STANDARD);

        //(main) providing reporter(path and config deatils) for test execution
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester","Varsha Rane");
        //entry for created test (reporter test)
        extent.createTest(path);
        logger.info("report generated");
        return extent;
    }
}
