package com.applause.auto.pageobjects.checkoutpage;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjects.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = CheckoutInformationTabPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = CheckoutInformationTabPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = CheckoutInformationTabPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CheckoutInformationTabPage extends BasePage {

  protected static final Logger logger = LogManager.getLogger(CheckoutInformationTabPage.class);

  @Override
  public void afterInit() {
    step("Waiting for Cart Page to be displayed");
    waitForPageToLoad(container, "Cart Page", 10);
  }

  public boolean isPageDisplayed(){
    return container.isDisplayed();
  }

  @Locate(xpath = "//div[@class='content checkout-page-wrap checkout-content']", on = Platform.WEB)
  private ContainerElement container;

}

