package net.elmosoft.splendid.service.runner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestObjectFactory;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.HTMLReporter;

public class TestRunner {

	private TestNG testNG = new TestNG();

	private static final String REPORTNG_ESCAPE_PROP = "org.uncommons.reportng.escape-output";

	private static final String TEST_OUTPUT_FOLDER = "test-output";

	private static final Logger LOGGER = Logger.getLogger(TestRunner.class);

	@SuppressWarnings("rawtypes")
	private List<Class> listeners = new ArrayList<Class>();

	public TestRunner(String[] args) {


		listeners.add(HTMLReporter.class);
		configureLogger();

	}

	private void configureLogger() {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("log4j.properties");

		Properties prop = new Properties();
		try {
			prop.load(url.openStream());
		} catch (IOException e) {
			LOGGER.error("Used default log4j settings");
			e.printStackTrace();
		}
		PropertyConfigurator.configure(prop);
	}

	@SuppressWarnings("rawtypes")
	public void setListeners(List<Class> listeners) {
		this.listeners = listeners;
	}



	public static void main(String[] args) {

		try {
			TestRunner tr = new TestRunner(args);
			@SuppressWarnings("rawtypes")
			List<Class> listeners = new ArrayList<Class>();
			listeners.add(HTMLReporter.class);
			listeners.add(SuiteListener.class);
			tr.setListeners(listeners);
			tr.run();
		} catch (Exception e) {
			LOGGER.info(e);
			throw new RuntimeException(e);
		} finally {
		}
	}

	public void run() {
		try {
			String[] suites = TestNgParameters.getInstance().getSuites();
			List<XmlSuite> suiteList = new ArrayList<XmlSuite>();
			for (String suite : suites) {
				suiteList.addAll(new Parser(suite).parseToList());
			}
//			testNG.setXmlSuites(suiteList);
//			testNG.setUseDefaultListeners(true);
//			testNG.setListenerClasses(listeners);
//			System.setProperty(REPORTNG_ESCAPE_PROP, "false");
			File dir = new File(TEST_OUTPUT_FOLDER);
			deleteDir(dir);
			dir.mkdirs();
			testNG.run();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			int exitCode = BuildResult.getExitResult();
			LOGGER.info("Exit with code : " + exitCode);
			System.exit(exitCode);
		}
	}

	public TestNG getTestNG() {
		return this.testNG;
	}

	public void setObjectFactory(ITestObjectFactory objectFactory) {
		testNG.setObjectFactory(objectFactory);
	}

	private boolean deleteDir(File dir) {
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDir(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (dir.delete());
	}

}
