package de.ahofi.game.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Bank {

  private final static int MAX_RESSOURCE_COUNT = 19;

  private final Map<TileType, Integer> resources;

  public Bank() {
    this.resources = new HashMap<>();
    Arrays.stream(TileType.values()).filter(TileType::isResource).forEach(resource -> this.resources.put(resource, MAX_RESSOURCE_COUNT));
  }

  @Override
  public String toString() {
    return this.resources.toString();
  }

}
