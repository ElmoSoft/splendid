package net.elmosoft.splendid.driver.element;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import net.elmosoft.splendid.driver.element.helper.FindByHelper;
import net.elmosoft.splendid.driver.exceptions.CommonTestRuntimeException;
import net.elmosoft.splendid.driver.exceptions.WaitTimeoutException;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;


public class BrowserElement extends Element {
	private static final Logger LOGGER = LogManager.getLogger(Element.class);
	private By notFormatedBy;
	protected SeleniumDriver driver;
	protected By foundBy;
	private String nameForLogger="";
	protected String name="";
	private String formatLocator;

	public BrowserElement(SeleniumDriver driver, By foundBy) {
		this.foundBy = foundBy;
		this.driver = driver;
	}


	@Override
	public boolean isExists() {
		return driver.isElementExists(foundBy);
	}

	public boolean isVisible() {
		return driver.isElementDisplayed(foundBy);
	}

	public boolean isEnabled() {
		return driver.isElementEnabled(foundBy);
	}

	public void waitForElementClickable(long timeoutSeconds) {
		try {
			driver.waitForElementClickable(foundBy, timeoutSeconds);
		} catch (Exception e) {
			throw new WaitTimeoutException("Failed to wait element: " + foundBy + e.getMessage());
		}
	}

	public void waitForElementNotClickable(long timeoutSeconds) {
		try {
			driver.waitForElementClickable(foundBy, timeoutSeconds);
		} catch (Exception e) {
			throw new WaitTimeoutException("Failed to wait element: " + foundBy + e.getMessage());
		}
	}

	public void waitForElementTextNotNull(long timeoutSeconds) {
		try {
			driver.waitForElementTextNotNull(foundBy, timeoutSeconds);
		} catch (Exception e) {
			throw new WaitTimeoutException("Failed to wait element: " + foundBy + e.getMessage());
		}
	}

	@Override
	public void waitForElementDisplayed(long timeoutSeconds) {
		try {
			driver.waitForElementDisplayed(foundBy, timeoutSeconds);
		} catch (Exception e) {
			throw new WaitTimeoutException("Failed to wait element: "+ foundBy
					+ e.getMessage());
		}
	}

	@Override
	public void waitForElementDissappear(long timeoutSeconds) throws WaitTimeoutException {
		try {
			driver.waitForElementDissappear(foundBy, timeoutSeconds);
		} catch (Exception e) {
			throw new WaitTimeoutException("Failed to wait element disappear: "+ foundBy
					+ e.getMessage());
		}
	}

	public void waitForElementDissappear() {
		try {
			driver.waitForElementDissappear(foundBy, 10);
		} catch (Exception e) {
			throw new WaitTimeoutException("Failed to wait element disappear: "+ foundBy
					+ e.getMessage());
		}
	}

	public void waitForElementExist(long timeoutSeconds) {
		try {
			driver.waitForElement(foundBy, timeoutSeconds);
		} catch (Exception e) {
			throw new WaitTimeoutException("Failed to wait element: "+ foundBy
					+ e.getMessage());
		}
	}

	public void waitImplicitly(int seconds) {
		driver.waitImplicitly(seconds);
	}

//	@Override
//	public By getFoundBy() {
//		return foundBy;
//	}
//
//	@Override
//	public void setFoundBy(By foundBy) {
//		this.foundBy = foundBy;
//	}


	public void click() {
		driver.click(foundBy);
	}

	public void leftClick() {
		driver.leftClick(foundBy);
	}

	public By getFoundBy() {
		return foundBy;
	}

	public void clickJS() {
		driver.clickJS(foundBy);
	}

	public void type(String text) {
		driver.browserType(foundBy, text);
	}

	public void clickAndType(String text) {
		waitForElementClickable(5);
		click();
		driver.browserType(foundBy, text);
	}

	public WebElement getWebElement() {
		return driver.findElement(foundBy);
	}

	public WebElement getWebElement(By foundBy) {
		WebElement webElement = getWebElement();
		try {
			return webElement.findElement(foundBy);
		}
		catch (NoSuchElementException ex) {
			throw new NoSuchElementException(String.format("%s + %s", this.foundBy, foundBy));
		}
	}

	public void clear() {
		try {
			driver.clear(foundBy);
		}
		catch (InvalidElementStateException e) {
			LOGGER.debug(e.getMessage());
		}

	}

	public String getAttribute(String name) {
		return driver.getAttribute(foundBy, name);
	}

	public String getValue() {
		return getAttribute("value");
	}

	public void checkCheckbox(boolean value) {
		driver.check(foundBy, value);
	}

	public void checkCheckbox() {
		driver.check(foundBy, true);
	}

	public boolean isCheckboxChecked() {
		return driver.isCheckboxChecked(foundBy);
	}

	public void checkRadio(String value) {
		driver.checkRadio(foundBy, value);
	}

	public String getCheckedRadio() {
		return driver.getCheckedRadio(foundBy);
	}

	public void selectOptionByValue(String value) {
		driver.selectOption(foundBy, value);
	}

	public void selectOptionByIndex(int index) {
		driver.selectOption(foundBy, index);
	}

	public void selectFirstOption() {
		driver.selectOption(foundBy, 0);
	}

	public void scrollIntoElement() {
		driver.scrollToElement(foundBy);
	}

	public void uploadFile(String filePath) {
		driver.uploadFile(foundBy, filePath);
	}

	public String getText() {
		String text;
		try {
			driver.scrollToElement(foundBy);
			text = driver.getViewText(foundBy);
		} catch (StaleElementReferenceException e) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			text = driver.getViewText(foundBy);
		}
		return text;
	}

	public String getTextIfElementExist() {
		if (isExists()) {
			return getText();
		}
		return null;
	}

	public String getElementNameForLogger() {
		if (StringUtils.isNotEmpty(nameForLogger)) {
			return nameForLogger;
		}
		if (StringUtils.isNotEmpty(name)) {
			return name;
		}
		return FindByHelper.getStringLocator(notFormatedBy);
	}

	public BrowserElement format(Object... args) {
		nameForLogger = new StringBuilder(name).append(": ").append(Arrays.toString(args)).append("").toString();
		Class<?> byClass = notFormatedBy.getClass();
		try {
			String locatorString = FindByHelper.getStringLocator(notFormatedBy);
			foundBy = (By) byClass.getConstructor(String.class).newInstance(String.format(locatorString, args));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new CommonTestRuntimeException("Failed to format locator", e);
		}
		return this;
	}

	public Point getLocation() {
		return driver.findElement(foundBy).getLocation();
	}


	public Dimension getSize() {
		LOGGER.debug("Getting element size '" + foundBy + "' ...");
		return driver.findElement(foundBy).getSize();
	}
}
