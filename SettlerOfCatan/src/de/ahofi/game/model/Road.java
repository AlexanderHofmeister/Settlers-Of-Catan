package de.ahofi.game.model;

import java.util.List;

public class Road extends PlayingPiece {

  @Override
  public List<Cost> buildCosts() {
    return Cost.of(ResourceType.LUMBER, 1, ResourceType.BRICK, 1);
  }

}
