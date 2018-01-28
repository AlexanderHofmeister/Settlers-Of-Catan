package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.game.Board;
import model.game.Dice;
import model.game.Tile;

public class Main extends Application {
  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void start(final Stage primaryStage) {
    final BorderPane root = new BorderPane();
    final Scene scene = new Scene(root);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setTitle("Settler of Catan");
    final Label mouseCoordinates = new Label("- / -");
    scene.setOnMouseMoved(event -> {
      mouseCoordinates.setText(event.getSceneX() + " / " + event.getSceneY());
    });
    root.setTop(mouseCoordinates);
    final HBox diceBox = new HBox(10);
    final Dice diceLeft = new Dice();
    final Dice diceRight = new Dice();
    diceBox.getChildren().add(diceLeft);
    diceBox.getChildren().add(diceRight);
    final javafx.scene.control.Button rollDice = new Button("Roll dice");
    rollDice.setOnAction(action -> {
      diceLeft.roll();
      diceRight.roll();
    });
    diceBox.getChildren().add(rollDice);
    root.setBottom(diceBox);

    final Board board = new Board();
    for (final Tile Tile : board.getGrid().values()) {
      root.getChildren().add(Tile);
    }

  }
}
