package net.elmosoft.splendid.test;


import net.elmosoft.splendid.browser.DriverManager;
import net.elmosoft.splendid.browser.VideoManager;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;

/**
 * @author Aleksei_Mordas
 *
 *         e-mail: * alexey.mordas@gmail.com Skype: alexey.mordas
 */
public class BaseSplendidTest {
	
	public static final Logger LOGGER = LogManager.getLogger(BaseSplendidTest.class);
	
	protected SeleniumDriver driver;
	protected Boolean isRecordVideo = Boolean.parseBoolean(System.getProperty("video"));

	@BeforeClass(alwaysRun = true, description = "Class Level Setup")
	public void classSetUp(ITestContext testContext) {
		if (isRecordVideo) {
			VideoManager.getNewDriver();
		}
	}

	@BeforeMethod(alwaysRun = true, description = "Method Level Setup")
	public void methodSetUp(Method method) {

	}

	@AfterClass(alwaysRun = true, description = "Class Level Tear Down")
	public void classTearDown() {
		
	}

	@AfterSuite(alwaysRun = true, description = "Suite Level Tear Down")
	public void suiteTearDown() {
		DriverManager.closeAllOpenedBrowsers();
	}

	@AfterMethod(alwaysRun = true, description = "Method Level Tear Down")
	public void methodTearDown(final ITestContext testContext) {
		if (isRecordVideo) {
			VideoManager.getVideoRecorder().stopRecording();
		}
	}

}
