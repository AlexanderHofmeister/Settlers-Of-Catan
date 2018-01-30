package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.game.Board;
import model.game.BuildingCostsTable;
import model.game.Dice;
import model.game.Tile;

public class Main extends Application {
  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void start(final Stage primaryStage) throws InstantiationException, IllegalAccessException {
    final BorderPane root = new BorderPane();
    final Scene scene = new Scene(root);
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
    for (final Tile tile : board.getTiles()) {
      root.getChildren().add(tile);
      if (tile.getChip() != null) {
        final Text label = new Text(tile.getMiddleLeft().getX() + 50, tile.getMiddleLeft().getY(), tile.getChip().getLabel());
        label.toFront();
        root.getChildren().add(label);
      }
    }
    root.setRight(new BuildingCostsTable());
  }
}
