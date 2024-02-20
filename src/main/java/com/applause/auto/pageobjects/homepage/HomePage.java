package com.applause.auto.pageobjects.homepage;

import com.applause.auto.data.values.Category;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.elements.*;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.categorypage.CategoryPage;
import com.applause.auto.pageobjects.homepage.chunks.LoginPage;
import com.applause.auto.pageobjects.homepage.chunks.search.Search;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagView;
import com.applause.auto.pageobjects.homepage.chunks.MainCategories;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = HomePage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = HomePage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = HomePage.class, on = Platform.WEB_MOBILE_PHONE)
public class HomePage extends BasePage {

  protected static final Logger logger = LogManager.getLogger(HomePage.class);

  private MainCategories mainCategories;

  @Override
  public void afterInit() {
    step("Waiting for Home Page to be displayed");
    waitForPageToLoad(container, "Home Page", 10);
    mainCategories = SdkHelper.create(MainCategories.class);
  }

  public CategoryPage openCategory(Category category){
    return mainCategories.openCategory(category);
  }

  public BagView openBag(){
    step("Open bag");
    SdkHelper.getSyncHelper().wait(Until.uiElement(bagButton).clickable());
    bagButton.click();
    return SdkHelper.create(BagView.class);
  }

  public Search openSearch(){
    step("Open search");
    Helper.waitAndClick(searchButton);
    return SdkHelper.create(Search.class);
  }

  public LoginPage openLogin(){
    step("Open login");
    Helper.waitAndClick(loginButton);
    return SdkHelper.create(LoginPage.class);
  }

  @Locate(xpath = "//div[@class='nm-wrapper-content']", on = Platform.WEB)
  private ContainerElement container;

  @Locate(xpath = "//button[@id='bagBtn']", on = Platform.WEB)
  private Button bagButton;

  @Locate(xpath = "//button[@id='headerSearchBtn']", on = Platform.WEB)
  private Button searchButton;

  @Locate(xpath = "//a[contains(text(), 'Log in')]//parent::li", on = Platform.WEB)
  private Button loginButton;
}

