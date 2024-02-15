package com.applause.auto.pageobjects.popups;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.utils.Helper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Implementation(is = CookiesPopUp.class, on = Platform.WEB_DESKTOP)
@Implementation(is = CookiesPopUp.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = CookiesPopUp.class, on = Platform.WEB_MOBILE_PHONE)
public class CookiesPopUp extends BasePage {

  protected static final Logger logger = LogManager.getLogger(CookiesPopUp.class);

  @Locate(xpath = "//button[@id='ensCancel']", on = Platform.WEB)
  protected Button acceptCookiesButton;

  @SneakyThrows
  public void acceptCookies() {
    try {
      logger.info("Clicking on Accept cookies button");
      Helper.waitAndClick(acceptCookiesButton);
      logger.info("Waiting for accept cookies button to disappear");
      SdkHelper.getSyncHelper().wait(Until.uiElement(acceptCookiesButton).notVisible());
    }
    catch (Exception any) {
      throw new Exception("Failed to Accept cookies : " + any.getMessage());
    }
  }
}
