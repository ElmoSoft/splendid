package net.elmosoft.splendid.driver.element;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import net.elmosoft.splendid.driver.element.helper.FindByHelper;
import net.elmosoft.splendid.driver.exceptions.WaitTimeoutException;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;


public class BrowserElement extends Element {

	public BrowserElement(SeleniumDriver driver, By foundBy) {
		this.foundBy = foundBy;
		this.driver = driver;
	}

	protected SeleniumDriver driver;

	protected By foundBy;

	private static final Logger LOGGER = LogManager.getLogger(Element.class);

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
	public void waitForElementDissappear(long timeoutSeconds) {
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
			throw new RuntimeException("Failed to wait element: "+ foundBy
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

	public By getFoundBy() {
		return foundBy;
	}

	public void clickJS() {
		driver.clickJS(foundBy);
	}

	public void type(String text) {
		driver.type(foundBy, text);
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

	public BrowserElement format(Object... replaceString) {
		String locator = String.format(this.foundBy.toString(), replaceString);
		this.foundBy = FindByHelper.getByNestedObject(locator);
		return this;
	}
}
