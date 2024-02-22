package com.applause.auto.test;

import com.applause.auto.data.values.Category;
import com.applause.auto.pageobjects.categorypage.CategoryPage;
import com.applause.auto.pageobjects.categorypage.chunks.ProductResultSmallView;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagItem;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagView;
import com.applause.auto.utils.ExposedAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.applause.auto.data.Constants.TestNGGroups.Browsers;
import com.applause.auto.data.Constants.TestNGGroups.Environments;
import com.applause.auto.pageobjects.homepage.HomePage;

import java.util.List;

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
  public void addProductToBag() {
    HomePage homePage = navigateToLandingPage();
    CategoryPage bodyCategoryPage = homePage.openCategory(Category.BODY);
    List<ProductResultSmallView> productsList = bodyCategoryPage.getProductsResultList();
    String productName = productsList.get(1).getProductName();
    String productPrice = productsList.get(1).getProductPrice();
    productsList.get(1).addToBag();
    BagView bagView = homePage.openBag();
    BagItem bagItem = bagView.getBagProducts().get(0);
    String bagProductName = bagItem.getProductName();
    String bagProductPrice = bagItem.getProductPrice();
    ExposedAssert.assertEquals("Check if product name is correct on bag page",
            productName, bagProductName, "Product name doesn't match");
    ExposedAssert.assertEquals("Check if product price is correct on bag page",
            productPrice, bagProductPrice, "Product price doesn't match");
  }

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
          description = "C11139067")
  public void removeProductFromBag() {
    HomePage homePage = navigateToLandingPage();
    CategoryPage fragranceCategoryPage = homePage.openCategory(Category.FRAGRANCE);
    List<ProductResultSmallView> productsList = fragranceCategoryPage.getProductsResultList();
    BagView bagView  = productsList.get(0).addToBag();
    int bagTotalItems = bagView.getBagProductsNumber();
    ExposedAssert.assertEquals("Check bag counter is 1",
            bagTotalItems, 1, "Total number of bag items doesn't match");
    bagView.getBagProducts().get(0).removeProduct();
    bagTotalItems = bagView.getBagProductsNumber();
    ExposedAssert.assertEquals("Check bag counter is 0",
            bagTotalItems, 0, "Total number of bag items doesn't match");
  }
}
