package model.game;

import java.util.ArrayList;
import java.util.List;

import model.NumberType;
import model.RessourceType;

public class Hex {

  private final List<Vertex> vertices;

  private RessourceType type;

  private NumberType number;

  public Hex() {
    this.vertices = new ArrayList<>(6);
  }

}
