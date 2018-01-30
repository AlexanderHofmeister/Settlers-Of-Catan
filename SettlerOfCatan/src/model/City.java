package model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public class City extends PlayingPiece {

  @Override
  public Map<ResourceType, Integer> buildCosts() {
    return Maps.newHashMap(ImmutableMap.of(ResourceType.GRAIN, 1, ResourceType.ORE, 1, ResourceType.WOOL, 1));
  }

}
