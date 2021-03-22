package net.elmosoft.splendid.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Attachment;
import net.elmosoft.splendid.browser.DriverManager;

/**
 * @author Aleksei_Mordas
 *
 *         e-mail: * alexey.mordas@gmail.com Skype: alexey.mordas
 */
public class ScreenshotUtils {

	private static final Logger LOGGER = LogManager.getLogger(ScreenshotUtils.class);

	private static final String DATE_FORMAT = "dd_MMM_yyyy__hh_mm_ssaa_SSS";

	private static String fileSeparator = System.getProperty("file.separator");
	private static final String SCREENSHOTS = "screenshots";
	private static final String REPORT_DIRECTORY = "target" + fileSeparator + "test-output" + fileSeparator + "html";

	public synchronized static String makeScreenshot(WebDriver driver, String fileName) {
		try {
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
			File reportDirectory = new File(REPORT_DIRECTORY);
			File directory = new File(reportDirectory, SCREENSHOTS + fileSeparator);

			directory.mkdirs();

			fileName = fileName + "_" + dateFormat.format(date);

			File f = new File(directory, fileName + ".jpg");
			if (!f.exists()) {
				f.createNewFile();
			}

			byte[] scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

			try {
				FileUtils.writeByteArrayToFile(f, scrFile);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}

			String newFileNamePath = "<a href=\"" + SCREENSHOTS + "/" + fileName + ".jpg" + "\">screenshot-" + fileName
					+ "</a>";

			LOGGER.info(newFileNamePath);
			return newFileNamePath;

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Attachment(value = "Page screenshot", type = "image/png")
	public synchronized static byte[] makeAllureScreenshot(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
}
