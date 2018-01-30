package model;

import java.util.List;

public class City extends PlayingPiece {

  @Override
  public List<Cost> buildCosts() {
    return Cost.of(ResourceType.GRAIN, 2, ResourceType.ORE, 3);
  }

}
