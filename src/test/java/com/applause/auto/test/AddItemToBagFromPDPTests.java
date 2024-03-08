package com.applause.auto.test;

import com.applause.auto.data.types.Category;
import com.applause.auto.data.types.SortByValues;
import com.applause.auto.data.values.MockProducts;
import com.applause.auto.pageobjects.productlistpage.CategoryPage;
import com.applause.auto.pageobjects.productlistpage.chunks.ProductResultSmallView;
import com.applause.auto.pageobjects.commoncomponents.smallviews.bag.BagItem;
import com.applause.auto.pageobjects.commoncomponents.smallviews.bag.BagView;
import com.applause.auto.pageobjects.productlistpage.chunks.sets.ChooseSetPopUp;
import com.applause.auto.pageobjects.productpage.ProductPage;
import com.applause.auto.utils.Description;
import com.applause.auto.utils.ExposedAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import com.applause.auto.pageobjects.homepage.HomePage;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AddItemToBagFromPDPTests extends BaseWebTest {

  private static final Logger logger = LogManager.getLogger(AddItemToBagFromPDPTests.class);

  @Description(name = "Add unvariated product to cart from PDP")
  @Test(description = "C11139076")
  public void addProductToBagFromPDP() {
    HomePage homePage = navigateToLandingPage();
    CategoryPage bodyCategoryPage = homePage.openCategory(Category.BODY);
    List<ProductResultSmallView> productsList = bodyCategoryPage.getProductsResultList();

    ProductPage productPage = productsList.stream()
            .filter(item -> item.getProductName()
                    .equalsIgnoreCase(MockProducts.BODY_UNVARIATED_PRODUCT.getProductName()))
            .findFirst().get().openProduct();

    String productName = productPage.getProductName();
    BigDecimal productPrice = productPage.getProductPrice();

    BagView bagView = productPage.clickAddToBag();
    ExposedAssert.assertTrue("Check if bag is displayed",
            bagView.isBagDisplayed(), "Bag view is not displayed");

    BagItem bagItem = bagView.getBagProducts().get(0);
    String bagProductName = bagItem.getProductName();
    BigDecimal bagProductPrice = bagItem.getProductPrice();
    int bagProductQuantity = bagItem.getProductQuantity();

      ExposedAssert.assertEquals("Check bag has only one product",
              bagView.getBagProducts().size(), 1, "Bag has more than 1 product");

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

      ProductPage productPage = productsList.stream()
              .filter(item -> item.getProductName()
                      .equalsIgnoreCase(MockProducts.BODY_UNVARIATED_PRODUCT.getProductName()))
              .findFirst().get().openProduct();

    String productName = productPage.getProductName();
    BigDecimal productPrice = productPage.getProductPrice();

    BagView bagView = productPage.clickAddToBag();
    ExposedAssert.assertTrue("Check if bag is displayed",
            bagView.isBagDisplayed(), "Bag view is not displayed");
    BagItem bagItem = bagView.getBagProducts().get(0);
    String bagProductName = bagItem.getProductName();

   bagView.closeBagView();
    ExposedAssert.assertFalse("Check if bag is closed",
            bagView.isBagDisplayed(), "Bag view is still displayed");

    bagView = productPage.clickAddToBag();

    BigDecimal bagUpdatedProductPrice = bagItem.getProductPrice();
    BigDecimal bagTotalPrice = bagView.getBagTotalPrice();
    int bagUpdatedProductQuantity = bagItem.getProductQuantity();
    ExposedAssert.assertEquals("Check if product name is correct",
            productName, bagProductName, "Product name doesn't match");

      ExposedAssert.assertEquals("Check bag has only one product",
              bagView.getBagProducts().size(), 1, "Bag has more than 1 product");

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
        BagView bagView = productPage.clickAddToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");

        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        String bagProductShade = bagItem.getProductColor();
        int bagProductQuantity = bagItem.getProductQuantity();

        ExposedAssert.assertEquals("Check bag has only one product",
                bagView.getBagProducts().size(), 1, "Bag has more than 1 product");

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                productName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                productPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product color is correct",
                bagProductShade, shadeOption, "Product color is not correct");
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

        BagView bagView = productPage.clickAddToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");

        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        String bagProductSize = bagItem.getProductSize();
        int bagProductQuantity = bagItem.getProductQuantity();

        ExposedAssert.assertEquals("Check bag has only one product",
                bagView.getBagProducts().size(), 1, "Bag has more than 1 product");

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                productName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                productPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product size is correct",
                bagProductSize, sizeOption, "Product size is not correct");
    }

    @Description(name = "Add product with amount variant to cart from PDP")
    @Test(description = "C11139083")
    public void addProductWithAmountVariantToBagFromPDP() {
        HomePage homePage = navigateToLandingPage();
        CategoryPage shopAllCategoryPage = homePage.openCategory(Category.SHOP_ALL);
        shopAllCategoryPage.sortProductsBy(SortByValues.PRICE_ASCENDING);
        List<ProductResultSmallView> productsList = shopAllCategoryPage.getProductsResultList();
        ProductPage productPage = null;
        for(int i=0; i< productsList.size(); i++){
            if(productsList.get(i).hasAmountVariant()){
                productPage = productsList.get(i).openProduct();
                break;
            }
        }
        List<String> availableAmounts = productPage.getAvailableAmountsList();
        String amountOption = availableAmounts.get(random.nextInt(availableAmounts.size()));
        productPage.selectAmount(amountOption);
        String productName = productPage.getProductName();
        BigDecimal productPrice = productPage.getProductPrice();

        BagView bagView = productPage.clickAddToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");

        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        String bagProductSize = bagItem.getProductAmount();
        int bagProductQuantity = bagItem.getProductQuantity();

        ExposedAssert.assertEquals("Check bag has only one product",
                bagView.getBagProducts().size(), 1, "Bag has more than 1 product");

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                productName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                productPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product amount is correct",
                bagProductSize, amountOption, "Product amount is not correct");
    }

    //TODO add OOS tests here when product available

    @Description(name = "Add unvariated set to cart from PDP")
    @Test(description = "C11139087")
    public void addUnvariatedSetToBagFromPDP() {
        HomePage homePage = navigateToLandingPage();
        CategoryPage setsCategoryPage = homePage.openCategory(Category.SETS);

        List<ProductResultSmallView> productsList = setsCategoryPage.getProductsResultList();
        ProductPage productPage = productsList.stream()
                .filter(product -> product.getProductName()
                        .equalsIgnoreCase(MockProducts.UNVARIATED_SET.getProductName()))
                .findFirst().get().openProduct();

        String setProductName = productPage.getProductName();
        BigDecimal setProductPrice = productPage.getProductPrice();
        List<String> setItemsList = productPage.getSetItemsList()
                .stream().map(item -> item.getSetItemProductName()).collect(Collectors.toList());

        BagView bagView = productPage.clickAddSetToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");

        //Collect bag item data
        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        BigDecimal bagProductPreviousPrice = bagItem.getProductStrikeThroughPrice();
        int bagProductQuantity = bagItem.getProductQuantity();
        List<String> bagSetSubProducts = bagView.getBagProducts().get(0).getSetSubProducts();

        //Collect bag general data
        BigDecimal bagSubTotal = bagView.getBagSubTotalPrice();
        BigDecimal bagSavings = bagView.getBagSavings();
        BigDecimal bagTotalPrice = bagView.getBagTotalPrice();

        Collections.sort(bagSetSubProducts);
        Collections.sort(setItemsList);

        ExposedAssert.assertEquals("Check bag has only one product",
                bagView.getBagProducts().size(), 1, "Bag has more than 1 product");

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                setProductName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if set sub products are correct",
                setItemsList, bagSetSubProducts, "Set sub products are not correct");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                setProductPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check set strike through price is correct on bag page",
                bagSubTotal, bagProductPreviousPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check savings is correctly calculated on bag page",
                bagSavings, bagProductPreviousPrice.subtract(bagProductPrice),
                "Product price doesn't match");

        ExposedAssert.assertEquals("Check bag total price matches product's price",
                bagTotalPrice, bagProductPrice, "Product price doesn't match");
    }

    @Description(name = "Add fixed variant set to cart from PDP")
    @Test(description = "C11139089")
    public void addFixedVariantSetToBagFromPDP() {
        HomePage homePage = navigateToLandingPage();
        CategoryPage setsCategoryPage = homePage.openCategory(Category.SETS);
        List<ProductResultSmallView> productsList = setsCategoryPage.getProductsResultList();

        ProductPage productPage = productsList.stream()
                .filter(item -> item.getProductName()
                        .equalsIgnoreCase(MockProducts.FIXED_VARIATED_SET.getProductName()))
                .findFirst().get().openProduct();

        String setProductName = productPage.getProductName();
        BigDecimal setProductPrice = productPage.getProductPrice();
        List<String> setItemsList = productPage.getSetItemsList()
                .stream().map(item -> item.getSetItemProductName()).collect(Collectors.toList());

        BagView bagView = productPage.clickAddSetToBag();

        ExposedAssert.assertTrue("Check if bag is displayed",
                bagView.isBagDisplayed(), "Bag view is not displayed");

        //Collect bag item data
        BagItem bagItem = bagView.getBagProducts().get(0);
        String bagProductName = bagItem.getProductName();
        BigDecimal bagProductPrice = bagItem.getProductPrice();
        BigDecimal bagProductPreviousPrice = bagItem.getProductStrikeThroughPrice();
        int bagProductQuantity = bagItem.getProductQuantity();
        List<String> bagSetSubProducts = bagView.getBagProducts().get(0).getSetSubProducts();

        //Collect bag general data
        BigDecimal bagSubTotal = bagView.getBagSubTotalPrice();
        BigDecimal bagSavings = bagView.getBagSavings();
        BigDecimal bagTotalPrice = bagView.getBagTotalPrice();

        Collections.sort(bagSetSubProducts);
        Collections.sort(setItemsList);

        ExposedAssert.assertEquals("Check bag has only one product",
                bagView.getBagProducts().size(), 1, "Bag has more than 1 product");

        ExposedAssert.assertEquals("Check if product name is correct on bag page",
                setProductName, bagProductName, "Product name doesn't match");

        ExposedAssert.assertEquals("Check if set sub products are correct",
                setItemsList, bagSetSubProducts, "Set sub products are not correct");

        ExposedAssert.assertEquals("Check if product quantity is 1",
                bagProductQuantity, 1, "Product quantity is not correct");

        ExposedAssert.assertEquals("Check if product price is correct on bag page",
                setProductPrice, bagProductPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check set strike through price is correct on bag page",
                bagSubTotal, bagProductPreviousPrice, "Product price doesn't match");

        ExposedAssert.assertEquals("Check savings is correctly calculated on bag page",
                bagSavings, bagProductPreviousPrice.subtract(bagProductPrice),
                "Product price doesn't match");

        ExposedAssert.assertEquals("Check bag total price matches product's price",
                bagTotalPrice, bagProductPrice, "Product price doesn't match");
    }
}
