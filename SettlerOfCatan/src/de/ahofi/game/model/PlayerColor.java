package de.ahofi.game.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javafx.scene.paint.Color;

@RequiredArgsConstructor
public enum PlayerColor {

  BLUE(Color.BLUE),

  WHITE(Color.WHITE),

  ORANGE(Color.ORANGE),

  RED(Color.RED);

  @Getter
  private final Color color;

}
