package com.applause.auto.pageobjects.productlistpage.chunks.sets.chunks;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.utils.Helper;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = SetItem.class, on = Platform.WEB_DESKTOP)
@Implementation(is = SetItem.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = SetItem.class, on = Platform.WEB_MOBILE_PHONE)
public class SetItem extends BasePage {

  protected static final Logger logger = LogManager.getLogger(SetItem.class);

  @Setter
  @Getter
  String setItemProductName;

  @Setter
  @Getter
  String setItemAppliedSize;

  @Override
  //The " in " addition it's for bag validations. If other test will require different logic, find another solution
  public void afterInit() {
    step("Collecting Set items");
    if(Helper.isElementPresent(appliedSize, 2)){
      setSetItemProductName(productName.getText().trim() + " in " + appliedSize.getText());
    }
    else{
      setSetItemProductName(productName.getText().trim());
    }
  }

  @Locate(xpath = ".//a[@class='product-set__item-title']", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = ".//span[@class='js-product-set-variant-title']", on = Platform.WEB)
  private Text appliedSize;


}

