package model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public class DevelopmentCard extends PlayingPiece {

  @Override
  public Map<ResourceType, Integer> buildCosts() {
    return Maps.newHashMap(ImmutableMap.of(ResourceType.GRAIN, 2, ResourceType.ORE, 3));

  }

}
