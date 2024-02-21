package com.applause.auto.test;

import com.applause.auto.data.Constants.TestNGGroups.Browsers;
import com.applause.auto.data.Constants.TestNGGroups.Environments;
import com.applause.auto.data.values.Category;
import com.applause.auto.pageobjects.categorypage.CategoryPage;
import com.applause.auto.pageobjects.categorypage.chunks.ProductResultSmallView;
import com.applause.auto.pageobjects.checkoutpage.CheckoutInformationTabPage;
import com.applause.auto.pageobjects.homepage.HomePage;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagItem;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagView;
import com.applause.auto.utils.ExposedAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckoutTests extends BaseWebTest {

  private static final Logger logger = LogManager.getLogger(CheckoutTests.class);

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
      description = "C11139068")
  public void takeProductToCheckout() {
    HomePage homePage = navigateToLandingPage();
    CategoryPage bodyCategoryPage = homePage.openCategory(Category.BODY);
    List<ProductResultSmallView> productsList = bodyCategoryPage.getProductsResultList();
    productsList.get(1).addToBag();
    BagView bagView = homePage.openBag();
    CheckoutInformationTabPage checkoutInformationTabPage = bagView.clickCheckoutButton();
    ExposedAssert.assertTrue("Check if cart page is displayed",
            checkoutInformationTabPage.isPageDisplayed(), "Cart page is not displayed");
  }
}
