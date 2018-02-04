package de.ahofi.view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

import javafx.util.Pair;

@Getter
public enum VertexLocation {

  TOPLEFT(0, Direction.NORTHWEST, Direction.NORTH),

  TOPRIGHT(60, Direction.NORTH, Direction.NORTHEAST),

  MIDDLERIGHT(120, Direction.NORTHEAST, Direction.SOUTHEAST),

  BOTTOMRIGHT(180, Direction.SOUTHEAST, Direction.SOUTH),

  BOTTOMLEFT(240, Direction.SOUTH, Direction.SOUTHWEST),

  MIDDLELEFT(300, Direction.SOUTHWEST, Direction.NORTHWEST);

  static {
    TOPLEFT.opposite = BOTTOMRIGHT;
    MIDDLELEFT.opposite = MIDDLERIGHT;
    BOTTOMLEFT.opposite = TOPRIGHT;
    BOTTOMRIGHT.opposite = TOPLEFT;
    MIDDLERIGHT.opposite = MIDDLELEFT;
    TOPRIGHT.opposite = TOPLEFT;
  }

  public static List<VertexLocation> findLocationDirection(final Direction direction) {
    return Arrays.stream(values())
            .filter(location -> location.getDirection().getKey() == direction || location.getDirection().getValue() == direction)
            .collect(Collectors.toList());
  }

  private VertexLocation opposite;

  private Pair<Direction, Direction> direction;

  private final int angle;

  private VertexLocation(final int angle, final Direction a, final Direction b) {
    this.angle = angle;
    this.direction = new Pair<>(b, a);
  }
}
