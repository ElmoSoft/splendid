package net.elmosoft.splendid.driver.element;

import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;


public class MobileElement extends BrowserElement {

	private static final Logger LOGGER = LogManager.getLogger(MobileElement.class);

	public MobileElement(SeleniumDriver driver, By foundBy) {
		super(driver,foundBy);
	}

}
