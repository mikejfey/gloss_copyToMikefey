package com.applause.auto.pageobjects.homepage;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjects.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;


@Implementation(is = EnvironmentLoginPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = EnvironmentLoginPage.class, on = Platform.WEB_MOBILE_PHONE)
@Implementation(is = EnvironmentLoginPage.class, on = Platform.WEB_MOBILE_TABLET)
public class EnvironmentLoginPage extends BasePage {

  protected static final Logger logger = LogManager.getLogger(EnvironmentLoginPage.class);

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(passwordField).visible());
  }

  public void fillPassword(String password){
    step("Fill prelive environment password");
    passwordField.sendKeys(password);
  }

  public void clickEnter(){
    step("Click on Enter button");
    enterButton.click();
  }

  @Locate(xpath = "//div[@class='pwd__login-form-fields']//input[@id='password']", on = Platform.WEB)
  protected TextBox passwordField;

  @Locate(xpath = "//input[@name='commit']", on = Platform.WEB)
  protected Button enterButton;

}

