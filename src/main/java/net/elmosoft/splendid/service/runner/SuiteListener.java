package net.elmosoft.splendid.service.runner;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IConfigurationListener;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import io.qameta.allure.Attachment;
import net.elmosoft.splendid.browser.DriverManager;
import net.elmosoft.splendid.browser.VideoManager;
import net.elmosoft.splendid.utils.ScreenshotUtils;

/**
 * @author Aleksei_Mordas
 *
 *         e-mail: * alexey.mordas@gmail.com Skype: alexey.mordas
 */
public class SuiteListener implements ISuiteListener, ITestListener,
		IConfigurationListener {

	private static final Logger LOGGER = LogManager.getLogger(SuiteListener.class);

//	private static ExtentReports extent = ExtentManager.createInstance();
//	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestFailure(ITestResult result) {
		result.getThrowable().printStackTrace();
		saveLog(result.getThrowable().getMessage());
		Reporter.log(ScreenshotUtils.makeScreenshot(DriverManager.getDriver().getWebDriver(),
				result.getTestContext().getName() + "_" + result.getName()));
		LOGGER.info("================================== TEST "
				+ result.getName()
				+ " FAILED ==================================");
		makeScreenshot();
		DriverManager.getDriver().refresh();
//		saveLog("================================== TEST "
//				+ result.getName()
//				+ " FAILED ==================================");
	}

	public void extractJSLogs() throws IOException {
//		if (CliConfig.getBuild() == "none") {
//
//		} else {
//			File directory = new File(String.format(
//					"/jenkins/workspace/%s/builds/%s/archive",
//					CliConfig.getJobName(), CliConfig.getBuild())
//					+ fileSeparator + "screenshots" + fileSeparator + "js.log");
//
//			if (!directory.exists()) {
//				directory.createNewFile();
//			}
//			LogEntries logEntries = DriverManager.getWebDriver().getWebDriver()
//					.manage().logs().get(LogType.BROWSER);
//			FileOutputStream fos = new FileOutputStream(directory);
//
//			OutputStreamWriter osw = new OutputStreamWriter(fos);
//			for (LogEntry entry : logEntries) {
//				osw.write(new Date(entry.getTimestamp()) + " "
//						+ entry.getLevel() + " " + entry.getMessage()+"\n");
//			}
//			osw.close();
//		}
	}

	@Override
	public void onTestStart(ITestResult result) {
//		saveLog("================================== TEST "
//				+ result.getName()
//				+ " STARTED ==================================");
		LOGGER.info("================================== TEST "
				+ result.getName()
				+ " STARTED ==================================");
		if (Boolean.parseBoolean(System.getProperty("video"))) {
			VideoManager.getVideoRecorder().startRecording(DriverManager.getDriver(), result.getStartMillis() + result.getName());
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ScreenshotUtils.makeScreenshot(DriverManager.getDriver().getWebDriver(),
				result.getTestContext().getName() + "_" + result.getName());
		makeScreenshot();
		LOGGER.info("================================== TEST "
				+ result.getName()
				+ " SUCCESS ==================================");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		LOGGER.info("================================== TEST "
				+ result.getName()
				+ " SKIPPED ==================================");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onFinish(ITestContext context) {
		LOGGER.info("================================== CLASS "
				+ context.getName()
				+ " FINISHED ==================================");
	}

	@Override
	public void onStart(ISuite suite) {
		saveLog("================================== SUITE "
				+ suite.getName()
				+ " STARTED ==================================");
		LOGGER.info("================================== SUITE "
				+ suite.getName()
				+ " STARTED ==================================");
	}

	@Override
	public void onFinish(ISuite suite) {
		LOGGER.info("================================== SUITE "
				+ suite.getName()
				+ " FINISHED ==================================");

		boolean isFailed = false;

		IResultMap failedConfigs;
		IResultMap failedTests;
		IResultMap skippedConfigs;
		IResultMap skippedTests;

		Map<String, ISuiteResult> suiteResults = suite.getResults();

		for (ISuiteResult res : suiteResults.values()) {
			failedConfigs = res.getTestContext().getFailedConfigurations();
			failedTests = res.getTestContext().getFailedTests();
			skippedConfigs = res.getTestContext().getSkippedConfigurations();
			skippedTests = res.getTestContext().getSkippedTests();

			if (failedConfigs.size() != 0 || failedTests.size() != 0
					|| skippedConfigs.size() != 0 || skippedTests.size() != 0) {
				isFailed = true;
				break;
			}
		}

		if (!isFailed && !suiteResults.isEmpty()) {
			BuildResult.setExitResult(BuildResult.SUCCESS);
		}
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onConfigurationSuccess(ITestResult itr) {

	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {
		itr.getThrowable().printStackTrace();
	}

	@Override
	public void onConfigurationSkip(ITestResult itr) {
		itr.getThrowable().printStackTrace();
	}

	@Attachment(value = "Page screenshot", type = "image/png")
	private byte[] makeScreenshot() {
		return ((TakesScreenshot) DriverManager.getDriver().getWebDriver()).getScreenshotAs(OutputType.BYTES);
	}

	@Attachment(value = "{0}", type = "text/plain")
	public String saveLog(String message) {
		return message;
	}

}