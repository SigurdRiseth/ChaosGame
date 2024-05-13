package no.ntnu.idatg2003.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatg2003.controller.RunCustomGameController;

public class RunCustomGame {

  private final RunCustomGameController controller;

  public RunCustomGame(RunCustomGameController controller) {
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

    Button button1 = new Button("Game 1");
    Button button2 = new Button("Game 2");

    return vBox;
  }

}
