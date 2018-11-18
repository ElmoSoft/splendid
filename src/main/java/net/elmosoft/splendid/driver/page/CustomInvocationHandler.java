package net.elmosoft.splendid.driver.page;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.elmosoft.splendid.driver.element.BrowserElement;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class CustomInvocationHandler implements InvocationHandler {

	private final By locator;
	private final Class<?> clazz;
	private SeleniumDriver driver;

	public CustomInvocationHandler(SeleniumDriver driver, By locator,
			Class<?> clazz) {
		this.locator = locator;
		this.clazz = clazz;
		this.driver = driver;
	}

	@Override
	public Object invoke(Object object, Method method, Object[] objects)
			throws Throwable {
		List<WebElement> elements = driver.getWebDriver().findElements(locator);
		List<BrowserElement> customs = new ArrayList<>();
		for (int el = 0; el < elements.size(); el++) {
			Constructor<?> fieldConstructor = clazz.getConstructor(SeleniumDriver.class, By.class);
			customs.add((BrowserElement) fieldConstructor.newInstance(driver, locator));
		}
		try {
			return method.invoke(customs, objects);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}
	
}