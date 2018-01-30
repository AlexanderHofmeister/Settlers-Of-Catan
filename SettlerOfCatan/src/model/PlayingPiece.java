package model;

import java.util.List;

import lombok.Getter;

public abstract class PlayingPiece {

  @Getter
  private final List<Cost> costs;

  public PlayingPiece() {
    this.costs = buildCosts();
  }

  public abstract List<Cost> buildCosts();

}
