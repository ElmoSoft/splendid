package net.elmosoft.splendid.browser;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import net.elmosoft.splendid.utils.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class AndroidFactory extends BrowserFactory {

	private static final Logger LOGGER = LogManager.getLogger(AndroidFactory.class);

	@Override
	public WebDriver createBrowser(boolean acceptUntrustedCerts, boolean assumeUntrustedIssuer)  {
		throw new RuntimeException("Need to implement");
	}

	@Override
	public WebDriver createBrowser() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		Properties prop = PropertyReader.getInstance().getProperties("src/main/resources/local.properties");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("capabilities.deviceName"));
		capabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("capabilities.app"));
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("capabilities.automationName"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, prop.getProperty("capabilities.platformName"));

		AppiumDriver<MobileElement> driver = null;
		try {
			driver = new AndroidDriver<>(
					new URL(prop.getProperty("selenium_host")),
					capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}
}