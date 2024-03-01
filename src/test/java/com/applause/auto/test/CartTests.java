package com.applause.auto.test;

import com.applause.auto.data.values.Category;
import com.applause.auto.pageobjects.categorypage.CategoryPage;
import com.applause.auto.pageobjects.categorypage.chunks.ProductResultSmallView;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagItem;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagView;
import com.applause.auto.pageobjects.productpage.ProductPage;
import com.applause.auto.utils.ExposedAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.applause.auto.data.Constants.TestNGGroups.Browsers;
import com.applause.auto.data.Constants.TestNGGroups.Environments;
import com.applause.auto.pageobjects.homepage.HomePage;

import java.math.BigDecimal;
import java.util.List;

public class CartTests extends BaseWebTest {

  private static final Logger logger = LogManager.getLogger(CartTests.class);

  //Add unvariated product to cart from PLP
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
      description = "C11139075")
  public void addProductToBagFromPLP() {
    HomePage homePage = navigateToLandingPage();
    CategoryPage bodyCategoryPage = homePage.openCategory(Category.BODY);
    List<ProductResultSmallView> productsList = bodyCategoryPage.getProductsResultList();
    String productName = productsList.get(1).getProductName();
    BigDecimal productPrice = productsList.get(1).getProductPrice();
    BagView bagView = productsList.get(1).addToBag();
    ExposedAssert.assertTrue("Check if bag is displayed",
            bagView.isBagDisplayed(), "Bag view is not displayed");
    BagItem bagItem = bagView.getBagProducts().get(0);
    String bagProductName = bagItem.getProductName();
    BigDecimal bagProductPrice = bagItem.getProductPrice();
    int bagProductQuantity = bagItem.getProductQuantity();
    ExposedAssert.assertEquals("Check if product name is correct on bag page",
            productName, bagProductName, "Product name doesn't match");
    ExposedAssert.assertEquals("Check if product price is correct on bag page",
            productPrice, bagProductPrice, "Product price doesn't match");
    ExposedAssert.assertEquals("Check if product quantity is 1",
            bagProductQuantity, 1, "Product quantity is not correct");
  }

  //Add unvariated product to cart from PDP
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
          description = "C11139076")
  public void addProductToBagFromPDP() {
    HomePage homePage = navigateToLandingPage();
    CategoryPage bodyCategoryPage = homePage.openCategory(Category.BODY);
    List<ProductResultSmallView> productsList = bodyCategoryPage.getProductsResultList();
    ProductPage productPage = productsList.get(1).openProduct();
    String productName = productPage.getProductName();
    BigDecimal productPrice = productPage.getProductPrice();

    BagView bagView = productPage.addToBag();
    ExposedAssert.assertTrue("Check if bag is displayed",
            bagView.isBagDisplayed(), "Bag view is not displayed");

    BagItem bagItem = bagView.getBagProducts().get(0);
    String bagProductName = bagItem.getProductName();
    BigDecimal bagProductPrice = bagItem.getProductPrice();
    int bagProductQuantity = bagItem.getProductQuantity();
    ExposedAssert.assertEquals("Check if product name is correct on bag page",
            productName, bagProductName, "Product name doesn't match");
    ExposedAssert.assertEquals("Check if product price is correct on bag page",
            productPrice, bagProductPrice, "Product price doesn't match");
    ExposedAssert.assertEquals("Check if product quantity is 1",
            bagProductQuantity, 1, "Product quantity is not correct");
  }

  //Add unvariated product to cart when it is already present in cart
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
          description = "C11139077")
  public void addAgainExistingBagProduct() {
    HomePage homePage = navigateToLandingPage();
    CategoryPage bodyCategoryPage = homePage.openCategory(Category.BODY);
    List<ProductResultSmallView> productsList = bodyCategoryPage.getProductsResultList();
    ProductPage productPage = productsList.get(1).openProduct();
    String productName = productPage.getProductName();
    BigDecimal productPrice = productPage.getProductPrice();

    BagView bagView = productPage.addToBag();
    ExposedAssert.assertTrue("Check if bag is displayed",
            bagView.isBagDisplayed(), "Bag view is not displayed");
    BagItem bagItem = bagView.getBagProducts().get(0);
    String bagProductName = bagItem.getProductName();

   bagView.closeBagView();
    ExposedAssert.assertFalse("Check if bag is closed",
            bagView.isBagDisplayed(), "Bag view is still displayed");

    bagView = productPage.addToBag();

    BigDecimal bagUpdatedProductPrice = bagItem.getProductPrice();
    BigDecimal bagTotalPrice = bagView.getBagTotalPrice();
    int bagUpdatedProductQuantity = bagItem.getProductQuantity();
    ExposedAssert.assertEquals("Check if product name is correct",
            productName, bagProductName, "Product name doesn't match");

    ExposedAssert.assertEquals("Check if product price is correct",
            productPrice.multiply(BigDecimal.valueOf(2)),
            bagUpdatedProductPrice, "Product price doesn't match");

    ExposedAssert.assertEquals("Check if bag total price is correct",
            bagTotalPrice,
            productPrice.multiply(BigDecimal.valueOf(2)),
            "Bag total price doesn't match");

    ExposedAssert.assertEquals("Check if product quantity is 2",
            bagUpdatedProductQuantity, 2, "Product quantity is not correct");

    ExposedAssert.assertEquals("Check if bag product counter is 2",
            bagView.getBagProductsNumber(), 2, "Bag total products counter is not correct");
  }

    //Add product with shade variant to cart from PLP
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
            description = "C11139078")
    public void addVariatedProductToBagFromPLP() {
        HomePage homePage = navigateToLandingPage();
        CategoryPage balmsCategoryPage = homePage.openCategory(Category.BALMS);
        List<ProductResultSmallView> productsList = balmsCategoryPage.getProductsResultList();
        ProductResultSmallView productView = null;
        for(int i=0; i< productsList.size(); i++){
            if(productsList.get(i).hasShadesVariant()){
                productView = productsList.get(i);
                break;
            }
        }
        List<String> availableShades = productView.getAvailableShadesList();
//        String shadeOption = availableShades.get(random.nextInt(availableShades.size()));
        String shadeOption = availableShades.get(1);
        String productName = productView.getProductName();
        BigDecimal productPrice = productView.getProductPrice();

        productView.selectShade(shadeOption);
        BagView bagView = productView.addToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");
        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        String bagProductShade = bagItem.getProductColor();
        int bagProductQuantity = bagItem.getProductQuantity();

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                productName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                productPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product color is correct",
                bagProductShade, shadeOption, "Product color is not correct");
    }

    //Add product with shade variant to cart from PDP
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
            description = "C11139079")
    public void addVariatedProductToBagFromPDP() {
        HomePage homePage = navigateToLandingPage();
        CategoryPage balmsCategoryPage = homePage.openCategory(Category.BALMS);
        List<ProductResultSmallView> productsList = balmsCategoryPage.getProductsResultList();
        ProductPage productPage = null;
        for(int i=0; i< productsList.size(); i++){
            if(productsList.get(i).hasShadesVariant()){
                productPage = productsList.get(1).openProduct();
                break;
            }
        }
        List<String> availableShades = productPage.getAvailableShadesList();
        String shadeOption = availableShades.get(random.nextInt(availableShades.size()));
        String productName = productPage.getProductName();
        BigDecimal productPrice = productPage.getProductPrice();

        productPage.selectShade(shadeOption);
        BagView bagView = productPage.addToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");

        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        String bagProductShade = bagItem.getProductColor();
        int bagProductQuantity = bagItem.getProductQuantity();

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                productName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                productPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product color is correct",
                bagProductShade, shadeOption, "Product color is not correct");
    }

