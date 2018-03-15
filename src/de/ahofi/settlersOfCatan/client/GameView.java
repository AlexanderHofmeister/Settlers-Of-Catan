package de.ahofi.settlersOfCatan.client;

import de.ahofi.settlersOfCatan.client.commands.RollDiceCommand;
import de.ahofi.settlersOfCatan.dice.Dice;
import de.ahofi.settlersOfCatan.dice.DiceView;
import de.ahofi.settlersOfCatan.game.Player;

import java.util.List;

import lombok.Getter;

import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class GameView extends BorderPane {

  private static GameView instance;

  public static GameView getInstance() {
    if (instance == null) {
      instance = new GameView();
    }
    return instance;
  }

  @Getter
  private final GridPane playerBoard = new GridPane();

  public GameView() {
    setTop(this.playerBoard);
  }

  public void addPlayer(final List<Player> allplayer) {

    for (int i = 0; i < allplayer.size(); i++) {
      final Player player = allplayer.get(i);
      final GridPane playerPane = new GridPane();
      final Label playerName = new Label(player.getName());
      playerPane.add(playerName, i + 2, 0);
      final Rectangle rectangle = new Rectangle(50, 50, player.getColor().toFXColor());
      playerPane.add(rectangle, i, 0, 2, 2);
      GridPane.setValignment(playerName, VPos.TOP);
      this.playerBoard.add(playerPane, i, 0);
    }
    setTop(this.playerBoard);

    final HBox diceBox = new HBox(5);
    final DiceView diceView = DiceView.getInstance();
    diceBox.getChildren().add(diceView);
    final Button rollDiceButton = new Button("Roll");
    rollDiceButton.setOnAction(event -> {
      Clientmodel.getInstance().getClient().send(new RollDiceCommand(new Dice().roll(), new Dice().roll()));
    });
    diceBox.getChildren().add(rollDiceButton);
    setBottom(diceBox);
  }

  public void removePlayerBoard() {
    setTop(null);
  }

}
