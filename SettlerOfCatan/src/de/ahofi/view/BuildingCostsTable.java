package de.ahofi.view;

import de.ahofi.game.model.City;
import de.ahofi.game.model.Cost;
import de.ahofi.game.model.Development;
import de.ahofi.game.model.PlayingPiece;
import de.ahofi.game.model.Road;
import de.ahofi.game.model.Settlement;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class BuildingCostsTable extends GridPane {

  public BuildingCostsTable() {
    final Label label = new Label("Bulding Costs");
    label.setFont(new Font("Arial", 20));
    add(label, 0, 0, 5, 1);

    final List<PlayingPiece> playingCards = Stream.of(new Road(), new Settlement(), new City(), new Development())
            .collect(Collectors.toList());

    for (int row = 1; row <= playingCards.size(); row++) {
      final PlayingPiece piece = playingCards.get(row - 1);
      add(new Label(piece.getClass().getSimpleName()), 0, row);
      int column = 1;
      for (final Cost cost : piece.getCosts()) {
        for (int amount = 0; amount < cost.getAmount(); amount++) {
          add(new Rectangle(15, 15, cost.getResourceType().getColor()), column, row);
          column++;
        }
      }
    }

  }

}
