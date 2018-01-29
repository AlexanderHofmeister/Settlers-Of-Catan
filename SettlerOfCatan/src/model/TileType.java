package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javafx.scene.paint.Color;

@RequiredArgsConstructor
@Getter
public enum TileType {

  PASTURE(4, true, Color.LIGHTGREEN),

  FIELDS(4, true, Color.YELLOW),

  MOUNTAINS(3, true, Color.LIGHTGRAY),

  FOREST(4, true, Color.DARKGREEN),

  HILLS(3, true, Color.FIREBRICK),

  DESERT(1, false, new Color(1.0f, 0.99039216f, 0.7039216f, 1));

  public static List<TileType> buildRessourceTilesForGame() {
    final List<TileType> chips = new ArrayList<>();
    for (final TileType chip : values()) {
      IntStream.range(0, chip.getCount()).forEach(c -> {
        chips.add(chip);
      });
    }

    return chips;
  }

  private final int count;

  private final boolean resource;

  private final Color color;

}
