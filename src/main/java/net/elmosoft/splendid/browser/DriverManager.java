package net.elmosoft.splendid.browser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;


public class DriverManager {

	private static ThreadLocal<SeleniumDriver> defaultDriver = new ThreadLocal<SeleniumDriver>();
	private static ThreadLocal<SeleniumDriver> defaultMobileDriver = new ThreadLocal<SeleniumDriver>();
	private static List<SeleniumDriver> drivers = new CopyOnWriteArrayList<SeleniumDriver>();
	private static ThreadLocal<List<SeleniumDriver>> localDriverList = new ThreadLocal<List<SeleniumDriver>>();

	private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);

	public static SeleniumDriver getDriver() {
		Browsers browserType = Browsers.valueOf(System.getProperty("browser").toUpperCase());
		if(browserType== Browsers.IOS || browserType == Browsers.ANDROID) {
			return getMobileDriver();
		}
		if (null == defaultDriver.get()) {
			getNewDriver();
		}
		return defaultDriver.get();
	}

	public static SeleniumDriver getMobileDriver() {
		if (null == defaultMobileDriver.get()) {
			getNewDriver(Browsers.valueOf(System.getProperty("browser").toUpperCase()));
		}
		return defaultMobileDriver.get();
	}

	public static boolean isDefaultMobileDriver() {
		return defaultMobileDriver.get() != null;
	}

	public static void setDefaultDriver(SeleniumDriver driver) {
		defaultDriver.set(driver);
	}

	public static SeleniumDriver reopenDefaultDriver() {
		SeleniumDriver current = getDriver();
		closeDriver(current);
		localDriverList.get().remove(current);
		defaultDriver.set(null);
		return getDriver();
	}

	/**
	 * Close default driver
	 */
	public static void closeDefaultDriver() {
		SeleniumDriver current = defaultDriver.get();
		int browsersCountBeforeClosing = drivers.size();
		if (current == null && browsersCountBeforeClosing == 0) {
			LOGGER.debug("There are no opened browsers");
			return;
		}
		LOGGER.debug("Active browsers count before closing is " + browsersCountBeforeClosing);
		try {
			closeDriver(current);
			localDriverList.get().remove(current);
		} finally {
			int browsersCountAfterClosing = drivers.size();
			LOGGER.debug("Active browsers count after closing is " + browsersCountAfterClosing);
			LOGGER.debug("Default driver is opened" + isDefaultDriverOpened());

			if (localDriverList.get() != null) {
				if ((localDriverList.get().size() != 0) && (browsersCountBeforeClosing > browsersCountAfterClosing)) {
					int previousDriver = localDriverList.get().size() - 1;
					defaultDriver.set(localDriverList.get().get(previousDriver));
				} else {
					defaultDriver.set(null);
				}
			}
		}
	}

	/**
	 * Close default driver
	 */
	public static void closeDefaultMobileDriver() {
		SeleniumDriver current = defaultMobileDriver.get();
		int browsersCountBeforeClosing = drivers.size();
		if (current == null) {
			LOGGER.debug("There are no opened mobile drivers");
			return;
		}
		LOGGER.debug("Active drivers count before closing is " + browsersCountBeforeClosing);
		try {
			closeDriver(current);
		} finally {
			int browsersCountAfterClosing = drivers.size();
			LOGGER.debug("Active drivers count after closing is " + browsersCountAfterClosing);
			LOGGER.debug("Default driver is opened" + isDefaultMobileDriver());
			defaultMobileDriver.set(null);
		}
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
		LOGGER.debug("Trying to close browser (driver.quit()): " + driver);
		try {
			if (driver != null) {
				//driver.close();
				driver.quit();
				drivers.remove(driver);
			}
		} catch (WebDriverException exc) {
			exc.printStackTrace();
		} finally {
			drivers.remove(driver);
		}
	}

	public static SeleniumDriver getNewDriver() {
		return getNewDriver(Browsers.valueOf(System.getProperty("browser", "chrome").toUpperCase()));
	}

	public static SeleniumDriver getNewDriver(Browsers browsers) {
		LOGGER.debug("Create new instance of Driver.");
		SeleniumDriver driver = new SeleniumDriver(browsers);
		switch (browsers) {
			case ANDROID:
			case IOS:
				defaultMobileDriver.set(driver);
				break;
			default:
				setDefaultDriver(driver);
		}
		drivers.add(driver);
		List<SeleniumDriver> localList = localDriverList.get() == null ? new ArrayList<>() : localDriverList.get();
		localList.add(driver);
		localDriverList.set(localList);
		return driver;
	}

	/**
	 * Trying to close all opened during test executing browsers (driver
	 * instances)
	 */
	public static void closeAllOpenedBrowsers() {
		LOGGER.debug("Close all opened during tests executing browsers");
		int i = 0;
		List<SeleniumDriver> driversToClose = new LinkedList<SeleniumDriver>();
		try {
			driversToClose.addAll(drivers);
			for (SeleniumDriver driver : driversToClose) {
				LOGGER.debug("Close Browser " + i);
				i++;
				closeDriver(driver);
				LOGGER.debug("Success");
			}
		} finally {
			defaultDriver.set(null);
			defaultMobileDriver.set(null);
		}
	}

	public static void closeAllOpenedBrowsersInCurrentThread() {
		LOGGER.debug("Close all opened during tests execution browsers in the current thread");
		int i = 1;
		LOGGER.debug("Drivers size in the current thread is " + localDriverList.get().size());
		List<SeleniumDriver> drivers = new ArrayList<SeleniumDriver>(localDriverList.get());
		try {
			for (SeleniumDriver driver : drivers) {
				LOGGER.debug("Close Browser " + i + ", driver: " + driver);
				closeDriver(driver);
				localDriverList.get().remove(driver);
				LOGGER.debug("Browser " + i + " was closed successfully");
				i++;
			}
		} finally {
			localDriverList.set(null);
			defaultDriver.set(null);
			defaultMobileDriver.set(null);
		}
	}

}