package model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public class Settlement extends PlayingPiece {

  @Override
  public Map<ResourceType, Integer> buildCosts() {
    return Maps.newHashMap(ImmutableMap.of(ResourceType.LUMBER, 1, ResourceType.BRICK, 1, ResourceType.GRAIN, 1, ResourceType.WOOL, 1));
  }

}
