package no.ntnu.idatg2003.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.RunGameController;

public class RunGameView {

  private Scene scene;
  private RunGameController controller;

  public RunGameView(RunGameController controller) {
    this.controller = controller;
    this.scene = new Scene(createContent(), 800, 600);
  }

  public Scene getScene() {
    return scene;
  }

  private BorderPane createContent() {
    VBox buttons = new VBox();
    buttons.setSpacing(10);
    buttons.setAlignment(javafx.geometry.Pos.CENTER);

    Button juliaButton = new Button("Julia Set");
    Button sierpinskiButton = new Button("Sierpinski Triangle");
    Button barnsleyButton = new Button("Barnsley Fern");
    Button customButton = new Button("Custom Game");

    juliaButton.setOnAction(e -> {
      controller.openPresetGameScene("julia");
    });

    sierpinskiButton.setOnAction(e -> {
      controller.openPresetGameScene("sierpinski");
    });

    barnsleyButton.setOnAction(e -> {
      controller.openPresetGameScene("barnsley");
    });

    customButton.setOnAction(e -> {
      controller.openPresetGameScene("custom");
    });

    Button backButton = new Button("Return");
    backButton.setOnAction(e -> {
      controller.openFrontPageScene();
    });


    Text text = new Text("Choose a game to run!");
    text.setStyle("-fx-font-size: 24; -fx-fill: white;");
    text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));

    buttons.getChildren().addAll(juliaButton, sierpinskiButton, barnsleyButton, customButton);

    VBox mainContent = new VBox();
    mainContent.getChildren().addAll(text, buttons);
    mainContent.setSpacing(30);
    mainContent.setAlignment(javafx.geometry.Pos.CENTER);

    BorderPane content = new BorderPane();
    BorderPane.setMargin(backButton, new Insets(10));
    content.setTop(backButton);
    content.setCenter(mainContent);
    content.setStyle("-fx-background-color: #3b1d5a;");
    return content;
  }

}
