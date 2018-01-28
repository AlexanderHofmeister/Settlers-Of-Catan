package model.game;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

import javafx.scene.paint.Color;
import view.Point;

@Getter
public class Board {

  public static final int GRID_SIZE = 5;

  private final Map<Point, Tile> grid = new LinkedHashMap<>();

  public Board() {
    for (int column = 0; column < GRID_SIZE; column++) {

      for (int row = 0; row < GRID_SIZE; row++) {

        if (column == 0 && row >= GRID_SIZE - 2) {
          continue;
        } else if (column == 1 && row >= GRID_SIZE - 1) {
          continue;
        } else if (column == GRID_SIZE - 2 && row >= GRID_SIZE - 1) {
          continue;
        } else if (column == GRID_SIZE - 1 && row >= GRID_SIZE - 2) {
          continue;
        }

        Tile tile = null;
        final Point point = new Point(row, column);
        if (point.getX() == 0.0 && point.getY() == 0.0) {
          tile = Tile.createVerticalTile(new Point(100, 150), new Point(150, 150));
        } else if (point.getX() == 0 && point.getY() > 0.0 && point.getY() <= Board.GRID_SIZE / 2) {
          tile = Tile.createTopRight(this.grid.get(new Point(0, point.getY() - 1)));
        } else if (point.getX() > 0) {
          tile = Tile.createVerticalTile(this.grid.get(new Point(point.getX() - 1, point.getY())));
        } else if (point.getX() == 0 && point.getY() > Board.GRID_SIZE / 2) {
          tile = Tile.createBottomRight(this.grid.get(new Point(0, point.getY() - 1)));
        }

        tile.setFill(Color.LEMONCHIFFON);
        tile.setStroke(Color.BLACK);

        this.grid.put(point, tile);
      }
    }
  }

}
