package view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.game.Board;

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

    final Board board = new Board();
    final Set<Point> points = board.getGrid().keySet();
    final Map<Point, Hexagon> createdFields = new LinkedHashMap<>();

    for (final Point point : points) {
      Hexagon hexagon = null;
      if (point.getX() == 0.0 && point.getY() == 0.0) {
        hexagon = Hexagon.createVerticalHexagon(new Point(100, 150), new Point(150, 150));
      } else if (point.getX() == 0 && point.getY() > 0.0 && point.getY() <= Board.GRID_SIZE / 2) {
        hexagon = Hexagon.createTopRight(createdFields.get(new Point(0, point.getY() - 1)));
      } else if (point.getX() > 0) {
        hexagon = Hexagon.createVerticalHexagon(createdFields.get(new Point(point.getX() - 1, point.getY())));
      } else if (point.getX() == 0 && point.getY() > Board.GRID_SIZE / 2) {
        hexagon = Hexagon.createBottomRight(createdFields.get(new Point(0, point.getY() - 1)));
      } else {
        System.out.println("wrong: " + point);
      }

      if (hexagon != null) {
        hexagon.setFill(Color.LEMONCHIFFON);
        hexagon.setStroke(Color.BLACK);
        createdFields.put(point, hexagon);
        root.getChildren().add(hexagon);
      }
    }

  }
}
