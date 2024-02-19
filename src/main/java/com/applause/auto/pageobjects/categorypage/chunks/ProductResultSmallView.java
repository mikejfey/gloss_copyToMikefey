package com.applause.auto.pageobjects.categorypage.chunks;

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

@Implementation(is = ProductResultSmallView.class, on = Platform.WEB_DESKTOP)
@Implementation(is = ProductResultSmallView.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = ProductResultSmallView.class, on = Platform.WEB_MOBILE_PHONE)
public class ProductResultSmallView extends BasePage {

  protected static final Logger logger = LogManager.getLogger(ProductResultSmallView.class);

  //TODO add rest of the methods

  public void addToBag(){
    step("Adding product to bag - " + getProductName());
    Helper.waitAndClick(addToBag);
  }

  public String getProductName(){
    return productName.getText();
  }

  public String getProductPrice(){
    return productPrice.getText();
  }

  @Locate(xpath = ".//h3/a", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = ".//div[@class='pi__desc-wrapper-top']//span[@class='pi__price--current']", on = Platform.WEB)
  private Text productPrice;

  @Locate(xpath = ".//button[@name='add']", on = Platform.WEB)
  private Button addToBag;
}

