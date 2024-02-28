package com.applause.auto.test;

import com.applause.auto.pageobjects.commoncomponents.popups.CookiesPopUp;
import com.applause.auto.testng.BaseTest;
import com.applause.auto.utils.Helper;
import com.applause.auto.utils.TestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjects.homepage.HomePage;
import com.applause.auto.utils.ExecutionHelper;

import java.util.Random;

import static com.applause.auto.utils.AllureUtils.step;

@Listeners({TestListener.class})
public abstract class BaseWebTest extends BaseTest {

  protected static final Logger logger = LogManager.getLogger(BaseWebTest.class);
  protected Random random = new Random();

  @BeforeMethod
  public void setup(){
    String sessionId = ((RemoteWebDriver) SdkHelper.getDriver()).getSessionId().toString();
    logger.info("Test execution session id : {}", sessionId);
  }
  @Override
  @AfterMethod(alwaysRun = true)
  public void resetContext() {
    super.resetContext();
  }

  protected HomePage navigateToLandingPage() {
    maximiseDesktopBrowsers();
    String url = ExecutionHelper.getRunningConfiguration().getUrl();
    step("Navigating to Home Page");
    SdkHelper.getDriver().get(url);
    HomePage homePage = SdkHelper.create(HomePage.class);
    handleCookiesPolicy();
    return homePage;
  }

  private void handleCookiesPolicy(){
    step("Accepting cookies");
    CookiesPopUp cookiesPopUp = SdkHelper.create(CookiesPopUp.class);
    if(cookiesPopUp.isDisplayed(15))
    cookiesPopUp.acceptCookies();
  }

  private void maximiseDesktopBrowsers(){
    if (!Helper.isDevice()) {
      SdkHelper.getDriver().manage().window().maximize();
    }
  }
}
