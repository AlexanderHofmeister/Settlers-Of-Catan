package model.game;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

import view.Point;

@Getter
public class Board {

  public static final int GRID_SIZE = 5;

  private final Map<Point, Hex> grid = new LinkedHashMap<>();

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
        this.grid.put(new Point(row, column), new Hex());
      }
    }
  }

}
