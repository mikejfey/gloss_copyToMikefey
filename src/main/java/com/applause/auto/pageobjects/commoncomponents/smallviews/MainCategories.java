package com.applause.auto.pageobjects.commoncomponents.smallviews;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.types.Category;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.productlistpage.CategoryPage;
import com.applause.auto.utils.Helper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = MainCategories.class, on = Platform.WEB_DESKTOP)
@Implementation(is = MainCategoriesDevice.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = MainCategoriesDevice.class, on = Platform.WEB_MOBILE_PHONE)
public class MainCategories extends BasePage {

  protected static final Logger logger = LogManager.getLogger(MainCategories.class);

  @Override
  public void afterInit() {}

  @SneakyThrows
  public CategoryPage openCategory(Category category){
    step("Open products category %s", category.toString());
    switch (category) {
      case SKINCARE:
        Helper.waitAndClick(skinCareCategory);
        break;
      case MAKEUP: Helper.waitAndClick(makeupCategory);
        break;
      case BALMS: Helper.waitAndClick(balmsCategory);
        break;
      case BODY: Helper.waitAndClick(bodyCategory);
        break;
      case FRAGRANCE: Helper.waitAndClick(fragranceCategory);
        break;
      case GLOSSIER_GOODS: Helper.waitAndClick(glossierGoodsCategory);
        break;
      case SETS: Helper.waitAndClick(setsCategory);
        break;
      case SHOP_ALL:
        Helper.hoverOverElement(skinCareCategory.getWebElement());
        Helper.waitAndClick(shopAllCategory);
        break;
      default: throw new Exception("Please use existing defined category");
    }
    return SdkHelper.create(CategoryPage.class, category);
  }

  @Locate(xpath = "//a[@data-testid='globalNavLink' and contains(text(), 'Shop All')]", on = Platform.WEB)
  private Button shopAllCategory;

  @Locate(xpath = "//ul[@data-testid='topNavLinkList']//a[@data-nav-item='skincare']", on = Platform.WEB)
  private Button skinCareCategory;

  @Locate(xpath = "//ul[@data-testid='topNavLinkList']//a[@data-nav-item='makeup']", on = Platform.WEB)
  private Button makeupCategory;

  @Locate(xpath = "//ul[@data-testid='topNavLinkList']//a[contains(text(), 'Balms')]", on = Platform.WEB)
  private Button balmsCategory;

  @Locate(xpath = "//ul[@data-testid='topNavLinkList']//a[@data-nav-item='body']", on = Platform.WEB)
  private Button bodyCategory;

  @Locate(xpath = "//ul[@data-testid='topNavLinkList']//a[@data-nav-item='fragrance']", on = Platform.WEB)
  private Button fragranceCategory;

  @Locate(xpath = "//ul[@data-testid='topNavLinkList']//a[@data-nav-item='glossier-goods']", on = Platform.WEB)
  private Button glossierGoodsCategory;

  @Locate(xpath = "//ul[@data-testid='topNavLinkList']//a[@data-nav-item='sets']", on = Platform.WEB)
  private Button setsCategory;
}

class MainCategoriesDevice extends MainCategories{

  @SneakyThrows
  public CategoryPage openCategory(Category category){
    By allProductsSubCategory = By.xpath(".//following-sibling::ul/li/a");
    step("Open products category %s", category.toString());
    switch (category) {
      case SKINCARE:
        Helper.waitAndClick(skinCareCategory);
        Helper.findChildren(skinCareCategory, allProductsSubCategory).get(0).click();
        break;
      case MAKEUP: Helper.waitAndClick(makeupCategory);
        Helper.findChildren(makeupCategory, allProductsSubCategory).get(0).click();
        break;
      case BALMS:
        Helper.waitAndClick(balmsCategory);
        break;
      case BODY:
        Helper.waitAndClick(bodyCategory);
        Helper.findChildren(bodyCategory, allProductsSubCategory).get(0).click();
        break;
      case FRAGRANCE: Helper.waitAndClick(fragranceCategory);
        Helper.findChildren(fragranceCategory, allProductsSubCategory).get(0).click();
        break;
      case GLOSSIER_GOODS: Helper.waitAndClick(glossierGoodsCategory);
        Helper.findChildren(glossierGoodsCategory, allProductsSubCategory).get(0).click();
        break;
      case SETS: Helper.waitAndClick(setsCategory);
        Helper.findChildren(setsCategory, allProductsSubCategory).get(0).click();
        break;
      case SHOP_ALL:
        Helper.waitAndClick(shopAllCategory);
        break;
      default: throw new Exception("Please use existing defined category");
    }
    return SdkHelper.create(CategoryPage.class, category);
  }

  @Locate(xpath = "//a[@data-testid='globalNavLink' and contains(text(), 'Shop All')]", on = Platform.WEB)
  private Button shopAllCategory;

  @Locate(xpath = "//button[@data-testid='menuNavLink' and contains(text(), 'Skincare')]", on = Platform.WEB)
  private Button skinCareCategory;

  @Locate(xpath = "//button[@data-testid='menuNavLink' and contains(text(), 'Makeup')]", on = Platform.WEB)
  private Button makeupCategory;

  @Locate(xpath = "//a[@data-testid='menuNavLink' and contains(text(), 'Balms')]", on = Platform.WEB)
  private Button balmsCategory;

  @Locate(xpath = "//button[@data-testid='menuNavLink' and contains(text(), 'Body')]", on = Platform.WEB)
  private Button bodyCategory;

  @Locate(xpath = "//button[@data-testid='menuNavLink' and contains(text(), 'Fragrance')]", on = Platform.WEB)
  private Button fragranceCategory;

  @Locate(xpath = "//button[@data-testid='menuNavLink' and contains(text(), 'Glossier Goods')]", on = Platform.WEB)
  private Button glossierGoodsCategory;

  @Locate(xpath = "//button[@data-testid='menuNavLink' and contains(text(), 'Sets')]", on = Platform.WEB)
  private Button setsCategory;

}

