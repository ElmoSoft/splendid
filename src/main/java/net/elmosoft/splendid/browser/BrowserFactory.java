package net.elmosoft.splendid.browser;

import org.openqa.selenium.WebDriver;

/**
 * @author Aleksei_Mordas
 *
 */
public abstract class BrowserFactory {

	public abstract WebDriver createBrowser(boolean acceptUntrustedCerts,
			boolean assumeUntrustedIssuer);

	public abstract WebDriver createBrowser();

}
