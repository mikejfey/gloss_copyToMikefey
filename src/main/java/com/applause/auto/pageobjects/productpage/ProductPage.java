package com.applause.auto.pageobjects.productpage;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.popups.YouDeserveItPopUp;
import com.applause.auto.pageobjects.homepage.chunks.bag.BagView;
import com.applause.auto.utils.Helper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.applause.auto.utils.AllureUtils.step;

public class ProductPage extends BasePage {

    @Override
    public void afterInit() {
        step("Waiting for Product Page to be displayed");
        waitForPageToLoad(container, "Product Page", 10);
        logger.info("Navigate again to PDP ORL with popup query");
        String currentUrl = SdkHelper.getDriver().getCurrentUrl();
        SdkHelper.getDriver().get(currentUrl + "?supressklaviyo=true");
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

    public List<String> getAvailableShadesList(){
        logger.info("Collect all available shades");
        ((LazyList<ContainerElement>) availableShadesList).initialize();
        return availableShadesList.stream()
                .map(item -> item.getAttributeValue("data-option-value")
                        .trim()).collect(Collectors.toList());
    }

    public void selectShade(String value){
        step("Select shade %s", value);
        ((LazyList<ContainerElement>) availableShadesList).initialize();
        ContainerElement option = availableShadesList.stream()
                .filter(item -> item.getAttributeValue("data-option-value").equals(value))
                .findFirst().get();
        Helper.logicWithPopUpHandle(
                YouDeserveItPopUp.class, 15,
                "Select shade", logic -> Helper.waitAndClick(option));
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

    @Locate(xpath = "//div[@class='config']/div[@data-option-name='Flavor']/ul/li", on = Platform.WEB)
    private List<ContainerElement> availableShadesList;

    @Locate(xpath = "//div[@class='pv-actions']//button[@name='add']", on = Platform.WEB)
    private Button addToBag;
}
