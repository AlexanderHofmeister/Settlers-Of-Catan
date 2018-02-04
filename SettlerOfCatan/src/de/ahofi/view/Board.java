package de.ahofi.view;

import de.ahofi.game.model.Chip;
import de.ahofi.game.model.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

import javafx.util.Pair;

public class Board {

  public static double APP_WIDTH = 900;
  public static double APP_HEIGHT = 900;
  public static double APP_CENTER_X = APP_WIDTH / 2;
  public static double APP_CENTER_Y = APP_HEIGHT / 2;

  public static final double HEX_RADIUS = 50;

  public static Point getHexCenter(final CubeCoordinates coords) {
    final double x = HEX_RADIUS * 3 / 2 * coords.getX();
    final double y = HEX_RADIUS * Math.sqrt(3) * (coords.getZ() + (double) coords.getX() / 2);

    return new Point(x + APP_CENTER_X, y + APP_CENTER_Y);
  }

  public List<TileType> ressourceTiles = TileType.buildRessourceTilesForGame();
  public List<Chip> chips = Chip.buildChipsForGame();

  @Getter
  private final List<Tile> tiles = new ArrayList<>();
  @Getter
  private final List<Vertex> vertices = new ArrayList<>();
  @Getter
  private final List<Edge> edges = new ArrayList<>();

  public Board() {

    final Tile start = new Tile();
    start.initTile(getRandomTileType(), getRandomTileType().isResource() ? getRandomChip() : null);
    this.tiles.add(start);
    buildVertices(start);

    for (final Direction dir : Direction.values()) {

      final TileType tileType = getRandomTileType();
      final Chip chip = tileType.isResource() ? getRandomChip() : null;

      final Tile transformed = start.getTransformed(dir);
      transformed.initTile(tileType, chip);
      buildVertices(transformed);
      buildEdges(transformed);
      this.tiles.add(transformed);
    }

  }

  private void buildEdges(final Tile hexagon) {

    for (final Direction direction : Direction.values()) {

      if (hexagon.getEdgeByDirection(direction) == null) {

        // wenn noch keine Kante da, dann hole Nachbarshexagon
        // wenn nachbar da, dann setze Ecke auf deine Ecke
        // und mach weiter
        // wenn kein nachbar da, dann bau neue Ecke und füge sie hinzu

        final Direction oppDir = direction.getOpposite();
        final Tile neighbor = getNeighborTile(hexagon, direction);
        if (neighbor != null) {
          final Edge edgeOfNeighbor = neighbor.getEdgeByDirection(oppDir);
          if (edgeOfNeighbor != null) {
            hexagon.addEdge(direction, edgeOfNeighbor);
            continue;
          }
        }
        final Edge edge = new Edge();
        final List<VertexLocation> vertexLocations = VertexLocation.findLocationDirection(direction);

        edge.setPoints(new Pair<>(hexagon.getVertexByLocation(vertexLocations.get(0)).getLocation(),
                hexagon.getVertexByLocation(vertexLocations.get(1)).getLocation()));

        hexagon.addEdge(direction, edge);
        this.edges.add(edge);

      }
    }

  }

  private void buildVertices(final Tile tile) {

    for (final VertexLocation location : VertexLocation.values()) {

      if (tile.getVertexByLocation(location) == null) {

        final Pair<Direction, Direction> directionsOfOppositeLocation = location.getDirection();
        final Tile neighborA = getNeighborTile(tile, directionsOfOppositeLocation.getKey());

        if (neighborA != null) {
          final VertexLocation opposite = location.getOpposite();
          final Vertex opp = neighborA.getVertexByLocation(opposite);
          if (opp != null) {
            tile.addVertex(location, opp);
            continue;
          }
        }

        final Tile neighborB = getNeighborTile(tile, directionsOfOppositeLocation.getValue());

        if (neighborB != null) {
          final VertexLocation opposite = location.getOpposite();
          final Vertex opp = neighborB.getVertexByLocation(opposite);
          if (opp != null) {
            tile.addVertex(location, opp);
            continue;
          }
        }

        final Vertex newVertex = new Vertex();

        final Point point = tile.calcVertex(location.getAngle());
        newVertex.setLocation(point);
        tile.getPoints().add(point.getX());
        tile.getPoints().add(point.getY());
        tile.addVertex(location, newVertex);
        this.vertices.add(newVertex);

      }

    }

  }

  private Tile getNeighborTile(final Tile tile, final Direction direction) {
    return this.tiles.stream().filter(possibleNeighbor -> possibleNeighbor.getIndex().equals(direction.apply(tile.getIndex()).getIndex()))
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
}
