package com.applause.auto.pageobjects.commoncomponents.popups;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.utils.Helper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = YouDeserveItPopUp.class, on = Platform.WEB_DESKTOP)
@Implementation(is = YouDeserveItPopUp.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = YouDeserveItPopUp.class, on = Platform.WEB_MOBILE_PHONE)
public class YouDeserveItPopUp extends GlossierBasePopUp {

  protected static final Logger logger = LogManager.getLogger(YouDeserveItPopUp.class);

  public boolean isDisplayed(int maxWaitTimeForPopUp){
    return Helper.isElementDisplayed(popUp, maxWaitTimeForPopUp);
  }

  @SneakyThrows
  public void close(){
    step("Closing You Deserve It Popup");
    //Helper.hideWebElement(popUpLayer.getWebElement(), false);
    Helper.hideAllElements("needsclick  kl-private-reset-css-Xuajs1");
  }

  @Locate(xpath = "//div[@aria-label='POPUP Form']", on = Platform.WEB)
  private ContainerElement popUp;

  @Locate(xpath = "//div[@aria-label='POPUP Form']//parent::div//parent::div//parent::div", on = Platform.WEB)
  private ContainerElement popUpLayer;

}

