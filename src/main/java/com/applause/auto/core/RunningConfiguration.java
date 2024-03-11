package com.applause.auto.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Currency;

@AllArgsConstructor
@Getter
public enum RunningConfiguration {
  LIVE("https://www.glossier.com/?supressklaviyo=true"),
  PRE_LIVE("https://shop-dev.glossier.com/");

  private String url;
}
