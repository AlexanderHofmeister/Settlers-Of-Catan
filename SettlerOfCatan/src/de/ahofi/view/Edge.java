package de.ahofi.view;

import de.ahofi.game.model.Road;

import lombok.Getter;
import lombok.Setter;

import javafx.util.Pair;

public class Edge {

  @Getter
  @Setter
  private Road road;

  @Setter
  @Getter
  private Pair<Point, Point> points;

}
