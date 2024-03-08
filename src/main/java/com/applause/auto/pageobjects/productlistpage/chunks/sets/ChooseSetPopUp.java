package com.applause.auto.pageobjects.productlistpage.chunks.sets;

import com.applause.auto.core.GlossierConfig;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.values.Category;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.smallviews.bag.BagView;
import com.applause.auto.pageobjects.productlistpage.chunks.ProductResultSmallView;
import com.applause.auto.pageobjects.productlistpage.chunks.sets.chunks.SetItem;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = ChooseSetPopUp.class, on = Platform.WEB_DESKTOP)
@Implementation(is = ChooseSetPopUp.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = ChooseSetPopUp.class, on = Platform.WEB_MOBILE_PHONE)
public class ChooseSetPopUp extends BasePage {

  protected static final Logger logger = LogManager.getLogger(ChooseSetPopUp.class);

  private String name;

  @Override
  public void afterInit() {
    step("Waiting for Choose Set Popup to be displayed");
    container.format(name).initialize();
    waitForPageToLoad(container, "Choose Set Popup", 10);
  }

  public ChooseSetPopUp(String name){
    this.name = name;
  }

  public boolean isChooseSetPopUpDisplayed(){
    return container.isDisplayed();
  }

  public BagView clickAddSetToBag(){
    step("Click add set to bag");
    addSetToBagButton.format(name).initialize();
    Helper.waitAndClick(addSetToBagButton);
    return SdkHelper.create(BagView.class);
  }

  public BigDecimal getSetPrice(){
    logger.info("Collecting set price");
    currentPrice.format(name).initialize();
    return BigDecimal.valueOf(
            Double.parseDouble(currentPrice.getText()
                    .replace(GlossierConfig.getCurrencySymbol(), "").trim()));
  }

  public List<SetItem> getSetItemsList(){
    ((LazyList<SetItem>) setItemsList).format(name).initialize();
    SdkHelper.getSyncHelper().wait(Until.allOf(setItemsList).visible());
    return setItemsList;
  }


  @Locate(xpath = "(//div[@class='product-set__header']/h3[contains(text(),'%s')]//parent::div//parent::div//parent::div)[1]", on = Platform.WEB)
  private ContainerElement container;

  @Locate(xpath = "(//div[@class='product-set__header']/h3[contains(text(),'%s')]//parent::div//parent::div//parent::div)[1]//button[@data-testid='setModalATC']", on = Platform.WEB)
  private Button addSetToBagButton;

  @Locate(xpath = "(//div[@class='product-set__header']/h3[contains(text(),'%s')]//parent::div//parent::div//parent::div)[1]//span[@class='product-set__atc-price']", on = Platform.WEB)
  private Button currentPrice;

  @Locate(xpath = "(//div[@class='product-set__header']/h3[contains(text(),'%s')]//parent::div//parent::div//parent::div)[1]//ul[@class='product-set__list list-reset']/li", on = Platform.WEB)
  private List<SetItem> setItemsList;

}

