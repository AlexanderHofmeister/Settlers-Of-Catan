package de.ahofi.settlersOfCatan.client;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;

import de.ahofi.settlersOfCatan.client.net.Client;
import de.ahofi.settlersOfCatan.server.commands.AddPlayerCommand;
import de.ahofi.settlersOfCatan.server.commands.ServerToClientCommand;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public final class ConnectView extends GridPane {

  private static ConnectView instance;

  public static ConnectView getInstance() {
    if (instance == null) {
      instance = new ConnectView();
    }
    return instance;
  }

  public ConnectView() {

    final HBox addNewPlayer = new HBox(5);

    final TextField usernameField = new TextField();

    final Button sendMessage = new Button("Connect");

    sendMessage.setOnAction(event -> {
      final Client client = new Client("127.0.0.1");
      try {
        client.connect();
      } catch (final Exception exception) {
        exception.printStackTrace();
      }
      Clientmodel.getInstance().setClient(client);
      client.addParser(ServerToClientCommand.class, p -> p.execute());
      client.send(new AddPlayerCommand(usernameField.getText()));

      final GameApplication app = FXGL.getApp();
      app.getGameScene().removeUINode(this);

      app.getGameScene().addUINode(GameView.getInstance());

    });

    addNewPlayer.getChildren().add(usernameField);
    addNewPlayer.getChildren().add(sendMessage);

    getChildren().add(addNewPlayer);

  }

}
