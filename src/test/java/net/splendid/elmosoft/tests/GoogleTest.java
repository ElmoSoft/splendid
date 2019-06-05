package net.splendid.elmosoft.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import net.elmosoft.splendid.service.runner.SuiteListener;
import net.splendid.elmosoft.steps.GoogleSteps;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;

@Listeners({ SuiteListener.class, HTMLReporter.class })
@Epic("Demo Tests")
@Feature("REX Tab")
public class GoogleTest extends BaseSplendidTest {

    @Test(description = "Do search")
    public void doSearchOnGooglePage() {
        GoogleSteps steps = new GoogleSteps();
        steps.doSearch("Elmosoft minsk");
        Assert.assertTrue(steps.getNumberOfPages()>0, "There are no pages");
    }
}
