package net.splendid.elmosoft.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.elmosoft.splendid.service.runner.SuiteListener;
import net.elmosoft.splendid.test.BaseSplendidTest;
import net.splendid.elmosoft.steps.GoogleSteps;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;

import com.google.inject.Inject;

@Listeners({ SuiteListener.class, HTMLReporter.class })
@Epic("Demo Tests")
@Feature("REX Tab")
@Guice
public class GoogleTest extends BaseSplendidTest {

	@Inject
	private GoogleSteps googleSteps;

	@Test(description = "Do search")
	public void doSearchOnGooglePage() {
		googleSteps.doSearch("Elmosoft minsk");
		Assert.assertTrue(googleSteps.getNumberOfPages() > 0, "There are no pages");
	}
}
