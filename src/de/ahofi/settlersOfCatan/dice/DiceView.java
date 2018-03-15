package de.ahofi.settlersOfCatan.dice;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DiceView extends HBox {

  private static DiceView instance;

  public static DiceView getInstance() {
    if (instance == null) {
      instance = new DiceView();
    }
    return instance;
  }

  private final Label leftLabel = new Label();

  private final Label rightLabel = new Label("1");

  public DiceView() {
    setSpacing(5);
    final StackPane leftPane = new StackPane();
    final Rectangle leftBox = new Rectangle(50, 50);
    leftBox.setFill(Color.WHITE);
    leftBox.setStroke(Color.BLACK);
    leftBox.setStrokeWidth(2);
    leftPane.getChildren().add(leftBox);
    leftPane.getChildren().add(this.leftLabel);

    final StackPane rightPane = new StackPane();

    final Rectangle rightBox = new Rectangle(50, 50);
    rightBox.setFill(Color.WHITE);
    rightBox.setStroke(Color.BLACK);
    rightBox.setStrokeWidth(2);
    rightPane.getChildren().add(rightBox);
    rightPane.getChildren().add(this.rightLabel);

    getChildren().add(leftPane);
    getChildren().add(rightPane);
  }

  public void setDiceValues(final int left, final int right) {
    Platform.runLater(() -> {
      this.leftLabel.setText(String.valueOf(left));
      this.rightLabel.setText(String.valueOf(right));
    });
  }

}
