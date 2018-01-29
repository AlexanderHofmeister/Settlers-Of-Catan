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

  WOOL(4, true, Color.LIGHTGREEN),

  GRAIN(4, true, Color.YELLOW),

  ORE(3, true, Color.LIGHTGRAY),

  LUMBER(4, true, Color.DARKGREEN),

  BRICK(3, true, Color.FIREBRICK),

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

  private final boolean ressource;

  private final Color color;

}
