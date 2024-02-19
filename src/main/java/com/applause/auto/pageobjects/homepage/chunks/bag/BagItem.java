package com.applause.auto.pageobjects.homepage.chunks.bag;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = BagItem.class, on = Platform.WEB_DESKTOP)
@Implementation(is = BagItem.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = BagItem.class, on = Platform.WEB_MOBILE_PHONE)
public class BagItem extends BasePage {

  protected static final Logger logger = LogManager.getLogger(BagItem.class);

  //TODO add rest of the methods

  public String getProductName(){
    return productName.getText();
  }

  public String getProductPrice(){
    return productPrice.getText();
  }

  @Locate(xpath = ".//h4", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = ".//span[@class='bag-item__original-price']", on = Platform.WEB)
  private Text productPrice;
}

