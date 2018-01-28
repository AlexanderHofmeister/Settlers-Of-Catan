package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {

  public static List<Integer> chips = new ArrayList<>(Arrays.asList(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12));

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
