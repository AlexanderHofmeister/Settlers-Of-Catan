package de.ahofi.game;

import java.io.Serializable;

import javafx.scene.paint.Color;

public class PlayerColor implements Serializable {

  private static final long serialVersionUID = 1L;

  private final double red;
  private final double green;
  private final double blue;
  private final double alpha;

  public PlayerColor(final Color color) {
    this.red = color.getRed();
    this.green = color.getGreen();
    this.blue = color.getBlue();
    this.alpha = color.getOpacity();
  }

  public PlayerColor(final double red, final double green, final double blue, final double alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  public Color toFXColor() {
    return new Color(this.red, this.green, this.blue, this.alpha);
  }
}
