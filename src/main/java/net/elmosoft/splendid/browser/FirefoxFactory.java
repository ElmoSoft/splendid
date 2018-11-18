package net.elmosoft.splendid.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Aleksei_Mordas
 *
 */
public class FirefoxFactory extends BrowserFactory {

	private static final Logger LOGGER = Logger.getLogger(FirefoxFactory.class);

	@Override
	public WebDriver createBrowser(boolean acceptUntrustedCerts,
			boolean assumeUntrustedIssuer) {
		FirefoxProfile profile = new FirefoxProfile();
		LOGGER.info("Firefox profile was created");
		profile.setAcceptUntrustedCertificates(acceptUntrustedCerts);
		profile.setAssumeUntrustedCertificateIssuer(assumeUntrustedIssuer);
		new DesiredCapabilities();
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		capabilities.setCapability("fake", "true");
		capabilities.setCapability("media.navigator.permission.disabled", "true");
		LOGGER.info("Firefox profile was set as capability");
		WebDriverManager.firefoxdriver().setup();

		return new FirefoxDriver(capabilities);
	}

	@Override
	public WebDriver createBrowser() {
		return new FirefoxDriver();
	}

}
