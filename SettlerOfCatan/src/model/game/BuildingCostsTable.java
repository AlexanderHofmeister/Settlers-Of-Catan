package model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import model.PlayingPiece;
import model.ResourceType;

public class BuildingCostsTable extends GridPane {

  public BuildingCostsTable() throws InstantiationException, IllegalAccessException {
    add(new Label("Bulding Costs"), 0, 0, 5, 1);

    final List<Class<? extends PlayingPiece>> playingCards = new ArrayList<>();
    playingCards.addAll(
            new Reflections(new ConfigurationBuilder().addUrls(ClasspathHelper.forPackage("model"))).getSubTypesOf(PlayingPiece.class));
    for (int row = 1; row <= playingCards.size(); row++) {
      final Class<? extends PlayingPiece> clazz = playingCards.get(row - 1);
      final PlayingPiece newInstance = clazz.newInstance();
      int column = 0;
      for (final Map.Entry<ResourceType, Integer> price : newInstance.getCosts().entrySet()) {
        for (int amount = 0; amount < price.getValue(); amount++) {
          add(new Rectangle(15, 15, price.getKey().getColor()), column, row);
          column++;
        }
      }
    }

  }

}
