package com.applause.auto.pageobjects.homepage.chunks;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.helpers.sync.one.UiElementCondition;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjects.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = MarketSelect.class, on = Platform.WEB_DESKTOP)
@Implementation(is = MarketSelect.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = MarketSelect.class, on = Platform.WEB_MOBILE_PHONE)
public class MarketSelect extends BasePage {

  protected static final Logger logger = LogManager.getLogger(MarketSelect.class);

  public String getCurrentMarketValue(){
    logger.info("Collecting current market value");
    return currentMarketButton.getText().trim();
  }

  public void openMarketSelect(){
    step("Open market select");
    currentMarketButton.click();
    SdkHelper.getSyncHelper().wait(Until.uiElement(countrySelect).visible());
  }

  public void selectMarket(String value){
    step("Select market %s", value);
    String visibleText = countrySelect.getOptions().stream().filter(
            option -> option.getAttributeValue("value").equals(value)).findFirst().get().getText();
    countrySelect.select(visibleText);
    SdkHelper.getSyncHelper().wait(Until.uiElement(countrySelect)
            .meetsCustomCondition(element -> countrySelect.getSelectedOption().getText().equals(visibleText)));
  }

  public void clickSave(){
    step("Click on save button");
    saveButton.click();
  }

  @Locate(xpath = "//button[@class='btn-link header__navigation-item-btn header__navigation-item-btn--country-picker js-header-country-selector']", on = Platform.WEB)
  private Button currentMarketButton;

  @Locate(xpath = "//select[@name='country_code']", on = Platform.WEB)
  private SelectList countrySelect;

  @Locate(xpath = "//button[@class='btn btn--primary btn--gray-4 modal__content-button modal__content-button__header js-country-selector-save']", on = Platform.WEB)
  private Button saveButton;
}