//  @Test(
//          groups = {
//                  Browsers.CHROME_DESKTOP,
//                  Browsers.FIREFOX_DESKTOP,
//                  Browsers.EDGE_DESKTOP,
//                  Browsers.SAFARI_DESKTOP,
//                  Browsers.ANDROID_PHONE,
//                  Browsers.ANDROID_TABLET,
//                  Browsers.IOS_PHONE,
//                  Browsers.IOS_TABLET,
//                  Environments.PRE_LIVE,
//                  Environments.LIVE
//          },
//          description = "C11139067")
//  public void removeProductFromBag() {
//    HomePage homePage = navigateToLandingPage();
//    CategoryPage fragranceCategoryPage = homePage.openCategory(Category.FRAGRANCE);
//    List<ProductResultSmallView> productsList = fragranceCategoryPage.getProductsResultList();
//    BagView bagView  = productsList.get(0).addToBag();
//    int bagTotalItems = bagView.getBagProductsNumber();
//    ExposedAssert.assertEquals("Check bag counter is 1",
//            bagTotalItems, 1, "Total number of bag items doesn't match");
//    bagView.getBagProducts().get(0).removeProduct();
//    bagTotalItems = bagView.getBagProductsNumber();
//    ExposedAssert.assertEquals("Check bag counter is 0",
//            bagTotalItems, 0, "Total number of bag items doesn't match");
//  }
}
