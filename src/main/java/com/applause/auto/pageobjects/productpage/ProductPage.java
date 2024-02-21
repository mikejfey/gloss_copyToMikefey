package com.applause.auto.pageobjects.productpage;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjects.BasePage;

import static com.applause.auto.utils.AllureUtils.step;

public class ProductPage extends BasePage {

    @Override
    public void afterInit() {
        step("Waiting for Product Page to be displayed");
        waitForPageToLoad(container, "Product Page", 10);
    }

    public boolean isPageDisplayed(){
        logger.info("Returning if Product Page is displayed");
        return container.isDisplayed();
    }

    public String getProductName(){
        logger.info("Collect search result product name");
        return productName.getText().trim();
    }

    public String getProductPrice(){
        logger.info("Collect search result product price");
        return productPrice.getText().trim();
    }


    @Locate(xpath = "//section[@id='product']", on = Platform.WEB)
    private ContainerElement container;

    @Locate(xpath = "//h1[@class='pv-header__title']", on = Platform.WEB)
    private Text productName;

    @Locate(xpath = "(//span[@id='productPrice']/span[@class='pv-price__original js-price-original'])[1]", on = Platform.WEB)
    private Text productPrice;
}
