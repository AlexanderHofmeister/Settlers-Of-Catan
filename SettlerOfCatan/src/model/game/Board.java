package model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

import javafx.scene.paint.Color;
import model.Chip;
import model.Direction;
import model.TileType;
import view.Point;

@Getter
public class Board {

  public static final int GRID_SIZE = 5;

  private final List<Tile> tiles = new ArrayList<>();

  private final List<Edge> edges = new ArrayList<>();

  public List<Chip> chips = Chip.buildChipsForGame();

  public List<TileType> ressourceTiles = TileType.buildRessourceTilesForGame();

  public Board() {
    for (int column = 0; column < GRID_SIZE; column++) {

      for (int row = 0; row < GRID_SIZE; row++) {

        if (column == 0 && row >= GRID_SIZE - 2) {
          continue;
        } else if (column == 1 && row >= GRID_SIZE - 1) {
          continue;
        } else if (column == GRID_SIZE - 2 && row >= GRID_SIZE - 1) {
          continue;
        } else if (column == GRID_SIZE - 1 && row >= GRID_SIZE - 2) {
          continue;
        }

        Tile tile = null;
        final Point point = new Point(row, column);
        final TileType tileType = getRandomTileType();
        final Chip chip = tileType.isResource() ? getRandomChip() : null;
        if (point.getX() == 0.0 && point.getY() == 0.0) {
          tile = Tile.createVerticalTile(new Point(100, 150), new Point(150, 150), chip);
        } else if (point.getX() == 0 && point.getY() > 0.0 && point.getY() <= Board.GRID_SIZE / 2) {
          tile = Tile.createTopRight(getTileByPoint(new Point(0, point.getY() - 1)));
        } else if (point.getX() > 0) {
          tile = Tile.createVerticalTile(getTileByPoint(new Point(point.getX() - 1, point.getY())));
        } else if (point.getX() == 0 && point.getY() > Board.GRID_SIZE / 2) {
          tile = Tile.createBottomRight(getTileByPoint(new Point(0, point.getY() - 1)));
        }

        tile.setType(tileType);
        tile.setFill(tile.getType().getColor());
        tile.setStrokeWidth(3);
        tile.setStroke(Color.LEMONCHIFFON);
        tile.setChip(chip);

        tile.setIndex(point);

        this.tiles.add(tile);
        buildEdges(tile);
      }
    }
  }

  private void buildEdges(final Tile tile) {

    for (final Direction direction : Direction.values()) {

      if (tile.getEdgeByDirection(direction) == null) {

        // wenn noch keine Kante da, dann hole Nachbarshexagon
        // wenn nachbar da, dann setze Ecke auf deine Ecke
        // und mach weiter
        // wenn kein nachbar da, dann bau neue Ecke und füge sie hinzu

        final Direction oppDir = direction.getOpposite();
        final Tile neighbor = getNeighborTile(tile, direction);
        if (neighbor != null) {
          final Edge edgeOfNeighbor = neighbor.getEdgeByDirection(oppDir);
          if (edgeOfNeighbor != null) {
            tile.addEdge(direction, edgeOfNeighbor);
            continue;
          }
        }
        final Edge edge = new Edge();
        tile.addEdge(direction, edge);
        this.edges.add(edge);

      }
    }

  }

  private Tile getNeighborTile(final Tile tile, final Direction direction) {
    return this.tiles.stream()
            .filter(possibleNeighbor -> possibleNeighbor.getIndex().equals(tile.getIndex().addTo(direction.getIndexOfDirection())))
            .findFirst().orElse(null);
  }

  private Chip getRandomChip() {
    if (this.chips.size() == 0) {
      return null;
    }
    final int randomChipIndex = new Random().ints(0, this.chips.size()).findFirst().getAsInt();
    final Chip chip = this.chips.get(randomChipIndex);
    this.chips.remove(randomChipIndex);
    return chip;
  }

  private TileType getRandomTileType() {
    if (this.ressourceTiles.size() == 0) {
      return null;
    }
    final int randomressourceTiletypeIndex = new Random().ints(0, this.ressourceTiles.size()).findFirst().getAsInt();
    final TileType tileType = this.ressourceTiles.get(randomressourceTiletypeIndex);
    this.ressourceTiles.remove(randomressourceTiletypeIndex);
    return tileType;
  }

  private Tile getTileByPoint(final Point index) {
    return this.tiles.stream().filter(tile -> tile.getIndex().equals(index)).findFirst().orElse(null);
  }

}
