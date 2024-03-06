package com.applause.auto.pageobjects.categorypage;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.values.Category;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.categorypage.chunks.ProductResultSmallView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.applause.auto.utils.AllureUtils.step;

@Implementation(is = CategoryPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = CategoryPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = CategoryPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CategoryPage extends BasePage {

  protected static final Logger logger = LogManager.getLogger(CategoryPage.class);

  private Category name;

  //TODO implement sub-categories for each specific page

  public CategoryPage(Category name){
    this.name = name;
    logger.info("Creating Category Page for {}", name);
  }

  @Override
  public void afterInit() {
    step("Waiting for Category Page to be displayed");
    waitForPageToLoad(productsContainer, "Category Page", 10);
    logger.info("Navigate again to category URL with popup query");
    String currentUrl = SdkHelper.getDriver().getCurrentUrl();
    SdkHelper.getDriver().get(currentUrl + "?supressklaviyo=true");
    waitForPageToLoad(productsContainer, "Category Page", 10);
  }

  public List<ProductResultSmallView> getProductsResultList(){
    ((LazyList<ProductResultSmallView>) productsList).initialize();
    SdkHelper.getSyncHelper().wait(Until.allOf(productsList).visible());
    return productsList;
  }

  @Locate(xpath = "//ul[@class='collection__list js-collection-products list-reset container']", on = Platform.WEB)
  private ContainerElement productsContainer;

  @Locate(xpath = "//ul[@class='collection__list js-collection-products list-reset container']/li", on = Platform.WEB)
  private List<ProductResultSmallView> productsList;
}

