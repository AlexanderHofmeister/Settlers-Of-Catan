package view;

import lombok.Getter;

import javafx.scene.shape.Polygon;

@Getter
public class Hexagon extends Polygon {

  private static final double DEFAULT_WIDTH_HEX = 50.0;

  public static Hexagon createBottomRight(final Hexagon hexagon) {

    final Point topLeft = hexagon.getMiddleRight();
    final Point middleLeft = hexagon.getBottomRight();
    final Point bottomLeft = new Point(topLeft.getX(), topLeft.getY() + DEFAULT_WIDTH_HEX * 2);

    final Point topRight = new Point(topLeft.getX() + DEFAULT_WIDTH_HEX, topLeft.getY());
    final Point bottomRight = new Point(topRight.getX(), topRight.getY() + DEFAULT_WIDTH_HEX * 2);
    final Point middleRight = new Point(topRight.getX() + DEFAULT_WIDTH_HEX / 2, topRight.getY() + DEFAULT_WIDTH_HEX);

    return new Hexagon(topLeft, middleLeft, bottomLeft, bottomRight, middleRight, topRight);

  }

  public static Hexagon createTopRight(final Hexagon hexagon) {

    final Point middleLeft = hexagon.getTopRight();
    final Point bottomLeft = hexagon.getMiddleRight();
    final Point topLeft = new Point(middleLeft.getX() + DEFAULT_WIDTH_HEX / 2, middleLeft.getY() - DEFAULT_WIDTH_HEX);

    final Point topRight = new Point(topLeft.getX() + DEFAULT_WIDTH_HEX, topLeft.getY());

    final Point bottomRight = new Point(topRight.getX(), topRight.getY() + DEFAULT_WIDTH_HEX * 2);

    final Point middleRight = new Point(topRight.getX() + DEFAULT_WIDTH_HEX / 2, topRight.getY() + DEFAULT_WIDTH_HEX);

    return new Hexagon(topLeft, middleLeft, bottomLeft, bottomRight, middleRight, topRight);

  }

  public static Hexagon createVerticalHexagon(final Hexagon hexagon) {
    return createVerticalHexagon(hexagon.getBottomLeft(), hexagon.getBottomRight());

  }

  public static Hexagon createVerticalHexagon(final Point oldBottomLeft, final Point oldBottomRight) {
    final Point topLeft = oldBottomLeft;
    final Point topRight = oldBottomRight;

    final Point middleLeft = new Point(topLeft.getX() - DEFAULT_WIDTH_HEX / 2, topLeft.getY() + DEFAULT_WIDTH_HEX);
    final Point bottomLeft = new Point(topLeft.getX(), topLeft.getY() + DEFAULT_WIDTH_HEX * 2);

    final Point bottomRight = new Point(topRight.getX(), topRight.getY() + DEFAULT_WIDTH_HEX * 2);
    final Point middleRight = new Point(topRight.getX() + DEFAULT_WIDTH_HEX / 2, topRight.getY() + DEFAULT_WIDTH_HEX);

    return new Hexagon(topLeft, middleLeft, bottomLeft, bottomRight, middleRight, topRight);

  }

  private final Point topLeft;
  private final Point middleLeft;
  private final Point bottomLeft;
  private final Point bottomRight;
  private final Point middleRight;
  private final Point topRight;

  private Hexagon(final Point topLeft, final Point middleLeft, final Point bottomLeft, final Point bottomRight, final Point middleRight,
          final Point topRight) {
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
