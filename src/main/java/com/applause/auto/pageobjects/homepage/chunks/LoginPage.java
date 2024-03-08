package com.applause.auto.pageobjects.homepage.chunks;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjects.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = LoginPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = LoginPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = LoginPage.class, on = Platform.WEB_MOBILE_PHONE)
public class LoginPage extends BasePage {

  protected static final Logger logger = LogManager.getLogger(LoginPage.class);

  @Override
  public void afterInit() {
    step("Waiting for Login Page to be displayed");
    waitForPageToLoad(container, "Login Page", 10);
  }

  @Locate(xpath = "//div[@id='acLoginForm']", on = Platform.WEB)
  private ContainerElement container;

  @Locate(xpath = "//input[@id='customerEmail']", on = Platform.WEB)
  private TextBox emailField;

  @Locate(xpath = "//input[@id='customerPassword']", on = Platform.WEB)
  private TextBox passwordField;

  @Locate(xpath = "//button[@id='loginSubmitButton']", on = Platform.WEB)
  private Button signInButton;

  @Locate(xpath = "//a[contains(text(),'Create an account')]", on = Platform.WEB)
  private Button createAccountButton;

  @Locate(xpath = "//a[@id='recoverPassword']", on = Platform.WEB)
  private Button forgotPasswordButton;

}

