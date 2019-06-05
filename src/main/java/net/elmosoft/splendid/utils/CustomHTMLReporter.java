package net.elmosoft.splendid.utils;

import org.testng.ISuite;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.HTMLReporter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The purpose of this custom HTML reporter to sort suites in the report.
 *
 */
public class CustomHTMLReporter extends HTMLReporter {

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectoryName) {
		Comparator<ISuite> suiteComparator = new TestSuiteComparator(xmlSuites);
		suites.sort(suiteComparator);
		super.generateReport(xmlSuites, suites, outputDirectoryName);
	}

	public class TestSuiteComparator implements Comparator<ISuite> {

		public List<String> xmlNames;

		public TestSuiteComparator(List<XmlSuite> parentXmlSuites) {
			for (XmlSuite parentXmlSuite : parentXmlSuites) {
				List<XmlSuite> childXmlSuites = parentXmlSuite.getChildSuites();
				xmlNames = new ArrayList<String>();
				xmlNames.add(parentXmlSuite.getFileName());
				for (XmlSuite xmlsuite : childXmlSuites) {
					xmlNames.add(xmlsuite.getFileName());
				}
			}
		}

		@Override
		public int compare(ISuite suite1, ISuite suite2) {
			String suite1Name = suite1.getXmlSuite().getFileName();
			String suite2Name = suite2.getXmlSuite().getFileName();
			return xmlNames.indexOf(suite1Name) - xmlNames.indexOf(suite2Name);
		}
	}
}
