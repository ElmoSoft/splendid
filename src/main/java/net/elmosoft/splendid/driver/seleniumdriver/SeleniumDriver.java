package net.elmosoft.splendid.driver.seleniumdriver;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.elmosoft.splendid.browser.Browsers;
import net.elmosoft.splendid.browser.ChromeFactory;
import net.elmosoft.splendid.browser.FirefoxFactory;
import net.elmosoft.splendid.driver.element.BrowserElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import net.elmosoft.splendid.driver.exceptions.FailedToClickException;

/**
 * @author Aleksei_Mordas
 * 
 */
public class SeleniumDriver implements WebDriver {

	private static final Logger LOGGER = Logger.getLogger(SeleniumDriver.class);

	public static long IMPLICIT_WAIT_TIMEOUT = 30;
	public static long ZERO_WAIT_TIMEOUT = 0;
	private static final int Y_DIMENSION = 1050;
	private static final int X_DIMENSION = 1400;

	private WebDriver driver;

	private static final int WAIT_CUSTOM = 5;

	public SeleniumDriver(Browsers browserType) {
		switch (browserType) {
		case FF:
			driver = new FirefoxFactory().createBrowser(true, false);
			driver.manage().window().maximize();
			break;
		case CHROME:
			driver = new ChromeFactory().createBrowser(true, true);
			break;

		default:
			throw new RuntimeException("not implemented");
		}
		driver.manage().window().maximize();
		//driver.manage().window().setSize(new Dimension(X_DIMENSION, Y_DIMENSION));
		driver.manage().timeouts().implicitlyWait(WAIT_CUSTOM, TimeUnit.SECONDS);
	}

	public void waitForPageLoad() {
		Wait<WebDriver> wait = new WebDriverWait(driver, IMPLICIT_WAIT_TIMEOUT);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}

	@Override
	public void close() {
		LOGGER.info("Browser will be closed");
		driver.close();
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	public void waitImplicitly(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
	
	public void uploadFile(By locator, String filePath) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(locator);
		String visibleJS = "arguments[0].style.visibility = 'visible';";
		jse.executeScript(visibleJS, element);

		element.sendKeys(filePath);

		String hiddenJS = "arguments[0].style.visibility = 'hidden';";
		jse.executeScript(hiddenJS, element);	    
	}
	
	public void scrollToElement(By locator) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(locator);
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void scrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
	}

