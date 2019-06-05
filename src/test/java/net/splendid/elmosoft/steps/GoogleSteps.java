package net.splendid.elmosoft.steps;

import io.qameta.allure.Step;
import net.splendid.elmosoft.pages.GoogleMainPage;
import net.splendid.elmosoft.pages.GoogleSearchPage;

public class GoogleSteps {
    @Step("Make search with phraze: ${searchPhraze}")
    public void doSearch(String searchPhraze) {
        GoogleMainPage googleMainPage = new GoogleMainPage();
        googleMainPage.openPage().checkPage();
        googleMainPage.doSearch(searchPhraze);
    }

    public int getNumberOfPages() {
        return new GoogleSearchPage().getNumberOfPages();
    }
}
