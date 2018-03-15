package de.ahofi.settlersOfCatan.game;

import lombok.Getter;
import lombok.Setter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Game {

  private static Game instance;

  public static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }

  @Getter
  @Setter
  private ObservableList<Player> players = FXCollections.observableArrayList();

}
