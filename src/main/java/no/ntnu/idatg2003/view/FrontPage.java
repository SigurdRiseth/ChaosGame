package no.ntnu.idatg2003.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.ntnu.idatg2003.controller.FrontPageController;
import no.ntnu.idatg2003.model.game_engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game_engine.ChaosGame;
import no.ntnu.idatg2003.model.game_engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game_engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game_engine.ChaosGameObserver;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;

public class FrontPage {
  private FrontPageController controller;
  private Scene scene;

  public FrontPage(FrontPageController controller) {
    this.controller = controller;
    this.scene = new Scene(createContent(), 800, 600);
  }

  public Scene getScene() {
    return scene;
  }

  private BorderPane createContent() {
    BorderPane content = new BorderPane();

    FrontPage.Title title = new FrontPage.Title("C H A O S   G A M E");
    title.setPadding(new Insets(50, 50, 50, 50));
    content.setTop(title);

    Button runGameButton = new Button("Run a Game");
    Button createGameButton = new Button("Create a Custom Game");
    Button exitButton = new Button("Exit");

    runGameButton.setOnAction(e -> {
      controller.openRunGameScene();
    });

    createGameButton.setOnAction(e -> {
      throw new UnsupportedOperationException("Not implemented yet");
    });

    exitButton.setOnAction(e -> {
      controller.exit();
    });

    VBox vBox = new VBox(10, runGameButton, createGameButton, exitButton);
    vBox.setAlignment(javafx.geometry.Pos.CENTER);
    content.setCenter(vBox);
    content.setStyle("-fx-background-color: #3b1d5a;");
    return content;
  }

  private static class Title extends StackPane {
    public Title(String name) {
      Rectangle bg = new Rectangle(500, 70);
      bg.setStroke(javafx.scene.paint.Color.WHITE);
      bg.setStrokeWidth(2);
      bg.setFill(null);

      Text text = new Text(name);
      text.setFill(javafx.scene.paint.Color.WHITE);
      text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));

      setAlignment(javafx.geometry.Pos.CENTER);
      getChildren().addAll(bg, text);
    }
  }
}



