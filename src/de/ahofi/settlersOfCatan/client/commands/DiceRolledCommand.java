package de.ahofi.settlersOfCatan.client.commands;

import de.ahofi.settlersOfCatan.dice.DiceView;
import de.ahofi.settlersOfCatan.server.commands.ServerToClientCommand;

import java.io.Serializable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiceRolledCommand implements ServerToClientCommand, Serializable {

  private static final long serialVersionUID = 1L;

  private final int left;
  private final int right;

  @Override
  public void execute() {
    DiceView.getInstance().setDiceValues(this.left, this.right);
  }

}
