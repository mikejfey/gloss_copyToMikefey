package com.applause.auto.pageobjects.commoncomponents.smallviews.search;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = Search.class, on = Platform.WEB_DESKTOP)
@Implementation(is = Search.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = Search.class, on = Platform.WEB_MOBILE_PHONE)
public class Search extends BasePage {

  protected static final Logger logger = LogManager.getLogger(Search.class);

  @Override
  public void afterInit() {
    step("Waiting for Search to be displayed");
    waitForPageToLoad(searchField, "Search", 10);
  }

  public Search searchProduct(String text){
    step("Search for - %s", text);
    searchField.sendKeys(text);
    SdkHelper.getSyncHelper().wait(Until.uiElement(searchResultsContainer).visible());
    return this;
  }

  public List<SearchResult> getSearchResultsList(){
    ((LazyList<SearchResult>) resultsList).initialize();
    SdkHelper.getSyncHelper().wait(Until.allOf(resultsList).visible());
    return resultsList;
  }

  public void clearSearch() {
    step("Clear search");
    clearSearch.click();
  }

  public void closeSearch() {
    step("Close search");
    closeSearch.click();
  }

  @Locate(xpath = "//input[@class='input input--search']", on = Platform.WEB)
  private TextBox searchField;

  @Locate(xpath = "//div[@class='predictive-search__results']", on = Platform.WEB)
  private ContainerElement searchResultsContainer;

  @Locate(xpath = "//button[@type='reset']", on = Platform.WEB)
  private Button clearSearch;

  @Locate(xpath = "//span[@class='icon icon--close-black']//parent::button", on = Platform.WEB)
  private Button closeSearch;

  @Locate(xpath = "//a[@class='predictive-search__item js-predictive-search-item']", on = Platform.WEB)
  private List<SearchResult> resultsList;
}

