package net.elmosoft.splendid.utils;

import java.io.Serializable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.testng.Reporter;

import io.qameta.allure.Attachment;

/**
 * @author Aleksei_Mordas
 *
 *         e-mail: * alexey.mordas@gmail.com Skype: alexey.mordas
 */
@Plugin(name = "ReportAppender", category = "Core", elementType = "appender", printObject = true)
public class ReportAppender extends AbstractAppender {

	protected ReportAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
		super(name, filter, layout);
	}

	public void append(LogEvent event) {
		String log = StringUtils.buildString(new String(getLayout().toByteArray(event)), "<br>");
		if (event.getLevel().equals(Level.WARN)) {
			Reporter.log("<font color=\"red\">" + log + "</font>");
		} else {
			Reporter.log(log);
		}
		saveLog(log);
	}

	@Attachment(value = "{0}", type = "text/plain")
	public String saveLog(String message) {
		return message;
	}

	@PluginFactory
	public static ReportAppender createAppender(@PluginAttribute("name") String name,
												@PluginElement("filter") Filter filter, @PluginElement("layout") Layout<LogEvent> layout) {
		if (name == null) {
			LOGGER.error("No name provided for ReportAppender");
			return null;
		}

		if (layout == null) {
			LOGGER.error("No layout provided for ReportAppender");
			return null;
		}
		return new ReportAppender(name, filter, layout);
	}
}
