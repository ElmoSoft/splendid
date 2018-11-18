package net.elmosoft.splendid.utils;

import io.qameta.allure.Attachment;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Reporter;

/**
 * @author Aleksei_Mordas
 *
 *         e-mail: * alexey.mordas@gmail.com Skype: alexey.mordas
 */
public class ReportAppender extends AppenderSkeleton {
	@Override
	protected void append(LoggingEvent event) {

		String log = getLayout().format(event);
		log = StringUtils.replace(log, "\n", "</br>");
		Reporter.log(log, false);
		saveLog(log);

	}

	@Attachment(value = "{0}", type = "text/plain")
	public String saveLog(String message) {
		return message;
	}

	@Override
	public void close() {

		if (this.closed)
			return;
		this.closed = true;

	}

	@Override
	public boolean requiresLayout() {

		return true;
	}

}
