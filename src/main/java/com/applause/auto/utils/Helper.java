package com.applause.auto.utils;

import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.popups.GlossierBasePopUp;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.HideKeyboardStrategy;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.applause.auto.framework.SdkHelper.getDriver;

@SuppressWarnings("rawtypes")
public class Helper {
  private static final Logger logger = LogManager.getLogger(Helper.class);
  private static Actions actions = new Actions(SdkHelper.getDriver());

  /** Hides the keyboard for android and ios */
  public static void hideKeyboard() {
    ((IOSDriver) SdkHelper.getDriver()).hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
  }

  public static void hoverOverElement(WebElement element){
    actions.moveToElement(element).perform();
  }

  /** Scrolls page to specified element. */
  public static void scrollToElement(WebElement element) {
    getJavascriptExecutor().executeScript("arguments[0].scrollIntoView({block: \"center\",inline: \"center\",behavior: \"smooth\"});", element);
  }

  /** Scrolls to the page top */
  public static void scrollToPageTop() {
    logger.info("Scrolling to page top");
    ((JavascriptExecutor) SdkHelper.getDriver())
        .executeScript("window.scrollTo(0, -document.body.scrollHeight);");
  }

  //TODO move to dedicated helper class for numbers and digits handle
  private static ArrayList<Integer> getDigitValuesFromString(String text) {
    logger.info(String.format("Reading digit values from [%s]", text));
    Pattern p = Pattern.compile("\\d+");
    Matcher m = p.matcher(text);
    ArrayList<Integer> values = new ArrayList<>();
    while (m.find()) {
      values.add(Integer.parseInt(m.group()));
    }
    return values;
  }

  @SneakyThrows
  public static <T extends BasePage> void logicWithPopUpHandle(
          Class <T> popupClass, int maxWaitTimeForPopUp, String message, ComponentMethod component) {
    logger.info(message);
    try{
      component.performMethod(message);
    }
    catch (ElementNotInteractableException e1){
      GlossierBasePopUp popUp = (GlossierBasePopUp) SdkHelper.create(popupClass);
      if (popUp.isDisplayed(maxWaitTimeForPopUp)) {
        logger.info("Closing popup and trying again");
        popUp.close();
      }
      component.performMethod(message);
    }
  }

  @SneakyThrows
  public static <T extends BasePage> T logicWithPopUpHandleAndReturnPO(
          Class <T> popupClass,int maxWaitTimeForPopUp, String message, ComponentMethodWithReturn<T> classToReturn) {
    GlossierBasePopUp popUp = (GlossierBasePopUp) SdkHelper.create(popupClass);
    try{
      if (popUp.isDisplayed(maxWaitTimeForPopUp)) {
        popUp.close();
      }
      logger.info(message);
      return classToReturn.performMethod(message);
    }
    catch (Exception any){
      throw new Exception(any.getMessage());
    }
  }

  public static boolean isElementPresent(BaseElement element, int secTimeout) {
    try {
      SdkHelper.getSyncHelper()
              .wait(Until.uiElement(element).present().setTimeout(Duration.ofSeconds(secTimeout)));
      SdkHelper.getSyncHelper()
              .wait(Until.uiElement(element).visible().setTimeout(Duration.ofSeconds(secTimeout)));
      scrollToElement(element.getWebElement());
      return true;
    } catch (TimeoutException e) {
      logger.info("Element is not present");
      return false;
    }
  }

  public static boolean isElementDisplayed(BaseElement element, int secTimeout) {
    try {
      SdkHelper.getSyncHelper()
              .wait(Until.uiElement(element).visible().setTimeout(Duration.ofSeconds(secTimeout)));
      scrollToElement(element.getWebElement());
      return true;
    } catch (TimeoutException e) {
      logger.info("Element is not present");
      return false;
    }
  }

  public static boolean isDevice() {
    return isTablet() || isPhone();
  }

  public static boolean isTablet(){
    return SdkHelper.getDriverContext().getPlatform().getFriendlyName().endsWith("Tablet");
  }

  public static boolean isPhone(){
    return SdkHelper.getDriverContext().getPlatform().getFriendlyName().endsWith("Phone");
  }

  public static JavascriptExecutor getJavascriptExecutor() {
    return (JavascriptExecutor) getDriver();
  }

  @SneakyThrows
  public static void hideWebElement(WebElement element, boolean informFailure) {
    try {
      getJavascriptExecutor().executeScript("arguments[0].setAttribute('style', 'display:none;')", element);
      TimeUnit.SECONDS.sleep(1);
    }
    catch (Exception any) {
      if(informFailure){
        throw new Exception("Failed to remove webelement from DOM - " + any.getMessage());
      }
      else {
        logger.warn("Failed to remove webelement [ {} ]from DOM - ", any.getMessage());
      }
    }
  }

  public static void hideAllElements(String elementIdentifier){
    logger.warn("Removing all elements with identifier {} might remove also unwanted code !", elementIdentifier);
    String loopAllElementsAndRemove = "var elements = document.%s('%s');\n" +
            "for (var i = 0; i < elements.length; i++) {\n" +
            "    elements[i].setAttribute('style', 'display:none;');\n" +
            "}";
    String getElementsByClassName = "getElementsByClassName";
    String getElementsByName = "getElementsByName";
    String getElementsByTagName = "getElementsByTagName";

    getJavascriptExecutor().executeScript(String.format(loopAllElementsAndRemove, getElementsByClassName, elementIdentifier));
    getJavascriptExecutor().executeScript(String.format(loopAllElementsAndRemove, getElementsByName, elementIdentifier));
    getJavascriptExecutor().executeScript(String.format(loopAllElementsAndRemove, getElementsByTagName, elementIdentifier));
  }

  public static long getPageHeight() {
    return (Long) getJavascriptExecutor().executeScript("return document.body.scrollHeight");
  }

  public static void waitAndClick(BaseElement element) {
    logger.info("Waiting for element to be present");
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).present());
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    logger.info("Scrolling to element");
    scrollToElement(element.getWebElement());
    logger.info("Clicking element [{}]", element.getLocator().getBys().get(0).toString());
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).clickable());
    element.click();
  }

  public static ContainerElement findChild(BaseElement parent, By child){
    try{
      logger.info("From parent {} search child {}", parent, child);
      return parent.getChild(child);
    }
    catch (StaleElementReferenceException stale){
      parent.initialize();
      ContainerElement element =  parent.getChild(child);
      element.initialize();
      return element;
    }
  }

  public static List<WebElement> findChildren(BaseElement parent, By child){
    try{
      logger.info("From parent {} search child {}", parent, child);
      return parent.getWebElement().findElements(child);
    }
    catch (StaleElementReferenceException stale){
      parent.initialize();
      return parent.getWebElement().findElements(child);
    }
  }

  /** Perform click action and return expected class  */
  public static <T extends BaseComponent> T waitAndClick(BaseElement element, Class <T> clazz) {
    waitAndClick(element);
    return SdkHelper.create(clazz);
  }
}
