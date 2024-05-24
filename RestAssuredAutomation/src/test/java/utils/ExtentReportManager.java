package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentTest;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initializeReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReports/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    public static ExtentTest createTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
