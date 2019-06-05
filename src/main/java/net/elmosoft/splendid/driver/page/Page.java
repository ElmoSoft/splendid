package net.elmosoft.splendid.driver.page;

import net.elmosoft.splendid.browser.DriverManager;
import net.elmosoft.splendid.driver.element.WaitConditions;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {
	protected static final int TWO_MINUTE_WAIT = 120;
	protected static final int ONE_MINUTE_WAIT = 60;
	protected static final int TEN_SECONDS_WAIT = 10;
	protected static final int FIVE_SECONDS_WAIT = 5;

	protected static final int THIRTY_SECONDS_WAIT = 30;
	protected static final int THREE_MINUTE_WAIT = 180;
	public static final Logger LOGGER = LogManager.getLogger(Page.class);

	public abstract void checkPage();
	public abstract void waitForPageIsLoaded();

	public SeleniumDriver driver;

	protected Page() {
		driver = DriverManager.getDriver();
		PageFactory.initElements(driver, this);
	}
	

	protected String getImagePath(String nameImg) {
		return "src\\test\\resources\\images\\" + nameImg;

	}

	public void waitForAjax() {
		LOGGER.debug("Waiting for completing of all ajax requests");
		try {
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), TEN_SECONDS_WAIT);
			wait.until(WaitConditions.ajaxToFinishLoading());
		} catch (Exception e) {
			// do nothing
		}
	}

	public void waitForAngular() {
		LOGGER.info("Waiting for spinner disappearence and page is fully loaded...");
		driver.waitForPageLoad();
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(),60);
		JavascriptExecutor jsExec = (JavascriptExecutor) driver.getWebDriver();

		String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

		//Wait for ANGULAR to load
		ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf(((JavascriptExecutor) driver)
				.executeScript(angularReadyScript).toString());

		//Get Angular is Ready
		boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());

		//Wait ANGULAR until it is Ready!
		if(!angularReady) {
			LOGGER.info("ANGULAR is NOT Ready! Waiting..");
			//Wait for Angular to load
			wait.until(angularLoad);
		} else {
			LOGGER.info("ANGULAR is Ready!");
		}
	}

	public void sleepInMilliSec(int milliSec) {
		try {
			Thread.sleep(milliSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void pressButton(Keys key) {
		Actions action = new Actions(driver.getWebDriver());
		action.sendKeys(key).release().build().perform();
	}
}