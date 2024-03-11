package com.applause.auto.pageobjects.commoncomponents.popups;

import com.applause.auto.utils.Helper;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

@Implementation(is = LiveChat.class, on = Platform.WEB)
@Implementation(is = LiveChat.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = LiveChat.class, on = Platform.WEB_MOBILE_PHONE)
public class LiveChat extends GlossierBasePopUp {

  private final String liveChatMessageClassName = "acquire_w_ui_status_alert";
  private final String liveChatIconClassName = "acquire_lw_ui_status_alert";
  protected String jsRemoveScript = "document.getElementsByClassName('%s')[0].remove()";

  /** Checks the livechat component covering both parts of it: message & icon
   * Returns true if any or both are displayed
   * */
  public boolean isDisplayed(int maxWaitingTime) {
    boolean isMessageDisplayed = Helper.isElementDisplayed(liveMessage, maxWaitingTime);
    boolean isIconDisplayed = Helper.isElementPresent(liveChatIcon, maxWaitingTime);
    return isMessageDisplayed || isIconDisplayed;
  }

  /**
   * Close Live Chat Message popup
   */
  private void closeLiveChatMessagePopUp() {
    try {
      Helper.getJavascriptExecutor().executeScript(String.format(jsRemoveScript, liveChatMessageClassName));
    }
    catch (Exception any) {
      logger.warn("Livechat message wasn't displayed to close it");
    }
  }

  /**
   * Close Live Chat icon
   */
  private void closeLiveChatIcon() {
    try{
      Helper.getJavascriptExecutor().executeScript(String.format(jsRemoveScript, liveChatIconClassName));
    }
    catch (Exception any) {
      logger.warn("Livechat icon wasn't displayed to close it ");
    }
  }

  /**
   * Close All Live Chat popup components
   */
  public void close() {
    closeLiveChatMessagePopUp();
    closeLiveChatIcon();
  }

  @Locate(xpath = "//iframe[contains(@class,'status_invitation thread_status')]", on = Platform.WEB)
  protected ContainerElement liveMessage;

  @Locate(xpath = "//iframe[contains(@class,'aio-launcher-frame acquire')]", on = Platform.WEB)
  protected ContainerElement liveChatIcon;
}

