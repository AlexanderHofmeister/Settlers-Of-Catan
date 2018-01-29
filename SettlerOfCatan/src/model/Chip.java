package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Chip {

  TWO("2", 1),

  THREE("3", 2),

  FOUR("4", 2),

  FIVE("5", 2),

  SIX("6", 2),

  EIGHT("8", 2),

  NINE("9", 2),

  TEN("10", 2),

  ELEVEN("11", 2),

  TWELVE("12", 1);

  public static List<Chip> buildChipsForGame() {
    final List<Chip> chips = new ArrayList<>();
    for (final Chip chip : values()) {
      IntStream.range(0, chip.getCount()).forEach(c -> {
        chips.add(chip);
      });
    }

    return chips;
  }

  private final String label;

  private final int count;

}
