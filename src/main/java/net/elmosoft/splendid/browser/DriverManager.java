package net.elmosoft.splendid.browser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;


public class DriverManager {

	private static ThreadLocal<SeleniumDriver> defaultDriver = new ThreadLocal<SeleniumDriver>();
	private static List<SeleniumDriver> drivers = Collections
			.synchronizedList(new LinkedList<SeleniumDriver>());
	
	private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);


	public static SeleniumDriver getDriver() {
		if (null == defaultDriver.get()) {
			getNewDriver();
		}
		return defaultDriver.get();
	}


	public static void setDefaultDriver(SeleniumDriver driver) {
		defaultDriver.set(driver);
	}

	public static SeleniumDriver reopenDefaultDriver() {
		closeDefaultDriver();
		getDriver();
		return defaultDriver.get();
	}

	/**
	 * Close default driver
	 */
	public static void closeDefaultDriver() {
		SeleniumDriver current = defaultDriver.get();
		if (null != current) {
			current.close();
			current.quit();
			drivers.remove(current);
		}
		defaultDriver.set(null);
	}

	public static boolean isDefaultDriverOpened() {
		return null != defaultDriver.get();
	}

	/**
	 * Trying to call driver.quit()
	 * 
	 * @param driver
	 */
	public static void closeDriver(SeleniumDriver driver) {
		LOGGER.info(
				"Trying to close browser (driver.quit()): "+driver);
		try {
			if(driver!=null){
				//driver.close();
				driver.quit();
				drivers.remove(driver);
			}
		} catch (WebDriverException exc) {
			if (exc.getMessage()
					.contains(
							"Error communicating with the remote browser. It may have died.")) {
				LOGGER.warn(exc.getMessage());
			} else {
				throw new RuntimeException(
						"Error while closing browser.", exc);
			}
		}
	}


	public static SeleniumDriver getNewDriver() {
		LOGGER.info("Create new instance of Driver.");
		SeleniumDriver driver = new SeleniumDriver(Browsers.valueOf(System.getProperty("browser").toUpperCase()));
		drivers.add(driver);
		defaultDriver.set(driver);
		return driver;
	}

	/**
	 * Trying to close all opened during test executing browsers (driver
	 * instances)
	 */
	public static void closeAllOpenedBrowsers() {
		LOGGER.info("Close all opened during tests executing browsers");
		for (SeleniumDriver driver : drivers) {
			closeDriver(driver);
		}
	}

}