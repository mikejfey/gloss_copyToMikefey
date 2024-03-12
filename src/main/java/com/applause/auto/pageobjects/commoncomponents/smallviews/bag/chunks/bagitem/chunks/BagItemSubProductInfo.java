package com.applause.auto.pageobjects.commoncomponents.smallviews.bag.chunks.bagitem.chunks;

import com.applause.auto.core.GlossierConfig;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.popups.YouDeserveItPopUp;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = BagItemSubProductInfo.class, on = Platform.WEB_DESKTOP)
@Implementation(is = BagItemSubProductInfo.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = BagItemSubProductInfo.class, on = Platform.WEB_MOBILE_PHONE)
public class BagItemSubProductInfo extends BasePage {

  protected static final Logger logger = LogManager.getLogger(BagItemSubProductInfo.class);

  public String getProductName(){
    logger.info("Collect bag item sub product name");
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).present());
    return productName.getText().trim();
  }

  public boolean hasColorSwatch(){
    logger.info("Check if product {} has color swatch", productName.getText());
    return Helper.isElementPresent(colorSwatch, 1);
  }

  //TODO in future try to collect also swatch color  code and maybe assert it with value provided by shopify API

  @Locate(xpath = "./span[@class='bag-item__variant']", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = "./div[@class='bag-item__variant bag-item__variant-color js-bag-item-variant']", on = Platform.WEB)
  private ContainerElement colorSwatch;
}

