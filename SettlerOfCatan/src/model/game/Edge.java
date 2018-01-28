package model.game;

import lombok.Getter;

import model.Road;

@Getter
public class Edge {

  private Vertex a;
  private Vertex b;
  private Road road;

}
