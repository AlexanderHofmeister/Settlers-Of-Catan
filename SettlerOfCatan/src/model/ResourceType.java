package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javafx.scene.paint.Color;

@RequiredArgsConstructor
public enum ResourceType {

  WOOL(Color.LIGHTGREEN),

  GRAIN(Color.YELLOW),

  ORE(Color.LIGHTGRAY),

  LUMBER(Color.DARKGREEN),

  BRICK(Color.FIREBRICK);

  @Getter
  private final Color color;

}
