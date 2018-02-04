package de.ahofi.game.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Cost {

  private static Cost cost(final ResourceType resourceType, final int amount) {
    return new Cost(resourceType, amount);
  }

  public static List<Cost> of(final ResourceType type1, final int amount1, final ResourceType type2, final int amount2) {
    return Arrays.asList(cost(type1, amount1), cost(type2, amount2));
  }

  public static List<Cost> of(final ResourceType type1, final int amount1, final ResourceType type2, final int amount2,
          final ResourceType type3, final int amount3) {
    return Arrays.asList(cost(type1, amount1), cost(type2, amount2), cost(type3, amount3));
  }

  public static List<Cost> of(final ResourceType type1, final int amount1, final ResourceType type2, final int amount2,
          final ResourceType type3, final int amount3, final ResourceType type4, final int amount4) {
    return Arrays.asList(cost(type1, amount1), cost(type2, amount2), cost(type3, amount3), cost(type4, amount4));
  }

  private final ResourceType resourceType;

  private final int amount;

}
