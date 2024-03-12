package com.applause.auto.pageobjects.commoncomponents.smallviews.bag.chunks.bagitem;

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
import com.applause.auto.pageobjects.commoncomponents.smallviews.bag.chunks.bagitem.chunks.BagItemSubProductInfo;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = BagItem.class, on = Platform.WEB_DESKTOP)
@Implementation(is = BagItem.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = BagItem.class, on = Platform.WEB_MOBILE_PHONE)
public class BagItem extends BasePage {

  protected static final Logger logger = LogManager.getLogger(BagItem.class);

  public String getProductName(){
    logger.info("Collect product name");
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).present());
    return productName.getText().trim();
  }

  public BigDecimal getProductPrice(){
    logger.info("Collect bag product {} price", productName.getText());
    SdkHelper.getSyncHelper().wait(Until.uiElement(productPrice).present());
    return BigDecimal.valueOf(
            Double.parseDouble(productPrice.getText()
                    .replace(GlossierConfig.getCurrencySymbol(), "").trim()));
  }

  public BigDecimal getProductStrikeThroughPrice(){
    logger.info("Collect bag product {} strike through price", productName.getText());
    return BigDecimal.valueOf(
            Double.parseDouble(productStrikeThroughPrice.getText()
                    .replace(GlossierConfig.getCurrencySymbol(), "").trim()));
  }

  public int getProductQuantity(){
    logger.info("Collect product quantity");
    return Integer.parseInt(productQuantity.getText().trim());
  }

  public String getProductColor(){
    logger.info("Collect product color");
    return productColor.getText().trim();
  }

  public String getProductSize(){
    logger.info("Collect product size");
    return productSize.getText().trim();
  }

  public String getProductAmount(){
    logger.info("Collect amount size");
    return amountSize.getText().trim();
  }

  public List<String> getSetSubProductsNames(){
    logger.info("Collect bag set sub products names");
    ((LazyList<Text>) setSubProductsNameList).initialize();
    SdkHelper.getSyncHelper().wait(Until.allOf(setSubProductsNameList).visible());
    return setSubProductsNameList.stream().map(Text::getText).collect(Collectors.toList());
  }

  public List<BagItemSubProductInfo> getSetSubProductsInformation(){
    logger.info("Collect bag set sub products information");
    ((LazyList<BagItemSubProductInfo>) setSubProductsInformationList).initialize();
    SdkHelper.getSyncHelper().wait(Until.allOf(setSubProductsInformationList).visible());
    return setSubProductsInformationList;
  }

  public void removeProduct(){
    step("Remove product [%s] from bag", getProductName());
    Helper.logicWithPopUpHandle(
            YouDeserveItPopUp.class, 15,
            "Add product to bag", logic -> Helper.waitAndClick(removeButton));
  }

  @Locate(xpath = ".//h4", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = ".//span[@class='bag-item__original-price' or @class='bag-item__sale-price']", on = Platform.WEB)
  private Text productPrice;

  @Locate(xpath = ".//span[@class='bag-item__original-price bag-item__original-price--strike-through']", on = Platform.WEB)
  private Text productStrikeThroughPrice;

  @Locate(xpath = ".//div[@class='bag-item__info--bottom']/div/div/p", on = Platform.WEB)
  private Text productQuantity;

  @Locate(xpath = ".//span[@class='bag-item__variant']", on = Platform.WEB)
  private Text productColor;

  @Locate(xpath = ".//span[@class='bag-item__variant']", on = Platform.WEB)
  private Text productSize;

  @Locate(xpath = ".//span[@class='bag-item__variant']", on = Platform.WEB)
  private Text amountSize;

  @Locate(xpath = ".//span[@class='bag-item__variant']", on = Platform.WEB)
  private List<Text> setSubProductsNameList;

  @Locate(xpath = ".//div[@class='bag-item__variant-wrapper']", on = Platform.WEB)
  private List<BagItemSubProductInfo> setSubProductsInformationList;

  @Locate(xpath = ".//span[@class='bag-item__remove-label']", on = Platform.WEB)
  private Button removeButton;
}

