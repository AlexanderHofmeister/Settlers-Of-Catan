package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NumberType {

  TWO("2"),

  THREE("3"),

  FOUR("4"),

  FIVE("5"),

  SIX("5"),

  EIGHT("5"),

  NINE("5"),

  TEN("5"),

  ELEVEN("5"),

  TWELVE("5");

  private final String label;
}
