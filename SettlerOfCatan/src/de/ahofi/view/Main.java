package de.ahofi.view;

import de.ahofi.game.model.Dice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Pair;

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

    for (final Tile hexagon : board.getTiles()) {
      root.getChildren().add(hexagon);
    }

    for (final Vertex vertex : board.getVertices()) {
      final Circle e = new Circle(vertex.getLocation().getX(), vertex.getLocation().getY(), 10);
      e.toFront();
      root.getChildren().add(e);
    }

    for (final Edge edge : board.getEdges()) {
      final Pair<Point, Point> points = edge.getPoints();
      final Point a = points.getKey();
      final Point b = points.getValue();
      final Line edgeLine = new Line(a.getX(), a.getY(), b.getX(), b.getY());
      edgeLine.setStrokeWidth(1);
      edgeLine.setStroke(Color.RED);
      edgeLine.toFront();
      edgeLine.toFront();
      root.getChildren().add(edgeLine);
    }

    root.setRight(new BuildingCostsTable());
  }
}
