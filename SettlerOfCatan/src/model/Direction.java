package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import view.Point;

@Getter
@RequiredArgsConstructor
public enum Direction {

  NORTH(-1, 0),

  NORTHEAST(-1, 1),

  SOUTHEAST(0, 1),

  SOUTH(1, 0),

  SOUTWEST(0, -1),

  NORTHWEST(-1, -1);

  static {
    NORTH.opposite = SOUTH;
    NORTHEAST.opposite = SOUTWEST;
    SOUTHEAST.opposite = NORTHWEST;
    SOUTH.opposite = NORTH;
    SOUTWEST.opposite = NORTHEAST;
    NORTHWEST.opposite = SOUTHEAST;
  }

  private Direction opposite;

  private Point indexOfDirection;

  private Direction(final int x, final int y) {
    this.indexOfDirection = new Point(x, y);
  }

}
