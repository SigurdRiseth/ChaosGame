package no.ntnu.idatg2003.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.ntnu.idatg2003.controller.FrontPageController;

public class FrontPageView extends Application {

  private FrontPageController controller;

  public static void appMain(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    //controller = new FrontPageController();

    //this.primaryStage = primaryStage;

    BorderPane pane = createContent();

    Scene scene = new Scene(pane, 800, 600);
    scene.setFill(Color.PURPLE);

    primaryStage.setMinHeight(600);
    primaryStage.setMinWidth(800);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game");
    primaryStage.show();
  }

  private BorderPane createContent() {
    BorderPane content = new BorderPane();

    Title title = new Title("C H A O S   G A M E");
    title.setPadding(new Insets(50, 50, 50, 50));
    content.setTop(title);

    Button runGameButton = new Button("Run a Game");
    Button createGameButton = new Button("Create a Custom Game");
    Button exitButton = new Button("Exit");

    runGameButton.setOnAction(e -> {
      //controller.openRunGameScene(e);
    });

    createGameButton.setOnAction(e -> {
      throw new UnsupportedOperationException("Not implemented yet");
    });

    exitButton.setOnAction(e -> {
      System.exit(0);
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
