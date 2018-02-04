package de.ahofi.game.model;

import java.util.List;

public class Development extends PlayingPiece {

  @Override
  public List<Cost> buildCosts() {
    return Cost.of(ResourceType.GRAIN, 1, ResourceType.WOOL, 1, ResourceType.ORE, 1);
  }

}
