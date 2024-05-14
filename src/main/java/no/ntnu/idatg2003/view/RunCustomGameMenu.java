package no.ntnu.idatg2003.view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.RunCustomGameMenuController;
import no.ntnu.idatg2003.model.game.engine.ChaosGameFileHandler;

public class RunCustomGameMenu {

  private final RunCustomGameMenuController controller;

  public RunCustomGameMenu(RunCustomGameMenuController controller) {
    this.controller = controller;
  }

  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));

    return new Scene(content, 800, 600);
  }

  private BorderPane createContent() {
    // Back button
    Button backButton = new Button("Return");
    backButton.setOnAction(e -> controller.returnToLastPage());

    // Buttons for the games
    VBox vBox = initializeBox();

    // Set the content
    BorderPane content = new BorderPane();
    content.setTop(backButton);
    content.setCenter(vBox);
    content.setStyle("-fx-background-color: #3b1d5a;");

    return content;
  }

  private VBox initializeBox() {
    VBox vBox = new VBox();
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10, 10, 10, 10));
    vBox.setAlignment(javafx.geometry.Pos.CENTER);

    Text text = new Text("Choose which custom game to run!");
    text.setStyle("-fx-font-size: 24; -fx-fill: white;");
    vBox.getChildren().add(text);

    List<String> files = ChaosGameFileHandler.getCustomGameFileNames();

    for (String file : files) {
      Button button = new Button(file);
      button.setOnAction(e -> {
        controller.runCustomGame(file);
      });
      vBox.getChildren().add(button);
    }

    return vBox;
  }

}
