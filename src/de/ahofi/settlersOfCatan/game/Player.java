package de.ahofi.settlersOfCatan.game;

import de.ahofi.game.PlayerColor;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import lombok.Getter;

public class Player implements Externalizable {

  @Getter
  private String name;

  @Getter
  private PlayerColor color;

  public Player() {
  }

  public Player(final String name) {
    this.name = name;
    this.color = new PlayerColor(Math.random(), Math.random(), Math.random(), 1);
  }

  @Override
  public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
    this.name = in.readLine();
    this.color = (PlayerColor) in.readObject();

  }

  @Override
  public void writeExternal(final ObjectOutput out) throws IOException {
    out.writeChars(this.name);
    out.writeObject(this.color);
  }

}
