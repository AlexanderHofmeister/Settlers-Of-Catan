package view;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Point {

  private final double x;
  private final double y;

  public final Double[] getPoint() {
    return new Double[] { this.x, this.y };
  }

  @Override
  public String toString() {
    return this.x + " / " + this.y;
  }

}
