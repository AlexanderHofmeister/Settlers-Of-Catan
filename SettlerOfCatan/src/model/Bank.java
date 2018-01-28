package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Bank {

  private final int MAX_RESSOURCE_COUNT = 19;

  private final Map<RessourceType, Integer> ressources;

  public Bank() {
    this.ressources = new HashMap<>();
    Arrays.stream(RessourceType.values()).forEach(ressouce -> this.ressources.put(ressouce, this.MAX_RESSOURCE_COUNT));
  }

  @Override
  public String toString() {

    return this.ressources.toString();

  }

}
