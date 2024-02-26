package com.applause.auto.pageobjects.homepage.chunks.bag;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.checkoutpage.CheckoutInformationTabPage;
import com.applause.auto.utils.Helper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = BagView.class, on = Platform.WEB_DESKTOP)
@Implementation(is = BagView.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = BagView.class, on = Platform.WEB_MOBILE_PHONE)
public class BagView extends BasePage {

  protected static final Logger logger = LogManager.getLogger(BagView.class);

  @Override
  public void afterInit() {
    step("Waiting for Bag view to be displayed");
    SdkHelper.getSyncHelper().wait(Until.uiElement(container)
            .meetsCustomCondition(
                    element -> element.getAttributeValue("aria-hidden").equalsIgnoreCase("false")));
  }

  public boolean isBagDisplayed(){
    return container.getAttributeValue("aria-hidden").equalsIgnoreCase("false")
            && closeButton.isDisplayed();
  }

  public List<BagItem> getBagProducts(){
    logger.info("Collect all bag products");
    ((LazyList<BagItem>) bagProductsList).initialize();
    SdkHelper.getSyncHelper().wait(Until.allOf(bagProductsList).visible());
    return bagProductsList;
  }

  @SneakyThrows
  public int getBagProductsNumber(){
    logger.info("Collect bag items counter value");
    TimeUnit.SECONDS.sleep(2); //temporary approach to wait for counter to update
    return Integer.parseInt(bagCounter.getText().replaceAll("\\D", "").trim());
  }

  public BigDecimal getBagTotalPrice(){
    logger.info("Collect bag total price");
    String collectedText = totalPrice.getText().trim();
    int index = collectedText.trim().indexOf(" ");
    return BigDecimal.valueOf(
            Double.parseDouble(collectedText.substring(0, index)
                    .replace(",", ".")));
  }

  public CheckoutInformationTabPage clickCheckoutButton() {
    step("Click on checkout button");
    checkoutButton.click();
    return SdkHelper.create(CheckoutInformationTabPage.class);
  }

  public void closeBagView() {
    step("Click on close bag button");
    closeButton.click();
    SdkHelper.getSyncHelper().wait(Until.uiElement(container)
            .meetsCustomCondition(
                    element -> element.getAttributeValue("aria-hidden").equalsIgnoreCase("true")
            && !Helper.isElementDisplayed(closeButton, 5)));
  }

  @Locate(xpath = "//aside[@id='bag']", on = Platform.WEB)
  private ContainerElement container;

  @Locate(xpath = "//div[@id='bagItems']/article", on = Platform.WEB)
  private List<BagItem> bagProductsList;

  @Locate(xpath = "//p[@id='bagTotal']", on = Platform.WEB)
  private Text totalPrice;

  @Locate(xpath = "//button[@id='bagBtn']//span[@class='js-bag-btn-count']", on = Platform.WEB)
  private Text bagCounter;

  @Locate(xpath = "//button[@id='bagClose']", on = Platform.WEB)
  private Button closeButton;

  @Locate(xpath = "//a[@class='bag__action bag__checkout btn btn--tertiary btn--full']", on = Platform.WEB)
  private Button checkoutButton;

}

