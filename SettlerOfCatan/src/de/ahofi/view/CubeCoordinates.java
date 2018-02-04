package de.ahofi.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CubeCoordinates {

  private int x;
  private int y;
  private int z;

  public CubeCoordinates() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }

  public CubeCoordinates addX() {
    this.x--;
    return this;
  }

  public CubeCoordinates addY() {
    this.y++;
    return this;
  }

  public CubeCoordinates addZ() {
    this.z++;
    return this;
  }

  public CubeCoordinates copy() {
    return new CubeCoordinates(this.x, this.y, this.z);
  }

  public CubeCoordinates subX() {
    this.x++;
    return this;
  }

  public CubeCoordinates subY() {
    this.y++;
    return this;
  }

  public CubeCoordinates subZ() {
    this.z++;
    return this;
  }

  @Override
  public String toString() {
    return this.x + " / " + this.y + " / " + this.z;
  }
}
