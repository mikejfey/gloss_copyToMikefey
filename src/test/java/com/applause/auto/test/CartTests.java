package com.applause.auto.test;

import com.applause.auto.data.values.Category;
import com.applause.auto.data.values.SortByValues;
import com.applause.auto.pageobjects.categorypage.CategoryPage;
import com.applause.auto.pageobjects.categorypage.chunks.ProductResultSmallView;
import com.applause.auto.pageobjects.commoncomponents.smallviews.bag.BagItem;
import com.applause.auto.pageobjects.commoncomponents.smallviews.bag.BagView;
import com.applause.auto.pageobjects.productpage.ProductPage;
import com.applause.auto.utils.Description;
import com.applause.auto.utils.ExposedAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import com.applause.auto.pageobjects.homepage.HomePage;

import java.math.BigDecimal;
import java.util.List;

public class CartTests extends BaseWebTest {

  private static final Logger logger = LogManager.getLogger(CartTests.class);

  @Description(name = "Add unvariated product to cart from PLP")
  @Test(description = "C11139075")
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

  @Description(name = "Add unvariated product to cart from PDP")
  @Test(description = "C11139076")
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


  @Description(name = "Add unvariated product to cart when it is already present in cart")
  @Test(description = "C11139077")
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

    @Description(name = "Add product with shade variant to cart from PLP")
    @Test(description = "C11139078")
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

    @Description(name = "Add product with shade variant to cart from PDP")
    @Test(description = "C11139079")
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

    @Description(name = "Add product with size variant to cart from PLP")
    @Test(description = "C11139080")
    public void addProductWithSizeVariantToBagFromPLP() {
        HomePage homePage = navigateToLandingPage();
        CategoryPage balmsCategoryPage = homePage.openCategory(Category.SKINCARE);
        List<ProductResultSmallView> productsList = balmsCategoryPage.getProductsResultList();
        ProductResultSmallView product = null;
        for(int i=0; i< productsList.size(); i++){
            if(productsList.get(i).hasSizeVariant()){
                product = productsList.get(i);
                break;
            }
        }
        List<String> availableSizes = product.getAvailableSizesList();
        String sizeOption = availableSizes.get(random.nextInt(availableSizes.size()));
        product.selectSize(sizeOption);
        String productName = product.getProductName();
        BigDecimal productPrice = product.getProductPrice();

        BagView bagView = product.addToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");

        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        String bagProductSize = bagItem.getProductSize();
        int bagProductQuantity = bagItem.getProductQuantity();

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                productName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                productPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product size is correct",
                bagProductSize, sizeOption, "Product size is not correct");
    }

    @Description(name = "Add product with size variant to cart from PDP")
    @Test(description = "C11139081")
    public void addProductWithSizeVariantToBagFromPDP() {
        HomePage homePage = navigateToLandingPage();
        CategoryPage balmsCategoryPage = homePage.openCategory(Category.SKINCARE);
        List<ProductResultSmallView> productsList = balmsCategoryPage.getProductsResultList();
        ProductPage productPage = null;
        for(int i=0; i< productsList.size(); i++){
            if(productsList.get(i).hasSizeVariant()){
                productPage = productsList.get(i).openProduct();
                break;
            }
        }
        List<String> availableSizes = productPage.getAvailableSizesList();
        String sizeOption = availableSizes.get(random.nextInt(availableSizes.size()));
        productPage.selectSize(sizeOption);
        String productName = productPage.getProductName();
        BigDecimal productPrice = productPage.getProductPrice();

        BagView bagView = productPage.addToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");

        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        String bagProductSize = bagItem.getProductSize();
        int bagProductQuantity = bagItem.getProductQuantity();

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                productName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                productPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product size is correct",
                bagProductSize, sizeOption, "Product size is not correct");
    }

    @Description(name = "Add product with amount variant to cart from PLP")
    @Test(description = "C11139082")
    public void addProductWithAmountVariantToBagFromPLP() {
        HomePage homePage = navigateToLandingPage();
        CategoryPage shopAllCategoryPage = homePage.openCategory(Category.SHOP_ALL);
        shopAllCategoryPage.sortProductsBy(SortByValues.PRICE_ASCENDING);
        List<ProductResultSmallView> productsList = shopAllCategoryPage.getProductsResultList();
        ProductResultSmallView product = null;
        for(int i=0; i< productsList.size(); i++){
            if(productsList.get(i).hasAmountVariant()){
                product = productsList.get(i);
                break;
            }
        }
        List<String> availableAmounts = product.getAvailableAmountsList();
        String amountOption = availableAmounts.get(random.nextInt(availableAmounts.size()));
        product.selectAmount(amountOption);
        String productName = product.getProductName();
        BigDecimal productPrice = product.getProductPrice();

        BagView bagView = product.addToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");

        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        String bagProductSize = bagItem.getProductAmount();
        int bagProductQuantity = bagItem.getProductQuantity();

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                productName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                productPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product amount is correct",
                bagProductSize, amountOption, "Product amount is not correct");
    }

}
