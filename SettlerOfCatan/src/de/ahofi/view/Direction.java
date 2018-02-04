package de.ahofi.view;

import lombok.Getter;

public enum Direction {
  NORTH {
    @Override
    public Tile apply(final CubeCoordinates base) {
      return new Tile(base.copy().addY().subZ());
    }
  },
  NORTHEAST {
    @Override
    public Tile apply(final CubeCoordinates base) {
      return new Tile(base.copy().addX().subZ());
    }
  },
  SOUTHEAST {
    @Override
    public Tile apply(final CubeCoordinates base) {
      return new Tile(base.copy().addX().subY());
    }
  },
  SOUTH {
    @Override
    public Tile apply(final CubeCoordinates base) {
      return new Tile(base.copy().subY().addZ());
    }
  },
  SOUTHWEST {
    @Override
    public Tile apply(final CubeCoordinates base) {
      return new Tile(base.copy().subX().addZ());
    }
  },
  NORTHWEST {
    @Override
    public Tile apply(final CubeCoordinates base) {
      return new Tile(base.copy().subX().addY());
    }
  };

  static {
    NORTH.opposite = SOUTH;
    NORTHEAST.opposite = SOUTHWEST;
    SOUTHEAST.opposite = NORTHWEST;
    SOUTH.opposite = NORTH;
    SOUTHWEST.opposite = NORTHEAST;
    NORTHWEST.opposite = SOUTHEAST;
  }

  @Getter
  private Direction opposite;

  public abstract Tile apply(CubeCoordinates base);
}
