/**
 * 
 */
package net.elmosoft.splendid.driver.exceptions;

/**
 * @author Aleksei_Mordas
 *
 *         e-mail: * alexey.mordas@gmail.com Skype: alexey.mordas
 */
public class WaitTimeoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3545061457061570901L;

	public WaitTimeoutException(String message) {
		super(message);
	}

}
