package net.elmosoft.splendid.browser;

import net.elmosoft.splendid.utils.VideoRecorder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriverException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class VideoManager {

	private static ThreadLocal<VideoRecorder> defaultDriver = new ThreadLocal<VideoRecorder>();
	private static List<VideoRecorder> drivers = Collections
			.synchronizedList(new LinkedList<VideoRecorder>());
	
	private static final Logger LOGGER = LogManager.getLogger(VideoManager.class);


	public static VideoRecorder getVideoRecorder() {
		if (null == defaultDriver.get()) {
			getNewDriver();
		}
		return defaultDriver.get();
	}


	public static void setDefaultDriver(VideoRecorder driver) {
		defaultDriver.set(driver);
	}

	public static VideoRecorder reopenDefaultDriver() {
		stopDefaultRecording();
		getVideoRecorder();
		return defaultDriver.get();
	}

	/**
	 * Close default driver
	 */
	public static void stopDefaultRecording() {
		VideoRecorder current = defaultDriver.get();
		if (null != current) {
			current.stopRecording();
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
	public static void closeDriver(VideoRecorder driver) {
		LOGGER.info(
				"Trying to close browser (driver.quit()): "+driver);
		try {
			if(driver!=null){
				//driver.close();
				driver.stopRecording();
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


	public static VideoRecorder getNewDriver() {
		LOGGER.info("Create new instance of Driver.");
		VideoRecorder driver = new VideoRecorder();
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
		for (VideoRecorder driver : drivers) {
			closeDriver(driver);
		}
	}

}