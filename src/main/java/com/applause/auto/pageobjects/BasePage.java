package com.applause.auto.pageobjects;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;

@Implementation(is = BasePage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = BasePage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = BasePage.class, on = Platform.WEB_MOBILE_PHONE)
public class BasePage extends BaseComponent {
}
