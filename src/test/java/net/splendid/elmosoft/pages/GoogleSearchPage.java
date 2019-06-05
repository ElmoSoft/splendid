package net.splendid.elmosoft.pages;

import net.elmosoft.splendid.driver.annotation.FindBy;
import net.elmosoft.splendid.driver.element.BrowserElement;
import net.elmosoft.splendid.driver.page.Page;

import java.util.List;

public class GoogleSearchPage extends Page {

    @FindBy(css="[valign='top'] > td")
    private List<BrowserElement> size;

    public GoogleSearchPage() {
        super();
    }

    @Override
    public void checkPage() {
        waitForPageIsLoaded();
    }

    @Override
    public void waitForPageIsLoaded() {
        driver.waitForPageLoad();
    }

    public int getNumberOfPages() {
        final int size = this.size.size();
        LOGGER.info("Total pages size: "+ size);
        return size;
    }

}
