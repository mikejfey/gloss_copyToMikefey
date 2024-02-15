package com.applause.auto.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Country {
  USA("United States of America", "USA");

  @Getter
  String text;
  @Getter
  String value;
}
