package com.applause.auto.pageobjects.productlistpage.chunks;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.popups.YouDeserveItPopUp;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = SortProductsFeature.class, on = Platform.WEB_DESKTOP)
@Implementation(is = SortProductsFeature.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = SortProductsFeature.class, on = Platform.WEB_MOBILE_PHONE)
public class SortProductsFeature extends BasePage {

  protected static final Logger logger = LogManager.getLogger(SortProductsFeature.class);

  public void sortBy(String value){
    step("Select sort by %s", value);
    String visibleText = sortDropDown.getOptions().stream().filter(
            option -> option.getAttributeValue("value").equals(value)).findFirst().get().getText();
    Helper.logicWithPopUpHandle(
            YouDeserveItPopUp.class, 15,
            "Apply sort", logic -> sortDropDown.select(visibleText));
    SdkHelper.getSyncHelper().wait(Until.uiElement(sortDropDown)
            .meetsCustomCondition(element -> sortDropDown.getSelectedOption().getText().equals(visibleText)));
  }

  @Locate(xpath = "//div[@class='collection-filters hide-mobile']//select[@aria-label='Sort Options']", on = Platform.WEB)
  private SelectList sortDropDown;
}

