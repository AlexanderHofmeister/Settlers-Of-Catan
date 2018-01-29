package model;

import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Player {

  private final String name;
  private final PlayerColor color;
  private final Map<ResourceType, Integer> resourceCards;

}
