package com.applause.auto.pageobjects.productpage;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.popups.YouDeserveItPopUp;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagView;
import com.applause.auto.utils.Helper;

import java.math.BigDecimal;

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

    public BigDecimal getProductPrice(){
        logger.info("Collect search result product price");
        return BigDecimal.valueOf(
                Double.parseDouble(productPrice.getText()
                        .replace(",", ".")
                        .replaceAll("\\D", "").trim()));
    }

    public BagView addToBag(){
        step("Add product to bag - %s", getProductName());
        Helper.logicWithPopUpHandle(
                YouDeserveItPopUp.class, 15,
                "Add product to bag", logic -> Helper.waitAndClick(addToBag));
        return SdkHelper.create(BagView.class);
    }


    @Locate(xpath = "//section[@id='product']", on = Platform.WEB)
    private ContainerElement container;

    @Locate(xpath = "//h1[@class='pv-header__title']", on = Platform.WEB)
    private Text productName;

    @Locate(xpath = "(//span[@id='productPrice']/span[@class='pv-price__original js-price-original'])[1]", on = Platform.WEB)
    private Text productPrice;

    @Locate(xpath = "//div[@class='pv-actions']//button[@name='add']", on = Platform.WEB)
    private Button addToBag;
}
