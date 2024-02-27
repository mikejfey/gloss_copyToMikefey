package com.applause.auto.test;

import com.applause.auto.data.Constants.TestNGGroups.Browsers;
import com.applause.auto.data.Constants.TestNGGroups.Environments;
import com.applause.auto.data.values.Category;
import com.applause.auto.pageobjects.categorypage.CategoryPage;
import com.applause.auto.pageobjects.categorypage.chunks.ProductResultSmallView;
import com.applause.auto.pageobjects.homepage.HomePage;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagItem;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagView;
import com.applause.auto.pageobjects.homepage.chunks.search.Search;
import com.applause.auto.pageobjects.homepage.chunks.search.SearchResult;
import com.applause.auto.pageobjects.productpage.ProductPage;
import com.applause.auto.utils.ExposedAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.List;

public class SearchTests extends BaseWebTest {

  private static final Logger logger = LogManager.getLogger(SearchTests.class);

//  @Test(
//      groups = {
//        Browsers.CHROME_DESKTOP,
//        Browsers.FIREFOX_DESKTOP,
//        Browsers.EDGE_DESKTOP,
//        Browsers.SAFARI_DESKTOP,
//        Browsers.ANDROID_PHONE,
//        Browsers.ANDROID_TABLET,
//        Browsers.IOS_PHONE,
//        Browsers.IOS_TABLET,
//        Environments.PRE_LIVE,
//        Environments.LIVE
//      },
//      description = "C11139098")
//  public void searchProduct() {
//    HomePage homePage = navigateToLandingPage();
//    Search search = homePage.openSearch();
//    search.searchProduct("Milky Jelly Cleanser");
//    List<SearchResult> searchResults = search.getSearchResultsList();
//    String searchResultProductName = searchResults.get(0).getProductName();
//    ExposedAssert.assertTrue("Check that search returned results",
//            searchResults.size() > 0, "No results displayed");
//    ProductPage productPage = searchResults.get(0).openProduct();
//    String productPageProductName = productPage.getProductName();
//    ExposedAssert.assertTrue("Check if product page is displayed",
//            productPage.isPageDisplayed(), "Product page is not displayed");
//    ExposedAssert.assertEquals("Check product name matches",
//            productPageProductName, searchResultProductName, "Product name doesn't match");
//  }
}
