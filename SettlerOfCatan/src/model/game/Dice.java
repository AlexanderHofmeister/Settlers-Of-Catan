package model.game;

import java.util.Random;

import lombok.Getter;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Dice extends StackPane {

  private static final int MIN_NUMBER = 1;
  private static final int MAX_NUMBER = 6;

  private static int getRandomDiceNumber() {
    return new Random().ints(MIN_NUMBER, MAX_NUMBER + 1).findFirst().getAsInt();
  }

  @Getter
  private int number;
  @Getter
  private final Rectangle diceShape = new Rectangle(50, 50);

  private final Label numberLabel;

  public Dice() {
    this.diceShape.setFill(Color.WHITE);
    this.diceShape.setStroke(Color.BLACK);
    this.diceShape.setStrokeWidth(2);
    this.numberLabel = new Label();
    getChildren().add(this.diceShape);
    getChildren().add(this.numberLabel);
  }

  public void roll() {
    this.number = getRandomDiceNumber();
    this.numberLabel.setText(String.valueOf(this.number));
  }
}
