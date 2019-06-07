package net.elmosoft.splendid.driver.element.helper;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByPartialLinkText;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.support.ByIdOrName;

import net.elmosoft.splendid.driver.exceptions.CommonTestRuntimeException;

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
	
	
	public static String getLocatorFieldName(By by)
	{
		if(by instanceof ByClassName)
		{
			return "className";
		}
		if(by instanceof ByCssSelector)
		{
			return "cssSelector";
		}
		if(by instanceof ById)
		{
			return "id";
		}
		if(by instanceof ByIdOrName)
		{
			return "idOrName";
		}
		if(by instanceof ByLinkText)
		{
			return "linkText";
		}
		if(by instanceof ByName)
		{
			return "name";
		}
		if(by instanceof ByPartialLinkText)
		{
			return "partialLinkText";
		}
		if(by instanceof ByTagName)
		{
			return "tagName";
		}
		if(by instanceof ByXPath)
		{
			return "xpathExpression";
		}
		throw new CommonTestRuntimeException("Method format is not available for " + by.toString());
	}
	
	public static String getStringLocator(By by) {
		String fieldName = getLocatorFieldName(by);
		try {
			return (String) FieldUtils.readField(by, fieldName, true);
		} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
			throw new CommonTestRuntimeException("Failed to format locator", e);
		}
	}
}
