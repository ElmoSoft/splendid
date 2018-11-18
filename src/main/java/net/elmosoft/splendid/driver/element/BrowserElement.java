package net.elmosoft.splendid.driver.element;

import net.elmosoft.splendid.driver.exceptions.WaitTimeoutException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import net.elmosoft.splendid.driver.element.helper.FindByHelper;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;


public class BrowserElement extends Element {

	public BrowserElement(SeleniumDriver driver, By foundBy) {
		this.foundBy = foundBy;
		this.driver = driver;
	}

	protected SeleniumDriver driver;

	protected By foundBy;
	
	private static final Logger LOGGER = Logger.getLogger(Element.class);

	@Override
	public boolean isExists() {
		return driver.isElementExists(foundBy, 1);
	}

	
	public boolean isVisible() {
		return driver.isElementDisplayed(foundBy);
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
			throw new WaitTimeoutException("Failed to wait element: "+ foundBy
					+ e.getMessage());
		}
	}

	public void waitForElementDissappear() {
		try {
			driver.waitForElementDissappear(foundBy, 10);
		} catch (Exception e) {
			throw new WaitTimeoutException("Failed to wait element: "+ foundBy
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

	public void clear() {
		try {
			driver.clear(foundBy);
		}
		catch (InvalidElementStateException e) {
			LOGGER.info(e.getMessage());
		}

	}

	public String getAttribute(String name) {
		return driver.getAttribute(foundBy, name);
	}
	
	public void checkCheckbox(boolean value) {
		driver.check(foundBy, value);
	}
	
	public void checkCheckbox() {
		driver.check(foundBy, true);
	}
	
	public void checkRadio(String value) {
		driver.checkRadio(foundBy, value);
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
			text = driver.getViewText(foundBy);
		}
		catch(StaleElementReferenceException e ) {
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

	public boolean isElementDisplayed() {
		return driver.isElementDisplayed(foundBy);
	}

	public BrowserElement format(Object... replaceString) {
		String locator = String.format(this.foundBy.toString(), replaceString);
		this.foundBy = FindByHelper.getByNestedObject(locator);
		return this;
	}
}
