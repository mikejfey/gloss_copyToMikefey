package com.applause.auto.pageobjects.homepage.chunks.bag;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.popups.YouDeserveItPopUp;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = BagItem.class, on = Platform.WEB_DESKTOP)
@Implementation(is = BagItem.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = BagItem.class, on = Platform.WEB_MOBILE_PHONE)
public class BagItem extends BasePage {

  protected static final Logger logger = LogManager.getLogger(BagItem.class);

  //TODO add rest of the methods

  public String getProductName(){
    logger.info("Collect product name");
    return productName.getText();
  }

  public BigDecimal getProductPrice(){
    logger.info("Collect product price");
    return BigDecimal.valueOf(
            Double.parseDouble(productPrice.getText()
                    .replace(",", ".")
                    .replaceAll("\\D", "").trim()));
  }

  public int getProductQuantity(){
    logger.info("Collect product quantity");
    return Integer.parseInt(productQuantity.getText().trim());
  }

  public String getProductColor(){
    logger.info("Collect product color");
    return productColor.getText();
  }

  public void removeProduct(){
    step("Remove product [%s] from bag", getProductName());
    Helper.logicWithPopUpHandle(
            YouDeserveItPopUp.class, 15,
            "Add product to bag", logic -> Helper.waitAndClick(removeButton));
  }

  @Locate(xpath = ".//h4", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = ".//span[@class='bag-item__original-price']", on = Platform.WEB)
  private Text productPrice;

  @Locate(xpath = ".//div[@class='bag-item__info--bottom']/div/div/p", on = Platform.WEB)
  private Text productQuantity;

  @Locate(xpath = ".//span[@class='bag-item__variant']", on = Platform.WEB)
  private Text productColor;

  @Locate(xpath = ".//span[@class='bag-item__remove-label']", on = Platform.WEB)
  private Button removeButton;
}

