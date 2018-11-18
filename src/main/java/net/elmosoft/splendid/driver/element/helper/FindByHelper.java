package net.elmosoft.splendid.driver.element.helper;

import org.openqa.selenium.By;

public class FindByHelper {
	
	public static By getByObject(String locator) {
		if (locator.startsWith("By.xpath: ")) {
			return By.xpath(locator.replace("By.xpath: ",""));
		}
		if (locator.startsWith("By.cssSelector: ")) {
			return By.cssSelector(locator.replace("By.cssSelector: ",""));
		}
		if (locator.startsWith("By.id: ")) {
			return By.id(locator.replace("By.id: ",""));
		}
		if (locator.startsWith("By.name: ")) {
			return By.name(locator.replace("By.name: ",""));
		}
		if (locator.startsWith("By.className: ")) {
			return By.className(locator.replace("By.className: ",""));
		}
		return null;
	}
	
	public static By getByNestedObject(String locator) {
		return getByObject(locator);
	}
}
