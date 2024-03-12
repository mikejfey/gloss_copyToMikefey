package com.applause.auto.pageobjects.productlistpage.chunks.sets.chunks;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.popups.YouDeserveItPopUp;
import com.applause.auto.utils.Helper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = SetItem.class, on = Platform.WEB_DESKTOP)
@Implementation(is = SetItem.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = SetItem.class, on = Platform.WEB_MOBILE_PHONE)
public class SetItem extends BasePage {

  protected static final Logger logger = LogManager.getLogger(SetItem.class);

  @Setter
  @Getter
  String itemProductName;

  @Setter
  @Getter
  String itemAppliedVariant;

  @Override
  //The " in " addition it's for bag validations. If other test will require different logic, find another solution
  public void afterInit() {
    step("Collecting Set items");
    collectProductDefaultValues();
  }

  public void collectProductDefaultValues(){
    try{
      ((LazyList<Text>) appliedVariantValue).initialize();
    }
    catch (Exception e1) {
      logger.info("No variant found for product {}", productName.getText());
      appliedVariantValue = null;
    }
    if(Objects.nonNull(appliedVariantValue)){
      updateOrSetProductInformation();
    }
    else{
      setItemProductName(productName.getText().trim());
    }
  }

  //Collected value for name will be used to compare the prodct name in bag
  //Note that "in" + variant is important for variated products
  private void updateOrSetProductInformation(){
    setItemProductName(productName.getText().trim() + " in " + appliedVariantValue.get(0).getText());
    setItemAppliedVariant(appliedVariantValue.get(0).getText());
  }

  public boolean hasShadesVariant(){
    try{
      ((LazyList<ContainerElement>) availableShadesList).initialize();
      return true;
    }
    catch (Exception e1) {
      return false;
    }
  }

  public boolean hasSizeVariant(){
    try{
      ((LazyList<ContainerElement>) availableSizesList).initialize();
      return true;
    }
    catch (Exception e1) {
      return false;
    }
  }

  @SneakyThrows
  public List<String> getAvailableShadesList(){
    logger.info("Collect all available shades");
    try{
      ((LazyList<ContainerElement>) availableShadesList).initialize();
      return availableShadesList.stream()
              .map(item -> item.getAttributeValue("data-option-value")
                      .trim()).collect(Collectors.toList());
    }
    catch (NoSuchElementException noSuchElementException){
      throw new Exception("Item has no available shades to select");
    }
  }

  @SneakyThrows
  public void selectShade(String value){
    step("Select shade %s", value);
    ((LazyList<ContainerElement>) availableShadesList).initialize();
    ContainerElement option = availableShadesList.stream()
            .filter(item -> item.getAttributeValue("data-option-value").equals(value))
            .findFirst().get();
    Helper.logicWithPopUpHandle(
            YouDeserveItPopUp.class, 15,
            "Select shade", logic -> Helper.waitAndClick(option));
    updateOrSetProductInformation();
  }

  @SneakyThrows
  public List<String> getAvailableSizesList(){
    logger.info("Collect all available shades");
    try{
      ((LazyList<ContainerElement>) availableSizesList).initialize();
      return availableSizesList.stream()
              .map(item -> item.getAttributeValue("data-option-value")
                      .trim()).collect(Collectors.toList());
    }
    catch (NoSuchElementException noSuchElementException){
      throw new Exception("Item has no available sizes to select");
    }
  }

  public void selectSize(String value){
    step("Select shade %s", value);
    ((LazyList<ContainerElement>) availableSizesList).initialize();
    ContainerElement option = availableSizesList.stream()
            .filter(item -> item.getAttributeValue("data-option-value").equals(value))
            .findFirst().get();
    Helper.logicWithPopUpHandle(
            YouDeserveItPopUp.class, 15,
            "Select size", logic -> Helper.waitAndClick(option));
    updateOrSetProductInformation();
  }


  @Locate(xpath = ".//a[@class='product-set__item-title']", on = Platform.WEB)
  private Text productName;

  //This works the same for size/amount etc
  @Locate(xpath = ".//span[@class='js-product-set-variant-title']", on = Platform.WEB)
  private List<Text> appliedVariantValue;

  //Index 1 is to make sure color is selected from first colours set
  @Locate(xpath = ".//ul[@class='config__options config__options--color list-reset'][1]/li", on = Platform.WEB)
  private List<ContainerElement> availableShadesList;

  @Locate(xpath = ".//ul[@class='config__options config__options--size list-reset']/li", on = Platform.WEB)
  private List<ContainerElement> availableSizesList;

}

