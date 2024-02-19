package com.applause.auto.pageobjects.homepage.chunks.bag;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.categorypage.chunks.ProductResultSmallView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = BagView.class, on = Platform.WEB_DESKTOP)
@Implementation(is = BagView.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = BagView.class, on = Platform.WEB_MOBILE_PHONE)
public class BagView extends BasePage {

  protected static final Logger logger = LogManager.getLogger(BagView.class);

  @Override
  public void afterInit() {
    step("Waiting for Bag view to be displayed");
    waitForPageToLoad(container, "Bag View", 10);
  }

  public List<BagItem> getBagProducts(){
    ((LazyList<BagItem>) bagProductsList).initialize();
    SdkHelper.getSyncHelper().wait(Until.allOf(bagProductsList).visible());
    return bagProductsList;
  }


  @Locate(xpath = "//form[@id='bagForm']", on = Platform.WEB)
  private ContainerElement container;

  @Locate(xpath = "//div[@id='bagItems']/article", on = Platform.WEB)
  private List<BagItem> bagProductsList;
}

