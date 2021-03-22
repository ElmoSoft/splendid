package net.elmosoft.splendid.driver.page;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import net.elmosoft.splendid.browser.Browsers;
import net.elmosoft.splendid.driver.element.BrowserElement;
import net.elmosoft.splendid.driver.element.MobileElement;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.By;

import net.elmosoft.splendid.driver.annotation.FindBy;
import net.elmosoft.splendid.driver.element.Element;


/**
 * @author Aleksei_Mordas
 *
 */
public class PageFactory {

	public static <T extends Page> T initElements(SeleniumDriver driver,
												  Class<T> pageClass) {
		T page = instantiatePage(driver, pageClass);
		initElements(driver, page);
//		List<Class> list = getSuperClasses(page.getClass());
//		for( Class superClass: list){
//			initFields(page, superClass.getDeclaredFields(), driver );
//		}
		return page;
	}

	private static <T extends Page> T instantiatePage(SeleniumDriver driver,
													  Class<T> pageClass) {
		try {
			try {
				Constructor<T> constructor = pageClass
						.getConstructor();
				return constructor.newInstance();
			} catch (NoSuchMethodException e) {
				return pageClass.newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static void initFields(Page page, Field[] fields, SeleniumDriver driver ) {
		for (Field field : fields) {

			try {

				Class<?> fieldClass = field.getType();

				if (List.class.isAssignableFrom(fieldClass)) {
					Type genericType = field.getGenericType();
					Class<?> listElementClass = (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
					if (Element.class.isAssignableFrom(listElementClass)) {
						FindBy elementAnnotation = field.getAnnotation(FindBy.class);
						By by = setFindByToElement(elementAnnotation);
						field.setAccessible(true);
						field.set(page, createList(driver, page.getClass().getClassLoader(), by,
								(Class<Element>)
										listElementClass));

					}
				}
				switch (Browsers.valueOf(System.getProperty("browser", "chrome").toUpperCase())) {
					case ANDROID:
						if (MobileElement.class.isAssignableFrom(fieldClass)) {
							AndroidFindBy annotation = field.getAnnotation(AndroidFindBy.class);
							By by = setAndroidMobileFindByToElement(annotation);
							Constructor<?> fieldConstructor = fieldClass.getConstructor(SeleniumDriver.class, By.class);
							field.setAccessible(true);
							field.set(page, fieldConstructor.newInstance(driver, by));
						}
						break;
					case IOS:
						if (MobileElement.class.isAssignableFrom(fieldClass)) {
							iOSXCUITFindBy annotation = field.getAnnotation(iOSXCUITFindBy.class);
							By by = setIosMobileFindByToElement(annotation);
							Constructor<?> fieldConstructor = fieldClass.getConstructor(SeleniumDriver.class, By.class);
							field.setAccessible(true);
							field.set(page, fieldConstructor.newInstance(driver, by));
						}
						break;
					default:
						if (BrowserElement.class.isAssignableFrom(fieldClass)) {
							FindBy annotation = field.getAnnotation(FindBy.class);
							By by = setFindByToElement(annotation);
							Constructor<?> fieldConstructor = fieldClass.getConstructor(SeleniumDriver.class, By.class);
							field.setAccessible(true);
							BrowserElement element = (BrowserElement) fieldConstructor.newInstance(driver, by);
							FieldUtils.writeField(element, "name", field.getName(), true);
							field.set(page, element);
						}

				}


			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}


	public static <T extends Page> void initElements(SeleniumDriver driver, T page) {
		Class<?> pageClass = page.getClass();
		List<Field> annotatedFields = new ArrayList<Field>();
		while(Page.class.isAssignableFrom(pageClass)) {
			for(Field field: pageClass.getDeclaredFields()) {
				if(field.isAnnotationPresent(FindBy.class) || field.isAnnotationPresent(AndroidFindBy.class) || field.isAnnotationPresent(iOSXCUITFindBy.class)) {
					annotatedFields.add(field);
				}
			}
			pageClass = pageClass.getSuperclass();
		}
		initFields(page, annotatedFields.toArray(new Field[0]), driver);
	}

	public static List<Class> getSuperClasses(Class clazz) {
		List<Class> classList = new ArrayList<Class>();
		Class superclass = clazz.getSuperclass();
		classList.add(superclass);
		while (superclass != null) {
			clazz = superclass;
			superclass = clazz.getSuperclass();
			if(superclass.equals(Page.class) || superclass.equals(Object.class)) {
				classList.remove(Page.class);
				classList.remove(Object.class);
				break;
			}
			classList.add(superclass);

		}
		return classList;
	}

	private static By setFindByToElement(FindBy annotation) {
		By by = null;
		if (null != annotation) {
			String id = annotation.id();
			String xpath = annotation.xpath();
			String ngClick = annotation.ngClick();
			String css = annotation.css();
			String buttonContainsText = annotation.buttonContainsText();
			String buttonText = annotation.buttonText();
			String linkContainsText = annotation.linkContainsText();
			String linkText = annotation.linkText();
			String ngModel = annotation.ngModel();
			String ngRepeat = annotation.ngRepeat();
			String className = annotation.className();
			String name = annotation.name();
			String type = annotation.type();

			if (!id.isEmpty()) {
				by = By.id(id);
			} else if (!xpath.isEmpty()) {
				by = By.xpath(xpath);
			}
			else if (!ngClick.isEmpty()) {
				by = By.xpath(String.format("//*[@ng-click='%s']", ngClick));
			}
			else if (!css.isEmpty()) {
				by = By.cssSelector(css);
			}
			else if (!buttonContainsText.isEmpty()) {
				by = By.xpath(String.format("//button[contains(.,'%s')]", buttonContainsText));
			}
			else if (!buttonText.isEmpty()) {
				by = By.xpath(String.format("//button[text()='%s']", buttonText));
			}
			else if (!linkContainsText.isEmpty()) {
				by = By.xpath(String.format("//a[contains(.,'%s')]", linkContainsText));
			}
			else if (!linkText.isEmpty()) {
				by = By.xpath(String.format("//a[text()='%s']", linkText));
			}
			else if (!ngModel.isEmpty()) {
				by = By.xpath(String.format("//*[@ng-model='%s']", ngModel));
			}
			else if (!ngRepeat.isEmpty()) {
				by = (By.xpath(String.format("//*[@ng-repeat='%s']", ngRepeat)));
			}
			else if (!className.isEmpty()) {
				by = (By.className(className));
			}
			else if (!name.isEmpty()) {
				by = (By.name(name));
			}
			else if (!type.isEmpty()) {
				by = (By.xpath(String.format("//*[@type='%s']", type)));
			}
		}
		return by;
	}

	private static By setAndroidMobileFindByToElement(AndroidFindBy annotation) {
		By by = null;
		if (null != annotation) {
			String id = annotation.id();
			String xpath = annotation.xpath();
			String accessibility = annotation.accessibility();
			String uiAutomator = annotation.uiAutomator();
			String tagName = annotation.tagName();
			if (!id.isEmpty()) {
				by = MobileBy.id(id);
			} else if (!xpath.isEmpty()) {
				by = MobileBy.xpath(xpath);
			}
			else if (!accessibility.isEmpty()) {
				by = MobileBy.AccessibilityId(accessibility);
			}
			else if (!uiAutomator.isEmpty()) {
				by = MobileBy.AndroidUIAutomator(uiAutomator);
			}
			else if (!tagName.isEmpty()) {
				by = MobileBy.AndroidViewTag(tagName);
			}

		}
		return by;
	}

	private static By setIosMobileFindByToElement(iOSXCUITFindBy annotation) {
		By by = null;
		if (null != annotation) {
			String id = annotation.id();
			String xpath = annotation.xpath();
			String accessibility = annotation.accessibility();
			String iOSClassChain = annotation.iOSClassChain();
			String tagName = annotation.tagName();
			String className = annotation.className();
			if (!id.isEmpty()) {
				by = MobileBy.id(id);
			} else if (!xpath.isEmpty()) {
				by = MobileBy.xpath(xpath);
			}
			else if (!accessibility.isEmpty()) {
				by = MobileBy.AccessibilityId(accessibility);
			}
			else if (!iOSClassChain.isEmpty()) {
				by = MobileBy.iOSClassChain(iOSClassChain);
			}
			else if (!tagName.isEmpty()) {
				by = MobileBy.tagName(tagName);
			}
			else if (!className.isEmpty()) {
				by = MobileBy.className(className);
			}

		}
		return by;
	}

	private static Object createList(SeleniumDriver driver, ClassLoader loader, By by, Class<Element> clazz){
		InvocationHandler handler = new CustomInvocationHandler(driver, by, clazz);
		List<Element> elements = (List<Element>)Proxy.newProxyInstance(loader, new Class[] {List.class}, handler);
		return elements;
	}
}