	public void scrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0,0);");
	}

	@Override
	public void get(String url) {
		LOGGER.info("Opening " + url);
		this.driver.get(url);
	}

	@Override
	public WebElement findElement(By by) {
		LOGGER.info("Find element by " + by);
		return this.driver.findElement(by);
	}

	@Override
	public String getPageSource() {
		LOGGER.info("Getting page source");
		return this.driver.getPageSource();
	}

	@Override
	public void quit() {
		LOGGER.info("Browser quite");
		this.driver.quit();
	}

	@Override
	public Navigation navigate() {
		LOGGER.info("Browser navigate");
		return this.driver.navigate();
	}

	@Override
	public Options manage() {
		LOGGER.info("Browser manage");
		return this.driver.manage();
	}

	@Override
	public String getCurrentUrl() {
		LOGGER.info("Getting current URL");
		return this.driver.getCurrentUrl();
	}

	public void refresh() {
		LOGGER.info("Browser refresh");
		this.driver.navigate().refresh();
	}

	@Override
	public String getTitle() {
		LOGGER.info("Getting title");
		return driver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {
		LOGGER.info("Finding elements by locator: " + by);
		return driver.findElements(by);
	}

	public List<BrowserElement> findElementsCustom(By by) {
		LOGGER.info("Finding elements by locator: " + by);
		List<WebElement> elements = driver.findElements(by);
		List<BrowserElement> customs = new ArrayList<>();
		for (int el = 1; el <= elements.size(); el++) {
			customs.add((BrowserElement) new BrowserElement(this, by));
		}
		return customs;
	}

	@Override
	public Set<String> getWindowHandles() {
		LOGGER.info("Getting window handles");
		return driver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		LOGGER.info("Get window handle");
		return driver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	public void type(By locator, String text) {
		driver.findElement(locator).sendKeys(text);
		LOGGER.info("Typed text '" + text + "' ...");
		String value = driver.findElement(locator).getAttribute("value");
		if(!value.equals(text)) {
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(text);
		}

	}

	public void click(By by) {
		LOGGER.info("Clicking element '" + by + "' ...");
		int attempts = 0;
		while (attempts < 4) {
			try {
				driver.findElement(by).click();
				LOGGER.info("Element '" + by + "' clicked successfully");
				return;
			} catch (StaleElementReferenceException e) {
				LOGGER.info("StaleException. Attempt: " + attempts);
			} catch (WebDriverException e) {
				LOGGER.info("Element didn't click." + e.getMessage() + " Attempt: " + attempts);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			attempts++;
		}
		throw new FailedToClickException("Can't click :" + by);
	}

	public void clickJS(By locator) {
		WebElement element = driver.findElement(locator);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');" +
				"evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		String onClickScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');" +
				"evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onclick');}";

		executor.executeScript(mouseOverScript, element);

		executor.executeScript(onClickScript, element);
		//executor.executeScript("arguments[0].click();", element);
		LOGGER.info("Element '" + locator + "' clicked successfully with JS");
	}

	public void clear(By locator) {
		LOGGER.info("Clear element '" + locator + "' ...");
		driver.findElement(locator).clear();

	}

	public boolean isElementExists(By locator, int waitTimeout) {
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);
		try {
			LOGGER.info("Check is element exist: " + locator);
			driver.findElement(locator);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(WAIT_CUSTOM, TimeUnit.SECONDS);
		}
	}

	public boolean isElementDisplayed(By locator) {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		try {
			LOGGER.info("Check is element displayed: " + locator);
			return driver.findElement(locator).isDisplayed();
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(WAIT_CUSTOM, TimeUnit.SECONDS);
		}
	}

	public String getViewText(By locator) {
		LOGGER.info("Getting element text '" + locator + "' ...");
		int attempts = 0;
		String text = null;
		while (attempts < 5) {
			try {
				text = driver.findElement(locator).getText();
				break;
			} catch (StaleElementReferenceException e) {
				LOGGER.info("Attempt to get text:" + attempts);
			}
			attempts++;
		}

		LOGGER.info("=======Text: " + text + "==========");
		return text;
	}

	public org.openqa.selenium.Dimension getSize(By locator) {
		LOGGER.info("Getting element size '" + locator + "' ...");
		return driver.findElement(locator).getSize();
	}
	
	public void waitForElementClickable(final By locator, long timeOutInSeconds) {
		LOGGER.info("Waiting for element '" + locator + "' clickable during " + timeOutInSeconds + "sec timeout ...");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void waitForElementDisplayed(final By locator, long timeOutInSeconds) {
		LOGGER.info("Waiting for element '" + locator + "' exists during " + timeOutInSeconds + "sec timeout ...");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForElement(final By locator, long timeOutInSeconds) {
		LOGGER.info("Waiting for element '" + locator + "' exists during " + timeOutInSeconds + "sec timeout ...");
		new WebDriverWait(driver, timeOutInSeconds).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				try {
					return d.findElements(locator).size() > 0;
				} catch (NoSuchElementException e) {
					return false;
				} catch (WebDriverException e) {
					return false;
				}
			}
		});
	}

	public String getAttribute(By foundBy, String attribute) {
		return driver.findElement(foundBy).getAttribute(attribute);
	}

	public void waitForElementWithText(final String text, final By locator, long timeOutInSeconds) {
		LOGGER.info("Waiting for element '" + locator + "' exists during " + timeOutInSeconds + "sec timeout ...");
		new WebDriverWait(driver, timeOutInSeconds).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				try {
					return d.findElement(locator).getText().contains(text);
				} catch (NoSuchElementException e) {
					return false;
				}
			}
		});

	}

	public void waitForElementDissappear(final By locator, long timeOutInSeconds) {
		LOGGER.info("Waiting for element disappearence'" + locator + "' during " + timeOutInSeconds + "sec timeout ...");
		new WebDriverWait(driver, timeOutInSeconds).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				try {
					d.manage().timeouts().implicitlyWait(ZERO_WAIT_TIMEOUT, TimeUnit.SECONDS);
					if (d.findElements(locator).size() == 0) {
						return true;
					}
					d.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					return !d.findElement(locator).isDisplayed();
				} catch (Exception e) {
					return true;
				}
				finally {
					d.manage().timeouts().implicitlyWait(WAIT_CUSTOM, TimeUnit.SECONDS);
				}
			}
		});

	}
	
	public void checkRadio(By locator, String value) {
		LOGGER.info("Check radiobutton '" + locator + "' by value: " + value);
		List<WebElement> radios = findElement(locator).findElements(By.xpath(".//*[@role='radio']"));
		boolean isChecked = false;
		for (WebElement elem : radios) {
			if (elem.getAttribute("value").equalsIgnoreCase(value)) {
				elem.click();
				isChecked = true;
				break;
			}
		}
		if (!isChecked)
			throw new NoSuchElementException("There is not option '" + value + "' in the radiobutton");
	}
	
	public void selectOption(By locator, String value) {
		LOGGER.info("Select option by value '" + value + "' for locator: " + locator);
		List<WebElement> options = findElements(locator);
		boolean isChecked = false;
		for (WebElement elem : options) {
			if (elem.getText().equalsIgnoreCase(value) || elem.getAttribute("value").equals(value)) {
				elem.click();
				isChecked = true;
				try {
					if(elem.isDisplayed()) {
						LOGGER.info("Select option by value one more time");
						elem.click();
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		if (!isChecked)
			throw new NoSuchElementException("There is not option '" + value + "' in the dropdown/checkbox list");
	}
	
	public void selectOption(By locator, int index) {
		LOGGER.info("Select option by index '" + index + "' for locator: " + locator);
		List<WebElement> options = findElements(locator);
		try {
			options.get(index).click();
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException("Options is less than '" + index + "' in the dropdown/checkbox list");
		}
	}
	
	public void check(By locator, boolean value) {
		LOGGER.info("Check checkbox by locator '" + locator + "' if needed");
		WebElement element = findElement(locator);
		if (element.getAttribute("aria-checked") != null && element.getAttribute("aria-checked").equals("false") == value) {
			element.click();
		} else {
			LOGGER.info("Current state is " + value + ". Not necessary to check");
		}
	}

	public void waitForUrlContain(String url) {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_CUSTOM);
		ExpectedCondition<Boolean> urlIsCorrect = arg0 ->    driver.getCurrentUrl().contains(url);
		wait.until(urlIsCorrect);
	}
}
