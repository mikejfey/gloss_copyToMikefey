package com.applause.auto.pageobjects.homepage;

import com.applause.auto.pageobjectmodel.elements.*;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.utils.ExecutionHelper;
import lombok.SneakyThrows;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;

@Implementation(is = HomePage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = HomePage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = HomePage.class, on = Platform.WEB_MOBILE_PHONE)
public class HomePage extends BasePage {

  @Override
  public void afterInit() {
    waitForApplicationToLoad();
  }

  @SneakyThrows
  public void waitForApplicationToLoad() {
    try{
      logger.info("Waiting for Landing Page container to be present");
      SdkHelper.getSyncHelper().wait(Until.uiElement(container).present());
      SdkHelper.getSyncHelper().wait(Until.uiElement(container).visible());
      container.scrollToElement();
    }
    catch (Exception any){
      throw new Exception("Failed to wait after Landing Page container to be displayed");
    }
  }


  public boolean isPageDisplayed() {
    return container.isDisplayed() &&
            SdkHelper.getDriver().getCurrentUrl().contains(ExecutionHelper.getRunningConfiguration().getUrl());
  }

  @Locate(xpath = "//div[@class='nm-wrapper-content']", on = Platform.WEB)
  private ContainerElement container;

  @Locate(xpath = "//select[@name='sorting-helper']", on = Platform.WEB)
  private SelectList sorting;

  @Locate(xpath = "//button[@data-testid='plt_product-list__loadMore']", on = Platform.WEB)
  private Button loadMoreResults;

  @Locate(xpath = "//*[contains(text(),'%s') and contains(text(),'von')]", on = Platform.WEB)
  private Text numberOfResults;

  @Locate(xpath = "//div[@id='psyma_layer_background']", on = Platform.WEB)
  private ContainerElement surveyContainer;

  @Locate(xpath = "//div[@id='psyma_close_button_link']", on = Platform.WEB)
  private ContainerElement closeSurveyButton;
}

