package net.elmosoft.splendid.browser;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import net.elmosoft.splendid.utils.FileUtils;
import net.elmosoft.splendid.utils.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IosFactory extends BrowserFactory {

	private static final Logger LOGGER = LogManager.getLogger(IosFactory.class);

	@Override
	public WebDriver createBrowser(boolean acceptUntrustedCerts, boolean assumeUntrustedIssuer)  {
		throw new RuntimeException("Need to implement");
	}

	@Override
	public WebDriver createBrowser() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		Properties prop = PropertyReader.getInstance().getProperties("src/test/resources/local.properties");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("capabilities.deviceName"));
		capabilities.setCapability(MobileCapabilityType.APP, FileUtils.getAbsolutePath(prop.getProperty("capabilities.app")));
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("capabilities.automationName"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, prop.getProperty("capabilities.platformName"));
//		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, prop.getProperty("capabilities.platformVersion"));
		capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.parseBoolean(System.getProperty("noReset", prop.getProperty("capabilities.noReset"))));

		AppiumDriver<MobileElement> driver = null;
		try {
			driver = new IOSDriver<>(
					new URL(prop.getProperty("appium_host")),
					capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}
}