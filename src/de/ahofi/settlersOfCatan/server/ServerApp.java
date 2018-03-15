package de.ahofi.settlersOfCatan.server;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

import de.ahofi.settlersOfCatan.client.commands.ClientToServerCommand;

public class ServerApp extends GameApplication {

  public static void main(final String[] args) {
    launch();
  }

  private MultiServer server;

  @Override
  protected void initGame() {
    this.server = new MultiServer();
    this.server.addParser(ClientToServerCommand.class, p -> p.execute(this.server));
    this.server.start();
  }

  @Override
  protected void initSettings(final GameSettings settings) {
    settings.setTitle("Settlers of Catan - Server");
  }

}
