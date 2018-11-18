package net.elmosoft.splendid.driver.element;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WaitConditions{
	
	 public static ExpectedCondition<Boolean> textToBePresentInElementAttribute(
			 final By locator, final String attribute, final String value) {

		    return new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver driver) {
		        try {
		          String elementAttributeValue = driver.findElement(locator).getAttribute(attribute);
		          return elementAttributeValue.contains(value);
		        } catch (StaleElementReferenceException e) {
		          return null;
		        }
		      }

		      @Override
		      public String toString() {
		        return String.format("Wait for attribute ('%s') = value ('%s') to be present in element found by locator %s",
		            attribute, value, locator);
		      }
		    };
		  }
	 
	 
	 public static ExpectedCondition<Boolean> ajaxToFinishLoading() {

		    return new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver driver) {
		          String script = "return jQuery.active == 0 ||  jQuery.active == 1"; 	
		          return (Boolean) ((JavascriptExecutor)driver).executeScript(script);
		      }

		      @Override
		      public String toString() {
		        return String.format("Wait for ajax to finish loading");
		      }
		    };
		  }

	 public static ExpectedCondition<Boolean> htmlToFinishLoading() {

		    return new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver driver) {
		          String script = "return document.readyState"; 
		          return (Boolean) ((JavascriptExecutor)driver).executeScript(script).equals("complete");
		      }

		      @Override
		      public String toString() {
		        return String.format("Wait for html to finish loading");
		      }
		    };
		  }


	 public static ExpectedCondition<Boolean> textToBeNotPresentInElementAttribute(
			 final By locator, final String attribute, final String value) {

		    return new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver driver) {
		        try {
		          String elementAttributeValue = driver.findElement(locator).getAttribute(attribute);
		          return !(elementAttributeValue.contains(value));
		        } catch (StaleElementReferenceException e) {
		          return null;
		        }
		      }

		      @Override
		      public String toString() {
		        return String.format("Wait for attribute ('%s') = value ('%s') to be NOT present in element found by locator %s",
		            attribute, value, locator);
		      }
		    };
		  }
	 

	 public static ExpectedCondition<Boolean> textToBeNotEqualToTextInElementLocated(
		      final By locator, final String text) {

		    return new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver driver) {
		        try {
		          String elementText = driver.findElement(locator).getText();
		          return !elementText.contains(text);
		        } catch (StaleElementReferenceException e) {
		          return null;
		        }
		      }

		      @Override
		      public String toString() {
		        return String.format("text ('%s') to be not equal to text in element found by %s",
		            text, locator);
		      }
		    };
		  }

}
