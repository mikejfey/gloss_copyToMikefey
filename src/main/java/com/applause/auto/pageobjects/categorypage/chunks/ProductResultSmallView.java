package com.applause.auto.pageobjects.categorypage.chunks;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.popups.YouDeserveItPopUp;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagView;
import com.applause.auto.pageobjects.productpage.ProductPage;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = ProductResultSmallView.class, on = Platform.WEB_DESKTOP)
@Implementation(is = ProductResultSmallView.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = ProductResultSmallView.class, on = Platform.WEB_MOBILE_PHONE)
public class ProductResultSmallView extends BasePage {

  protected static final Logger logger = LogManager.getLogger(ProductResultSmallView.class);

  //TODO add rest of the methods

  public BagView addToBag(){
    step("Add product to bag - %s", getProductName());
    Helper.logicWithPopUpHandle(
            YouDeserveItPopUp.class, 15,
            "Add product to bag", logic -> Helper.waitAndClick(addToBag));
    return SdkHelper.create(BagView.class);
  }

  public ProductPage openProduct(){
    step("Open product - %s", getProductName());
    Helper.logicWithPopUpHandle(
            YouDeserveItPopUp.class, 15,
            "Open product", logic -> Helper.waitAndClick(getParent()));
    return SdkHelper.create(ProductPage.class);
  }

  public String getProductName(){
    logger.info("Collect product name");
    return productName.getText();
  }

  public BigDecimal getProductPrice(){
    logger.info("Collect product price");
    return BigDecimal.valueOf(
            Double.parseDouble(productPrice.getText()
                    .replace(",", ".")
                    .replaceAll("\\D", "").trim()));
  }

  public boolean hasShadesVariant(){
    logger.info("Check if product has shades");
    return Helper.isElementPresent(shadesContainer, 2);
  }

  public boolean hasSizeVariant(){
    logger.info("Check if product has size");
    return Helper.isElementPresent(sizeContainer, 2);
  }

  public List<String> getAvailableShadesList(){
    logger.info("Collect all available shades");
    ((LazyList<ContainerElement>) availableShadesList).initialize();
    return availableShadesList.stream()
            .map(item -> item.getAttributeValue("data-option-value")
                    .trim()).collect(Collectors.toList());
  }

  public void selectShade(String value){
    step("Select shade %s", value);
    ((LazyList<ContainerElement>) availableShadesList).initialize();
    ContainerElement option = availableShadesList.stream()
            .filter(item -> item.getAttributeValue("data-option-value").equals(value))
            .findFirst().get();
    Helper.logicWithPopUpHandle(
            YouDeserveItPopUp.class, 15,
            "Select shade", logic -> Helper.waitAndClick(option));
  }

  public List<ContainerElement> getAvailableSizesList(){
    ((LazyList<ContainerElement>) availableSizesList).initialize();
    return availableSizesList;
  }

  @Locate(xpath = ".//h3/a", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = ".//div[@class='pi__desc-wrapper-top']//span[@class='pi__price--current']", on = Platform.WEB)
  private Text productPrice;

  @Locate(xpath = ".//ul[@class='config__options config__options--color list-reset']", on = Platform.WEB)
  private ContainerElement shadesContainer;

  @Locate(xpath = ".//ul[@class='config__options config__options--size list-reset']", on = Platform.WEB)
  private ContainerElement sizeContainer;

  @Locate(xpath = ".//div[@class='config__group config__group--color js-option-group']/ul/li", on = Platform.WEB)
  private List<ContainerElement> availableShadesList;

  @Locate(xpath = ".//ul[@class='config__options config__options--size list-reset']/li", on = Platform.WEB)
  private List<ContainerElement> availableSizesList;

  @Locate(xpath = ".//button[@name='add']", on = Platform.WEB)
  private Button addToBag;

  @Locate(xpath = "//button[@id='bagClose']", on = Platform.WEB)
  private Button closeBagButton;
}

