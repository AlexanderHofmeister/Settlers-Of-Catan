package de.ahofi.settlersOfCatan.client.commands;

import de.ahofi.settlersOfCatan.server.MultiServer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RollDiceCommand implements ClientToServerCommand {

  private static final long serialVersionUID = 1L;

  private final int left;
  private final int right;

  @Override
  public void execute(final MultiServer server) {
    server.send(new DiceRolledCommand(this.left, this.right));
  }

}
