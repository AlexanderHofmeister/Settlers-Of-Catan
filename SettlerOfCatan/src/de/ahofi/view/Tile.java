package de.ahofi.view;

import de.ahofi.game.model.Chip;
import de.ahofi.game.model.TileType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

@Getter
@Setter
public class Tile extends Polygon {

  private CubeCoordinates index;

  private Point center;

  private final Map<Direction, Edge> edges = new LinkedHashMap<>(6);

  @Getter
  private final Map<VertexLocation, Vertex> vertices = new HashMap<>(6);

  @Setter
  private TileType type;

  @Setter
  @Getter
  private Chip chip;

  public Tile() {
    this(new CubeCoordinates());
  }

  public Tile(final CubeCoordinates coords) {
    this.index = coords;
    init();
  }

  public void addEdge(final Direction direction, final Edge edge) {
    this.edges.put(direction, edge);
  }

  public void addVertex(final VertexLocation location, final Vertex vertex) {
    this.vertices.put(location, vertex);
  }

  public Point calcVertex(final double degree) {
    return new Point(this.center.getX() + Board.HEX_RADIUS * Math.cos(Math.toRadians(degree)),
            this.center.getY() + Board.HEX_RADIUS * Math.sin(Math.toRadians(degree)));
  }

  public Edge getEdgeByDirection(final Direction direction) {
    return this.edges.get(direction);
  }

  public Tile getTransformed(final Direction direction) {
    return direction.apply(this.getIndex());
  }

  public Vertex getVertexByLocation(final VertexLocation location) {
    return this.vertices.get(location);
  }

  private void init() {
    this.center = Board.getHexCenter(this.index);

    this.setStroke(Color.DARKGRAY);
    this.setStrokeWidth(5);

  }

  public void initTile(final TileType tileType, final Chip chip) {
    this.chip = chip;
    this.type = tileType;
    setFill(this.type.getColor());
  }

}
