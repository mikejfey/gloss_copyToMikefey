package com.applause.auto.test;

import com.applause.auto.pageobjects.popups.CookiesPopUp;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.integrations.base.ApplauseSeleniumTest;
import com.applause.auto.pageobjects.homepage.HomePage;
import com.applause.auto.utils.ExecutionHelper;

public abstract class BaseWebTest extends ApplauseSeleniumTest {
  protected static final Logger logger = LogManager.getLogger(BaseWebTest.class);

  @Override
  @AfterMethod(alwaysRun = true)
  public void resetContext() {
    super.resetContext();
  }

  /**
   * Navigates to the application's base URLa
   *
   * @return HomePage
   */
  @SuppressWarnings("unlikely-arg-type")
  protected HomePage navigateToLandingPage() {
    maximiseDesktopBrowsers();
    String url = ExecutionHelper.getRunningConfiguration().getUrl();
    logger.info("Navigating to landing page: {}", url);
    SdkHelper.getDriver().get(url);
    HomePage homePage = SdkHelper.create(HomePage.class);
    handleCookiesPolicy();
    logger.info("Waiting for landing page to be visible");
    homePage.waitForApplicationToLoad();
    return homePage;
  }

  private void handleCookiesPolicy(){
    CookiesPopUp cookiesPopUp = SdkHelper.create(CookiesPopUp.class);
    cookiesPopUp.acceptCookies();
  }

  private void maximiseDesktopBrowsers(){
    if (!Helper.isDevice()) {
      logger.debug("Maximise the browser window...");
      SdkHelper.getDriver().manage().window().maximize();
    }
  }
}
