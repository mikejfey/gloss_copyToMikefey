package com.applause.auto.pageobjects.homepage;

import com.applause.auto.data.types.Category;
import com.applause.auto.pageobjectmodel.elements.*;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.productlistpage.CategoryPage;
import com.applause.auto.pageobjects.commoncomponents.popups.YouDeserveItPopUp;
import com.applause.auto.pageobjects.homepage.chunks.LoginPage;
import com.applause.auto.pageobjects.commoncomponents.smallviews.search.Search;
import com.applause.auto.pageobjects.commoncomponents.smallviews.bag.BagView;
import com.applause.auto.pageobjects.commoncomponents.smallviews.MainCategories;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = HomePage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = HomePagePhone.class, on = Platform.WEB_MOBILE_PHONE)
@Implementation(is = HomePageTablet.class, on = Platform.WEB_MOBILE_TABLET)
public class HomePage extends BasePage {

  protected static final Logger logger = LogManager.getLogger(HomePage.class);

  protected MainCategories mainCategories;

  @Override
  public void afterInit() {
    if(!Helper.isDevice()){
      waitForPageToLoad(container, "Home Page", 10);
      mainCategories = SdkHelper.create(MainCategories.class);
    }
  }

  public CategoryPage openCategory(Category category){
    return mainCategories.openCategory(category);
  }

  public BagView openBag(){
    step("Open bag");
    Helper.logicWithPopUpHandle(
            YouDeserveItPopUp.class, 15,
            "Open bag", logic -> Helper.waitAndClick(bagButton));
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
  protected ContainerElement container;

  @Locate(xpath = "//button[@id='bagBtn']", on = Platform.WEB)
  protected Button bagButton;

  @Locate(xpath = "//button[@id='headerSearchBtn']", on = Platform.WEB)
  protected Button searchButton;

  @Locate(xpath = "//a[contains(text(), 'Log in')]//parent::li", on = Platform.WEB)
  protected Button loginButton;
}

class HomePagePhone extends HomePage{

  protected static final Logger logger = LogManager.getLogger(HomePagePhone.class);


  public CategoryPage openCategory(Category category){
    logger.info("[Device] -> Clicking on Menu button for devices");
    menuButton.click();
    mainCategories = SdkHelper.create(MainCategories.class);
    return mainCategories.openCategory(category);
  }

  @Locate(xpath = "//li[@class='header__navigation-item hide-desktop']/button", on = Platform.WEB_MOBILE)
  protected Button menuButton;
}

class HomePageTablet extends HomePagePhone{

}

