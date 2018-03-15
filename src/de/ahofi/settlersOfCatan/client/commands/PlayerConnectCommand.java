package de.ahofi.settlersOfCatan.client.commands;

import de.ahofi.settlersOfCatan.client.Clientmodel;
import de.ahofi.settlersOfCatan.client.GameView;
import de.ahofi.settlersOfCatan.game.Player;
import de.ahofi.settlersOfCatan.server.commands.ServerToClientCommand;

import java.util.List;

import lombok.RequiredArgsConstructor;

import javafx.application.Platform;

@RequiredArgsConstructor
public class PlayerConnectCommand implements ServerToClientCommand {

  private static final long serialVersionUID = 1L;

  private final List<Player> username;

  @Override
  public void execute() {
    Platform.runLater(() -> {
      Clientmodel.getInstance().getGame().getPlayers().clear();
      Clientmodel.getInstance().getGame().getPlayers().addAll(this.username);
      GameView.getInstance().removePlayerBoard();
      GameView.getInstance().addPlayer(Clientmodel.getInstance().getGame().getPlayers());
    });
  }

}
