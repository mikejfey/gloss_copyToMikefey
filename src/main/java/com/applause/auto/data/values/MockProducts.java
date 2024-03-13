package com.applause.auto.data.values;


public enum MockProducts {
    UNVARIATED_SET("The Super Pack"),
    VARIATED_SET("The Makeup Set"),
    MULTI_SET("Lash Slick Duo"),
    FIXED_VARIATED_SET("Milky Jelly Cleanser + Futuredew"),
    AMOUNT_VARIATED_PRODUCT("Digital Gift Card"),
    SHADE_VARIATED_PRODUCT("Balm Dotcom"),
    BODY_UNVARIATED_PRODUCT("Hand Cream");

  private final String productName;

  MockProducts(String productName){
    this.productName=productName;
  }

  public String getProductName(){
    return this.productName;
  }
}
