package de.ahofi.settlersOfCatan.server.commands;

import de.ahofi.settlersOfCatan.client.commands.ClientToServerCommand;
import de.ahofi.settlersOfCatan.client.commands.PlayerConnectCommand;
import de.ahofi.settlersOfCatan.game.Player;
import de.ahofi.settlersOfCatan.server.MultiServer;

import java.util.ArrayList;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddPlayerCommand implements ClientToServerCommand {

  private static final long serialVersionUID = 1L;
  private final String playername;

  @Override
  public void execute(final MultiServer server) {
    final Player newPlayer = new Player(this.playername);
    server.getGame().getPlayers().add(newPlayer);
    server.send(new PlayerConnectCommand(new ArrayList<>(server.getGame().getPlayers())));

  }
}
