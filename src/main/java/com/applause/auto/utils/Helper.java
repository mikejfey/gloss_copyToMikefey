package com.applause.auto.utils;

import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjects.commoncomponents.LiveChat;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.HideKeyboardStrategy;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.applause.auto.framework.SdkHelper.getDriver;

@SuppressWarnings("rawtypes")
public class Helper {
  private static final Logger logger = LogManager.getLogger(Helper.class);

  /** Hides the keyboard for android and ios */
  public static void hideKeyboard() {
    ((IOSDriver) SdkHelper.getDriver()).hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
  }

  /** Scrolls page to specified element. */
  public static void scrollToElement(WebElement element) {
    getJavascriptExecutor().executeScript("arguments[0].scrollIntoView(true);", element);

  }

  /** Scrolls to the page top */
  public static void scrollToPageTop() {
    logger.info("Scrolling to page top");
    ((JavascriptExecutor) SdkHelper.getDriver())
        .executeScript("window.scrollTo(0, -document.body.scrollHeight);");
  }

  public static double parsePrice(String price) {
    logger.info(String.format("Parsing price of: [%s]", price));
    Number number = null;
    try {
      number =
          DecimalFormat.getInstance(Locale.GERMANY).parse(price.substring(0, price.length() - 1));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    logger.info(String.format("Parsed price is: [%s]", number));
    return number.doubleValue();
  }

  public static int getPowerKw(String text) {
    return getDigitValuesFromString(text).get(1);
  }

  public static int getPowerPs(String text) {
    return getDigitValuesFromString(text).get(2);
  }

  public static int getEngineDisplacementNumber(String text) {
    return getDigitValuesFromString(text).get(0);
  }

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

  /** Scroll element to middle of the screen. */
  public static void scrollElementToMiddleOfView(WebElement element) {
    // scrolls element to view...
    getJavascriptExecutor().executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", element);
    getJavascriptExecutor().executeScript("javascript:window.scrollBy(0,250)");
  }

  @SneakyThrows
  public static <T> void handleLiveChatPopUp(String message, ComponentMethod component) {
    LiveChat livePersonChunk = SdkHelper.create(LiveChat.class);
    if (livePersonChunk.isDisplayed()) {
      livePersonChunk.closeLiveChat();
    }
    logger.info(message);
    component.performMethod(message);
  }

  public static <T> T handleLiveChatPopUpWithReturn(
          String message, ComponentMethodWithReturn<T> component) {
    LiveChat livePersonChunk = SdkHelper.create(LiveChat.class);
    try{
      if (livePersonChunk.isDisplayed()) {
        livePersonChunk.closeLiveChat();
      }
      logger.info(message);
      return component.performMethod(message);
    }
    catch (Exception e){
      return null;
    }
  }

  public static void closeLiveChatIfVisible(){
    LiveChat livePersonChunk = SdkHelper.create(LiveChat.class);
    if(livePersonChunk.isDisplayed())
      livePersonChunk.closeLiveChat();
  }

  public static boolean isElementPresent(BaseElement element, int secTimeout) {
    try {
      SdkHelper.getSyncHelper()
              .wait(Until.uiElement(element).present().setTimeout(Duration.ofSeconds(secTimeout)));
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
      scrollElementToMiddleOfView(element.getWebElement());
      return true;
    } catch (TimeoutException e) {
      logger.info("Element is not present");
      return false;
    }
  }

  public static boolean isDevice() {
    boolean isTablet = SdkHelper.getEnvironmentHelper().isTablet();
    boolean isPhone = SdkHelper.getEnvironmentHelper().isPhone();
    logger.info("Device is phone [{}] tablet [{}]", isPhone, isTablet);
    return isPhone || isTablet;
  }

  public static boolean isTablet() {
    return SdkHelper.getEnvironmentHelper().isTablet();
  }

  public static JavascriptExecutor getJavascriptExecutor() {
    return (JavascriptExecutor) getDriver();
  }

  /**
   * Change display style attribute to hide the element
   *
   * <p>* @param elementXpath
   */
  @SneakyThrows
  public static void hideWebElement(WebElement element) {
    try{
      getJavascriptExecutor().executeScript("arguments[0].setAttribute('style', 'display:none;')", element);
    }
    catch (Exception any){
     throw new Exception("Failed to remove webelement from DOM - " + any.getMessage());
    }
  }

  public static long getPageHeight() {
    return (Long) getJavascriptExecutor().executeScript("return document.body.scrollHeight");
  }

  /** Perform selenium native click and handle ElementClickInterceptedException with JavaScript click */
  public static void nativeOrJsClick(BaseElement element){
    try{
      element.click();
    }
    catch (ElementClickInterceptedException elementClickInterceptedException){
      SdkHelper.getBrowserControl().jsClick(element);
    }
  }

  public static void waitAndClick(BaseElement element) {
    logger.info("Waiting for element to be present");
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).present());
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    logger.info("Scrolling to element");
    if(isDevice()) {
      scrollToElement(element.getWebElement());
    }
    else {
      element.scrollToElement();
    }
    closeLiveChatIfVisible();
    logger.info("Clicking element [{}]", element.getLocator().getBys().get(0).toString());
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).clickable());
    element.click();
  }

  /** Perform click action and return expected class  */
  public static <T extends BaseComponent> T waitAndClick(BaseElement element, Class <T> clazz) {
    waitAndClick(element);
    return SdkHelper.create(clazz);
  }

  public static void selectOptionFromDropDown(SelectList element, String option) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(element).present());
    element.scrollToElement();
    logger.info("Selecting [{}] option", element);
    element.select(option);
    SdkHelper.getSyncHelper()
            .wait(Until.uiElement(element)
                    .meetsCustomCondition(list -> !list.getSelectedOption().getText().isEmpty()));
  }

  public static void fillInput(TextBox element, String value) {
    Helper.scrollElementToMiddleOfView(element.getWebElement());
    element.sendKeys(value);
    logger.info("Waiting for field to be populated");
    SdkHelper.getSyncHelper().waitUntil(string -> element.getCurrentText().equals(value));
  }

  public static void waitForPageToLoad(){
    WebDriverWait wait = new WebDriverWait(SdkHelper.getDriver(), Duration.ofSeconds(30));
    logger.info("Waiting for page to load");
    wait.until(webDriver -> "complete".equals(((JavascriptExecutor) webDriver)
            .executeScript("return document.readyState")));
  }
}
