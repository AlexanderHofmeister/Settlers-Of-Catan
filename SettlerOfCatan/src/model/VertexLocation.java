package model;

import lombok.Getter;

import javafx.util.Pair;

@Getter
public enum VertexLocation {

  TOPLEFT(Direction.NORTHWEST, Direction.NORTH),

  TOPRIGHT(Direction.NORTH, Direction.NORTHEAST),

  MIDDLERIGHT(Direction.NORTHEAST, Direction.SOUTHEAST),

  BOTTOMRIGHT(Direction.SOUTHEAST, Direction.SOUTH),

  BOTTOMLEFT(Direction.SOUTH, Direction.SOUTWEST),

  MIDDLELEFT(Direction.SOUTWEST, Direction.NORTHWEST);

  static {
    TOPLEFT.opposite = BOTTOMRIGHT;
    MIDDLELEFT.opposite = MIDDLERIGHT;
    BOTTOMLEFT.opposite = TOPRIGHT;
    BOTTOMRIGHT.opposite = TOPLEFT;
    MIDDLERIGHT.opposite = MIDDLELEFT;
    TOPRIGHT.opposite = TOPLEFT;
  }

  private VertexLocation opposite;

  private Pair<Direction, Direction> direction;

  private VertexLocation(final Direction a, final Direction b) {
    this.direction = new Pair<>(b, a);
  }
}
