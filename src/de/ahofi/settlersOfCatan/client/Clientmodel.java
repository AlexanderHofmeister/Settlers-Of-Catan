package de.ahofi.settlersOfCatan.client;

import de.ahofi.settlersOfCatan.client.net.Client;
import de.ahofi.settlersOfCatan.game.Game;

import lombok.Getter;
import lombok.Setter;

public class Clientmodel {

  private static Clientmodel instance;

  public static Clientmodel getInstance() {
    if (instance == null) {
      instance = new Clientmodel();
    }
    return instance;
  }

  @Getter
  @Setter
  private Client client;

  @Getter
  private final Game game;

  public Clientmodel() {
    this.game = Game.getInstance();
  }

}
