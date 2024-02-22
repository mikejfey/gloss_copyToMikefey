package com.applause.auto.pageobjects.commoncomponents.popups;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
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

  public boolean isDisplayed(int maxWaitingTime){
     return Helper.isElementDisplayed(cookiesPopUp, maxWaitingTime);
  }

  @SneakyThrows
  public void acceptCookies() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(cookiesPopUp).visible());
    acceptCookiesButton.click();
    SdkHelper.getSyncHelper().wait(Until.uiElement(cookiesPopUp).notVisible());
  }

  @Locate(xpath = "//button[@id='onetrust-accept-btn-handler']", on = Platform.WEB)
  protected Button acceptCookiesButton;

  @Locate(xpath = "//div[@id='onetrust-banner-sdk']", on = Platform.WEB)
  protected ContainerElement cookiesPopUp;
}
