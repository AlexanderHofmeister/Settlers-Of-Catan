package de.ahofi.game.model;

import java.util.List;

import lombok.Getter;

public class BuildingCost {

  @Getter
  private final List<Cost> costs;

  public BuildingCost(final List<Cost> costs) {
    this.costs = costs;
  }

}
