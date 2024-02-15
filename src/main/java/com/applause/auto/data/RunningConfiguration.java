package com.applause.auto.data;

import java.time.ZoneId;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RunningConfiguration {
  DE_LIVE("https://www.glossier.com/"),
  DE_PRE_LIVE("https://add_me_once_provided.com");

  private String url;

}
