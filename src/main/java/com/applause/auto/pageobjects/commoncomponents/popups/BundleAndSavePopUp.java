package com.applause.auto.pageobjects.commoncomponents.popups;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.utils.Helper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = BundleAndSavePopUp.class, on = Platform.WEB_DESKTOP)
@Implementation(is = BundleAndSavePopUp.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = BundleAndSavePopUp.class, on = Platform.WEB_MOBILE_PHONE)
public class BundleAndSavePopUp extends GlossierBasePopUp {

  protected static final Logger logger = LogManager.getLogger(BundleAndSavePopUp.class);

  public boolean isDisplayed(int maxWaitTimeForPopUp){
    return Helper.isElementDisplayed(popUp, maxWaitTimeForPopUp);
  }

  @SneakyThrows
  public void close(){
    step("Closing Bundle and Save Popup by clicking on Skip For Now, to open Bag");
    Helper.waitAndClick(skipForNowUpsellModalButton);
  }

  @Locate(xpath = "//div[@id='interstitialUpsellModal']", on = Platform.WEB)
  private ContainerElement popUp;

  @Locate(xpath = "//button[@id='interstitialUpsellSkipForNow']", on = Platform.WEB)
  private Button skipForNowUpsellModalButton;

}

