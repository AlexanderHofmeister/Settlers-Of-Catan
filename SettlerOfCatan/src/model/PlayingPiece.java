package model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public abstract class PlayingPiece {

  @Getter
  private Map<ResourceType, Integer> costs = new HashMap<>();

  public PlayingPiece() {
    this.costs = buildCosts();
  }

  public abstract Map<ResourceType, Integer> buildCosts();

}
