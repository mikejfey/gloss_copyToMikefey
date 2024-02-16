package com.applause.auto.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.applause.auto.data.Constants.TestNGGroups.Browsers;
import com.applause.auto.data.Constants.TestNGGroups.Environments;
import com.applause.auto.pageobjects.homepage.HomePage;

public class CartTests extends BaseWebTest {

  private static final Logger logger = LogManager.getLogger(CartTests.class);

  @Test(
      groups = {
        Browsers.CHROME_DESKTOP,
        Browsers.FIREFOX_DESKTOP,
        Browsers.EDGE_DESKTOP,
        Browsers.SAFARI_DESKTOP,
        Browsers.ANDROID_PHONE,
        Browsers.ANDROID_TABLET,
        Browsers.IOS_PHONE,
        Browsers.IOS_TABLET,
        Environments.PRE_LIVE,
        Environments.LIVE
      },
      description = "C11139056")
  public void testAddProductToCart() {
    HomePage homePage = navigateToLandingPage();

  }
}
