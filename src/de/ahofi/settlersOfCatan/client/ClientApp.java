package de.ahofi.settlersOfCatan.client;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

public class ClientApp extends GameApplication {

  public static void main(final String[] args) {
    launch();
  }

  private final ConnectView view = ConnectView.getInstance();

  @Override
  protected void initSettings(final GameSettings settings) {
    settings.setTitle("Settlers of Catan");
  }

  @Override
  protected void initUI() {
    getGameScene().addUINode(this.view);
  }

}
