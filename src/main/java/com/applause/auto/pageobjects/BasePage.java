package com.applause.auto.pageobjects;


import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.utils.Helper;
import lombok.SneakyThrows;

@Implementation(is = BasePage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = BasePage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = BasePage.class, on = Platform.WEB_MOBILE_PHONE)
public class BasePage extends BaseComponent {

    @SneakyThrows
    protected void waitForPageToLoad(BaseElement pageContainer, String pageName, int maxWaitingTime){
        try{
            Helper.isElementPresent(pageContainer, maxWaitingTime);
        }
        catch (Exception any){
            throw new Exception(
                    String.format("Failed to wait after %s to be displayed : %s",
                            pageName, any.getMessage()));
        }
    }
}
