package com.applause.auto.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RunningConfiguration {
  LIVE("https://www.glossier.com/"),
  PRE_LIVE("https://add_me_once_provided.com");

  private String url;

}
