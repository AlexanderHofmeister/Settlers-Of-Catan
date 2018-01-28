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

  @Getter
  private int number;

  @Getter
  private final Rectangle diceShape = new Rectangle(50, 50);
  private final Label numberLabel;

  public Dice() {
    this.number = getRandomDiceNumber();
    this.diceShape.setFill(Color.WHITE);
    this.diceShape.setStroke(Color.BLACK);
    this.diceShape.setStrokeWidth(2);
    getChildren().add(this.diceShape);
    this.numberLabel = new Label(String.valueOf(this.number));
    getChildren().add(this.numberLabel);
  }

  private int getRandomDiceNumber() {
    final Random random = new Random();
    return random.ints(MIN_NUMBER, MAX_NUMBER + 1).findFirst().getAsInt();
  }

  public void roll() {
    this.number = getRandomDiceNumber();
    this.numberLabel.setText(String.valueOf(this.number));
  }
}
