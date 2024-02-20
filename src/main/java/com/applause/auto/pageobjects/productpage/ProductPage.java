package com.applause.auto.pageobjects.productpage;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjects.BasePage;

import static com.applause.auto.utils.AllureUtils.step;

public class ProductPage extends BasePage {

    @Override
    public void afterInit() {
        step("Waiting for Product Page to be displayed");
        waitForPageToLoad(container, "Product Page", 10);
    }


    @Locate(xpath = "//section[@id='product']", on = Platform.WEB)
    private ContainerElement container;
}
