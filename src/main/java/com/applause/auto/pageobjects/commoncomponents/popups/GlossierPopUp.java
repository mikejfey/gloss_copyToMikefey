package com.applause.auto.pageobjects.commoncomponents.popups;

import com.applause.auto.pageobjects.BasePage;

public abstract class GlossierPopUp extends BasePage {

    public abstract boolean isDisplayed(int maxWaitTimeForPopUp);

    public abstract void close();
}
