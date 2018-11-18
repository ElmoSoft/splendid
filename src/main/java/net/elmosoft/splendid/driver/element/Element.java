package net.elmosoft.splendid.driver.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class Element {

	public abstract boolean isExists();

	public abstract void waitForElementDisplayed(long timeoutSeconds);

	public abstract void waitForElementDissappear(long timeoutSeconds);


}