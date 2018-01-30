package model;

import java.util.Map;

import lombok.Getter;

public class BuildingCost {

  @Getter
  private final Map<ResourceType, Integer> price;

  public BuildingCost(final Map<ResourceType, Integer> price) {
    this.price = price;
  }

}
