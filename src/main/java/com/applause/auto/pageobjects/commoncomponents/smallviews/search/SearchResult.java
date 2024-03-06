package com.applause.auto.pageobjects.commoncomponents.smallviews.search;

import com.applause.auto.core.GlossierConfig;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.productpage.ProductPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = SearchResult.class, on = Platform.WEB_DESKTOP)
@Implementation(is = SearchResult.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = SearchResult.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchResult extends BasePage {

  protected static final Logger logger = LogManager.getLogger(SearchResult.class);

  public String getProductName(){
    logger.info("Collect search result product name");
    return productName.getText().trim();
  }

  public BigDecimal getProductPrice(){
    logger.info("Collect search result product price");
    return BigDecimal.valueOf(
            Double.parseDouble(productPrice.getText()
                    .replace(GlossierConfig.getCurrencySymbol(), "").trim()));
  }

  public ProductPage openProduct(){
    step("Open product - %s", getProductName());
    getParent().click();
    return SdkHelper.create(ProductPage.class);
  }

  @Locate(xpath = ".//span[@class='predictive-search__item-title-txt']", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = ".//span[@class='predictive-search__item-title-price']", on = Platform.WEB)
  private Text productPrice;

}

