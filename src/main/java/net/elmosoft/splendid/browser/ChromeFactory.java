package net.elmosoft.splendid.browser;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class ChromeFactory extends BrowserFactory {
	private static final Logger LOGGER = LogManager.getLogger(ChromeFactory.class);

	@Override
	public WebDriver createBrowser(boolean acceptUntrustedCerts,
			boolean assumeUntrustedIssuer) {
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromePreferences = new HashMap<>();
		chromePreferences.put("profile.password_manager_enabled", false);
		options.addArguments("--start-maximized");
		options.addArguments("--disable-web-security");
		options.addArguments("--disable-user-media-security=true");
		options.addArguments("--allow-running-insecure-content");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--window-size=1600,1400");
		if (System.getProperty("headless").equals("true")) {
			options.addArguments("--headless");
		}
		//options.setPageLoadStrategy(PageLoadStrategy.NONE);
		LoggingPreferences loggingprefs = new LoggingPreferences();
		loggingprefs.enable(LogType.BROWSER, Level.ALL);
		LOGGER.info("Chrome profile was created");
//		options.setHeadless(HEADLESS);
		options.addArguments("--no-default-browser-check");
		options.setExperimentalOption("prefs", chromePreferences);
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("profile.default_content_setting_values.notifications", 1);
		options.setExperimentalOption("prefs", prefs);
		WebDriverManager.chromedriver().setup();

		return new ChromeDriver(options);
	}

	@Override
	public WebDriver createBrowser() {
		return new ChromeDriver();
	}
}