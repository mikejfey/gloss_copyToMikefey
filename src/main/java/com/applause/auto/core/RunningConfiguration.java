package com.applause.auto.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Currency;

@AllArgsConstructor
@Getter
public enum RunningConfiguration {
  LIVE("https://www.glossier.com/?supressklaviyo=true"),
  PRE_LIVE("https://add_me_once_provided.com");

  private String url;
}
