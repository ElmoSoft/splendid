package net.elmosoft.splendid.driver.page;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import net.elmosoft.splendid.driver.element.Element;
import net.elmosoft.splendid.driver.seleniumdriver.SeleniumDriver;
import org.openqa.selenium.By;

import net.elmosoft.splendid.driver.annotation.FindBy;


/**
 * @author Aleksei_Mordas
 *
 */
public class PageFactory {

	public static <T extends Page> T initElements(SeleniumDriver driver,
			Class<T> pageClass) {
		T page = instantiatePage(driver, pageClass);
		initElements(driver, page);
		List<Class> list = getSuperClasses(page.getClass());
		for( Class superClass: list){
			initFields(page, superClass.getDeclaredFields(), driver );
		}
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

				if (Element.class.isAssignableFrom(fieldClass)) {
					FindBy annotation = field.getAnnotation(FindBy.class);
					By by = setFindByToElement(annotation);
					Constructor<?> fieldConstructor = fieldClass.getConstructor(SeleniumDriver.class, By.class);
					field.setAccessible(true);
					field.set(page, fieldConstructor.newInstance(driver, by));
				}

			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}


	public static <T extends Page> void initElements(SeleniumDriver driver, T page) {
		initFields(page, page.getClass().getDeclaredFields(), driver);
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

	private static Object createList(SeleniumDriver driver, ClassLoader loader, By by, Class<Element> clazz){
		InvocationHandler handler = new CustomInvocationHandler(driver, by, clazz);
		List<Element> elements = (List<Element>)Proxy.newProxyInstance(loader, new Class[] {List.class}, handler);
		return elements;
	}
}