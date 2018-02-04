package de.ahofi.view;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Point {

  protected final double x;
  protected final double y;

  public Point addTo(final Point point) {
    return new Point(this.x + point.getX(), this.y + point.getY());
  }

  public final Double[] getPoint() {
    return new Double[] { this.x, this.y };
  }

  @Override
  public String toString() {
    return this.x + " / " + this.y;
  }

}
