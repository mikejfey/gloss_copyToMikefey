package com.applause.auto.data.values;


import lombok.AllArgsConstructor;

public enum SortByValues {
 PRICE_ASCENDING("price-ascending");

  private final String htmlValue;

  SortByValues(String htmlValue){
    this.htmlValue=htmlValue;
  }

  public String getValue(){
    return this.htmlValue;
  }
}
