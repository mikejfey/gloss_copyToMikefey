package com.applause.auto.pageobjects.productpage;

import com.applause.auto.core.GlossierConfig;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.pageobjects.BasePage;
import com.applause.auto.pageobjects.commoncomponents.popups.GlossierBasePopUp;
import com.applause.auto.pageobjects.commoncomponents.popups.YouDeserveItPopUp;
import com.applause.auto.pageobjects.commoncomponents.smallviews.bag.BagView;
import com.applause.auto.pageobjects.productlistpage.chunks.sets.chunks.SetItem;
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
        SdkHelper.getSyncHelper().wait(Until.uiElement(productName).visible());
    }

    public boolean isPageDisplayed(){
        logger.info("Returning if Product Page is displayed");
        return container.isDisplayed();
    }

    public BagView clickAddToBag(){
        step("Add product to bag - %s", getProductName());
        Helper.logicWithPopUpHandle(
                YouDeserveItPopUp.class, 15,
                "Add product to bag", logic -> Helper.waitAndClick(addToBagButton));
        return SdkHelper.create(BagView.class);
    }

    public BagView clickAddSetToBag(){
        step("Clicking on choose set - %s", getProductName());
        Helper.logicWithPopUpHandle(
                YouDeserveItPopUp.class, 15,
                "Choose set", logic -> Helper.waitAndClick(addToBagButton));
        return SdkHelper.create(BagView.class);
    }

    public <T extends GlossierBasePopUp> BagView clickAddSetToBagAndHandlePopUp(Class <T> popupClass){
        step("Clicking on choose set - %s", getProductName());
        Helper.logicWithPopUpHandle(
                YouDeserveItPopUp.class, 15,
                "Choose set", logic -> {
                    Helper.waitAndClick(addToBagButton);
                    GlossierBasePopUp popUp = (GlossierBasePopUp) SdkHelper.create(popupClass);
                    if(popUp.isDisplayed(10)){
                        popUp.close();
                    }
                });
        return SdkHelper.create(BagView.class);
    }

    public String getProductName(){
        logger.info("Collect search result product name");
        return productName.getText().trim();
    }

    public BigDecimal getProductPrice(){
        logger.info("Collect search result product price");
        if(Helper.isElementPresent(productStrikeThroughPrice, 2)){
            return BigDecimal.valueOf(
                    Double.parseDouble(discountedProductPrice.getText()
                            .replace(GlossierConfig.getCurrencySymbol(), "").trim()));
        }
        else{
            return BigDecimal.valueOf(
                    Double.parseDouble(productPrice.getText()
                            .replace(GlossierConfig.getCurrencySymbol(), "").trim()));
        }
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

    public List<String> getAvailableSizesList(){
        logger.info("Collect all available shades");
        ((LazyList<ContainerElement>) availableSizesList).initialize();
        return availableSizesList.stream()
                .map(item -> item.getAttributeValue("data-option-value")
                        .trim()).collect(Collectors.toList());
    }

    public void selectSize(String value){
        step("Select shade %s", value);
        ((LazyList<ContainerElement>) availableSizesList).initialize();
        ContainerElement option = availableSizesList.stream()
                .filter(item -> item.getAttributeValue("data-option-value").equals(value))
                .findFirst().get();
        Helper.logicWithPopUpHandle(
                YouDeserveItPopUp.class, 15,
                "Select size", logic -> Helper.waitAndClick(option));
    }

    public List<String> getAvailableAmountsList(){
        logger.info("Collect all available amounts");
        ((LazyList<ContainerElement>) availableAmountList).initialize();
        return availableAmountList.stream()
                .map(item -> item.getAttributeValue("data-option-value")
                        .trim()).collect(Collectors.toList());
    }

    public void selectAmount(String value){
        step("Select amount %s", value);
        ((LazyList<ContainerElement>) availableAmountList).initialize();
        ContainerElement option = availableAmountList.stream()
                .filter(item -> item.getAttributeValue("data-option-value").equals(value))
                .findFirst().get();
        Helper.logicWithPopUpHandle(
                YouDeserveItPopUp.class, 15,
                "Select amount", logic -> Helper.waitAndClick(option));
    }

    public List<SetItem> getSetItemsList(){
        ((LazyList<SetItem>) setItemsList).initialize();
        SdkHelper.getSyncHelper().wait(Until.allOf(setItemsList).visible());
        return setItemsList;
    }

    @Locate(xpath = "//section[@id='product']", on = Platform.WEB)
    private ContainerElement container;

    @Locate(xpath = "//h1[@class='pv-header__title']", on = Platform.WEB)
    private Text productName;

    @Locate(xpath = "(//span[@id='productPrice']/span[@class='pv-price__original js-price-original'])[1]", on = Platform.WEB)
    private Text productPrice;

    @Locate(xpath = "(//span[@class='js-product-set-atc-price']/span[@class='product-set__atc-price-compare'])[1]", on = Platform.WEB)
    private Text productStrikeThroughPrice;

    @Locate(xpath = "(//span[@class='js-product-set-atc-price']/span[@class='product-set__atc-price'])[1]", on = Platform.WEB)
    private Text discountedProductPrice;

    @Locate(xpath = "//div[@class='config']/div[@data-option-name='Flavor']/ul/li", on = Platform.WEB)
    private List<ContainerElement> availableShadesList;

    @Locate(xpath = "//div[@class='pv-actions']//button[@name='add']", on = Platform.WEB)
    private Button addToBagButton;

    @Locate(xpath = "//div[@class='config']/div[@data-option-name='Size']/ul/li", on = Platform.WEB)
    private List<ContainerElement> availableSizesList;

    @Locate(xpath = "//div[@class='config']/div[@data-option-name='Denominations']/ul/li", on = Platform.WEB)
    private List<ContainerElement> availableAmountList;

    @Locate(xpath = "//div[@id='productSetConfigurable']//ul/li[@class='product-set__item js-product-set-item']", on = Platform.WEB)
    private List<SetItem> setItemsList;
}
