package com.applause.auto.data.values;


public enum MockProducts {
    UNVARIATED_SET("The Super Pack"),
    FIXED_VARIATED_SET("Milky Jelly Cleanser + Futuredew"),
    AMOUNT_VARIATED_PRODUCT("Digital Gift Card"),
    BODY_UNVARIATED_PRODUCT("Hand Cream");

  private final String productName;

  MockProducts(String productName){
    this.productName=productName;
  }

  public String getProductName(){
    return this.productName;
  }
}
