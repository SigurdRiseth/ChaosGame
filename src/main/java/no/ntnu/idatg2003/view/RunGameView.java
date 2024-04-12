package no.ntnu.idatg2003.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.PresetGameController;

public class RunGameView {

  private PresetGameController controller;

  public Scene getScene() {
    BorderPane content = createContent();
    return new Scene(content, 800, 600);
  }

  private BorderPane createContent() {
    controller = new PresetGameController();
    VBox buttons = new VBox();
    buttons.setSpacing(10);
    buttons.setAlignment(javafx.geometry.Pos.CENTER);

    Button juliaButton = new Button("Julia Set");
    Button sierpinskiButton = new Button("Sierpinski Triangle");
    Button barnsleyButton = new Button("Barnsley Fern");
    Button customButton = new Button("Custom Game");

    juliaButton.setOnAction(e -> {
      controller.openPresetGameScene(e);
    });

    sierpinskiButton.setOnAction(e -> {
      throw new UnsupportedOperationException("Not implemented yet");
    });

    barnsleyButton.setOnAction(e -> {
      throw new UnsupportedOperationException("Not implemented yet");
    });

    customButton.setOnAction(e -> {
      throw new UnsupportedOperationException("Not implemented yet");
    });

    buttons.getChildren().addAll( juliaButton, sierpinskiButton, barnsleyButton, customButton);

    BorderPane content = new BorderPane();
    Text text = new Text("Choose a game to run!");
    text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
    content.setTop(text);
    content.setCenter(buttons);
    content.setStyle("-fx-background-color: #3b1d5a;");
    return content;
  }

}
