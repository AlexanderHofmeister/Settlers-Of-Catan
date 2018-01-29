package model.game;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import javafx.scene.shape.Polygon;
import model.Chip;
import model.TileType;
import view.Point;

@Getter
public class Tile extends Polygon {

  private static final double DEFAULT_WIDTH_HEX = 50.0;

  public static Tile createBottomRight(final Tile tile) {

    final Point topLeft = tile.getMiddleRight();
    final Point middleLeft = tile.getBottomRight();
    final Point bottomLeft = new Point(topLeft.getX(), topLeft.getY() + DEFAULT_WIDTH_HEX * 2);

    final Point topRight = new Point(topLeft.getX() + DEFAULT_WIDTH_HEX, topLeft.getY());
    final Point bottomRight = new Point(topRight.getX(), topRight.getY() + DEFAULT_WIDTH_HEX * 2);
    final Point middleRight = new Point(topRight.getX() + DEFAULT_WIDTH_HEX / 2, topRight.getY() + DEFAULT_WIDTH_HEX);

    return new Tile(topLeft, middleLeft, bottomLeft, bottomRight, middleRight, topRight, tile.getChip());

  }

  public static Tile createTopRight(final Tile tile) {

    final Point middleLeft = tile.getTopRight();
    final Point bottomLeft = tile.getMiddleRight();
    final Point topLeft = new Point(middleLeft.getX() + DEFAULT_WIDTH_HEX / 2, middleLeft.getY() - DEFAULT_WIDTH_HEX);

    final Point topRight = new Point(topLeft.getX() + DEFAULT_WIDTH_HEX, topLeft.getY());

    final Point bottomRight = new Point(topRight.getX(), topRight.getY() + DEFAULT_WIDTH_HEX * 2);

    final Point middleRight = new Point(topRight.getX() + DEFAULT_WIDTH_HEX / 2, topRight.getY() + DEFAULT_WIDTH_HEX);

    return new Tile(topLeft, middleLeft, bottomLeft, bottomRight, middleRight, topRight, tile.getChip());

  }

  public static Tile createVerticalTile(final Point oldBottomLeft, final Point oldBottomRight, final Chip chip) {
    final Point topLeft = oldBottomLeft;
    final Point topRight = oldBottomRight;

    final Point middleLeft = new Point(topLeft.getX() - DEFAULT_WIDTH_HEX / 2, topLeft.getY() + DEFAULT_WIDTH_HEX);
    final Point bottomLeft = new Point(topLeft.getX(), topLeft.getY() + DEFAULT_WIDTH_HEX * 2);

    final Point bottomRight = new Point(topRight.getX(), topRight.getY() + DEFAULT_WIDTH_HEX * 2);
    final Point middleRight = new Point(topRight.getX() + DEFAULT_WIDTH_HEX / 2, topRight.getY() + DEFAULT_WIDTH_HEX);

    return new Tile(topLeft, middleLeft, bottomLeft, bottomRight, middleRight, topRight, chip);

  }

  public static Tile createVerticalTile(final Tile tile) {
    return createVerticalTile(tile.getBottomLeft(), tile.getBottomRight(), tile.getChip());

  }

  @Setter
  private TileType type;

  @Setter
  @Getter
  private Chip chip;

  private final List<Vertex> vertices = new ArrayList<>(6);

  private final Point topLeft;
  private final Point middleLeft;
  private final Point bottomLeft;
  private final Point bottomRight;
  private final Point middleRight;
  private final Point topRight;

  private Tile(final Point topLeft, final Point middleLeft, final Point bottomLeft, final Point bottomRight, final Point middleRight,
          final Point topRight, final Chip chip) {
    this.topLeft = topLeft;
    this.middleLeft = middleLeft;
    this.bottomLeft = bottomLeft;
    this.bottomRight = bottomRight;
    this.middleRight = middleRight;
    this.topRight = topRight;

    getPoints().addAll(topLeft.getX(), topLeft.getY(), middleLeft.getX(), middleLeft.getY(), bottomLeft.getX(), bottomLeft.getY(),
            bottomRight.getX(), bottomRight.getY(), middleRight.getX(), middleRight.getY(), topRight.getX(), topRight.getY(),
            topLeft.getX(), topLeft.getY());
  }

}
