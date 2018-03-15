package de.ahofi.settlersOfCatan.dice;

import java.util.Random;

public class Dice {

  private static final int MIN_NUMBER = 1;
  private static final int MAX_NUMBER = 6;

  public int roll() {
    return new Random().ints(MIN_NUMBER, MAX_NUMBER + 1).findFirst().getAsInt();
  }
}
