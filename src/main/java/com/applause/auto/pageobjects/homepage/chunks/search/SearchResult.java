package com.applause.auto.pageobjects.homepage.chunks.search;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjects.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Implementation(is = SearchResult.class, on = Platform.WEB_DESKTOP)
@Implementation(is = SearchResult.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = SearchResult.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchResult extends BasePage {

  protected static final Logger logger = LogManager.getLogger(SearchResult.class);

  public void getProductName(){
    productName.getText();
  }

  public void getProductPrice(){
    productPrice.getText();
  }

  @Locate(xpath = ".//span[@class='predictive-search__item-title-txt']", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = ".//span[@class='predictive-search__item-title-price']", on = Platform.WEB)
  private Text productPrice;

}

